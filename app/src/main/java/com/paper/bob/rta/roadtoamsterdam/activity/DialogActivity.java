package com.paper.bob.rta.roadtoamsterdam.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.paper.bob.rta.roadtoamsterdam.R;
import com.paper.bob.rta.roadtoamsterdam.engineDialog.DialogComposer;
import com.paper.bob.rta.roadtoamsterdam.engineDialog.Dialogo;

import java.util.Stack;

/**Activity del dialogo, questa activity fa in modo di far apparire un dialogo animato a schermo.
 * Il suo obbiettivo è prendere da parametro Itent un valore, passarlo al DataGraberDialog, estrapolarne una pila di Oggetti dialogo,
 * e sistematicamente mostrarli all'utente in fila.
 * Per ultimo nella scelta da effettuare, se presente, registra la scelta, e la manda tramite parametro Intent alla Activity Precedente.
 * Sarà richiamata sempre Dalla PlatformMainActivity, Opzionalmente dal DialogActivity, e mai da altre Activity.
 * Porterà sempre alla PlatformMainActivity, Opzionalmente alla DialogActivity, e mai ad altre activity.
 * */
public class DialogActivity extends AppCompatActivity {

    //CAMPI XML

    /**Foto del Player*/
    private ImageView fotoPers2;
    /**Foto del Dialogante con il player*/
    private ImageView fotoPers1;
    /**Nome del Player*/
    private TextView nomePersDialogo2;
    /**Nome del Dialogante con il player*/
    private TextView nomePersDialogo1;
    /**Casella di testo dove apparre il dialogo*/
    private TextView textDialogo;
    /**Variabili dedite alla scelta*/
    private TextView textScelta;
    private RadioButton radioSceltaTrue,radioSceltaFalse;
    private RadioGroup radioGroupScelte;
    /**Layout da nascondere o mostrare In base al momento del dialogo*/
    private RelativeLayout layoutScelte,layoutTextDialogo,layoutButton;
    /**Button Avnti con il quale si avanza nel dialogo*/
    private View btn_avanti;

    //CAMPI PROPRI

    /**Stack di battute di un dialogo*/
    private Stack<Dialogo> dialoghi;
    /**Scelta ipotetica del dialogo*/
    private boolean switchScelta;
    /**Nome del dialogo, passato tra le activity*/
    private String nomeDialogo;
    /**Scelta del dialogo, nel caso necessaria o presente*/
    private boolean scelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*INIZIALIZZAZIONE Sensori e opzioni per l'hardware del dispositivo*///SCREEN BIGHTNESS
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock mWakeLock = pm != null ? pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "RTA") : null;
        mWakeLock.acquire(10*60*1000L /*10 minutes*/);

        //Avvio della Main Activity in FullScrean
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Eliminazione Title BAR
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set Activity con layout dialogo visibile
        setContentView(R.layout.activity_dialogo);
        //Recupero del nome del dialogo passato con gli intent tra le activity
        nomeDialogo = getIntent().getExtras().getString("nomeDialogo");
        Log.i("RTA","\n@DIALOGO\t"+nomeDialogo);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        //Individuazione elementi XML identificati tramite l'ID
        fotoPers2 = findViewById(R.id.fotoPersDialogo2);
        nomePersDialogo2 = findViewById(R.id.nomePersDialogo2);
        fotoPers1 = findViewById(R.id.fotoPersDialogo1);
        nomePersDialogo1 = findViewById(R.id.nomePersDialogo1);

        textDialogo = findViewById(R.id.textDialogo);
        textScelta = findViewById(R.id.textScelta);
        radioSceltaTrue = findViewById(R.id.radioButtonTrue);
        radioSceltaFalse = findViewById(R.id.radioButtonFalse);
        radioGroupScelte = findViewById(R.id.radioGroupScelte);

        layoutScelte = findViewById(R.id.layoutScelte);
        layoutTextDialogo = findViewById(R.id.layoutTextDialogo);
        layoutButton = findViewById(R.id.layoutButton);
        btn_avanti = findViewById(R.id.btn_avanti);

        //Assegnazione al btn_avanti il listener event per il quale si fa avanzare le battute del dialogo
        btn_avanti.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    applyDialog(dialoghi);
                }
                return false;
            }
        });

        //Setto un Listener per la radio Group in maniera di capire quando si sceglie un radioButton
        radioGroupScelte.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                //Applicazione Scelta
                scelta = (radioGroupScelte.getCheckedRadioButtonId() == radioSceltaTrue.getId());
                layoutButton.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        /*Compositore di dialoghi che crea e restituisce i dialoghi*/
        DialogComposer dc = new DialogComposer(nomeDialogo, this);
        setDialoghi(dc.getDialoghi());
        applyDialog(dialoghi);
    }

    /**Overiding del metodo onKeyDown per non farlgi eseguire nessuna azione del caso in cui venga premuto
     * il pulsante indietro all'interno del gioco*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5 && keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {onBackPressed();return true;}
        return super.onKeyDown(keyCode, event);
    }
    /**Overiding del metodo onBackPressed per non farlgi eseguire nessuna azione del caso in cui venga premuto
     * il pulsante indietro all'interno del gioco*/
    @Override
    public void onBackPressed() {}

    /**Metodo che come dice il nome apply un dialogo.
     * La struttura dei dialoghi è fatta a modo di stack, ciò significa che una volta letta una battuta, viene buttata via, e tramite il
     * mecanismo FIFO, i dialoghi vengono letti in ordine e seguendo una logica di botta e risposta.
     * L'algoritmo di questo metodo assegna in base ai dialoghi i giusti valori alle VIEW XML.
     * Nel caso in cui si abbbia un dialogo semplice di testo, assegna dei valori alle classiche View di dialogo,
     * Altrimenti in caso di dialogo con scelte, assegna i valori alle VIEW per le scelte, inoltre nasconde e mostra
     * i layout che sono neccessari al momento giusto.
     * Nel caso non ci siano più dialoghi fa finire l'activity impostando come parametro All'intent dell'activity il valore della scelta.
     * @param dialoghi Pila contenente tutte le battute del dialogo, raggruppati ciascuno dentro Ogetto Dialogo.
     * @return valore booleano rappresentante per true l'aver applicato un dialogo con le scelte, e per false un dialogo normale.
     */
    public boolean applyDialog(Stack<Dialogo> dialoghi)
    {
        if(dialoghi.size()>0) {
            Dialogo d = dialoghi.pop();
            layoutScelte.setVisibility(View.INVISIBLE);
            layoutTextDialogo.setVisibility(View.VISIBLE);
            textDialogo.setText(d.getBattuta());
            nomePersDialogo2.setText(d.getNomePers());
            nomePersDialogo1.setText(d.getNomeOtherPers());
            fotoPers2.setImageBitmap(BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(d.getNomeImmPers(), "drawable", getPackageName())));
            fotoPers1.setImageBitmap(BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(d.getNomeImmOtherPers(), "drawable", getPackageName())));
            if (switchScelta) {
                textScelta.setText(d.getScelta());
                radioSceltaTrue.setText(d.getScelte().get(0));
                radioSceltaFalse.setText(d.getScelte().get(1));
                layoutScelte.setVisibility(View.VISIBLE);
                layoutTextDialogo.setVisibility(View.INVISIBLE);
                //Set invisible Btn fino a quando non si sceglie, sarà mostrato dopo in caso si prema una delle due scelte
                layoutButton.setVisibility(View.INVISIBLE);
                //Modifica del SwitchScelta
                switchScelta = false;
                return true;
            }
            if(dialoghi.size() != 0) {
                d = dialoghi.peek();
                switchScelta = !d.getScelta().equals("");
            }
        }else
        {
            Intent intent = new Intent();
            intent.putExtra("scelta", scelta);
            setResult(RESULT_OK, intent);
            finish();
        }
        return false;
    }

    /**
     * Metodo set che setta la Pila di battute di un dialogo
     * @param dialoghi Pila di battute di un certo dialogo
     */
    public void setDialoghi(Stack<Dialogo> dialoghi)
    {
        this.dialoghi = dialoghi;
    }
}
