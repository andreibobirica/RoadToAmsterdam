package com.paper.bob.rta.roadtoamsterdam.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class DialogoActivity extends AppCompatActivity {

    private PowerManager.WakeLock mWakeLock;

    private ImageView fotoPers2;
    private TextView nomePersDialogo2;
    private ImageView fotoPers1;
    private TextView nomePersDialogo1;

    private TextView textDialogo;
    private TextView textScelta;
    private RadioGroup radioGroupScelte;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;

    private RelativeLayout layoutScelte;
    private RelativeLayout layoutTextDialogo;
    private RelativeLayout layoutButton;


    private Stack<Dialogo> dialoghi;
    private boolean switchScelta = false;
    private View btn_avanti;
    private DialogComposer dc;
    private String nomeDialogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*INIZIALIZZAZIONE Sensori e opzioni per l'hardware del dispositivo*/
        //SCREEN BIGHTNESS
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "RTA");
        this.mWakeLock.acquire();
        //Avvio della Main Activity in FullScrean
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Eliminazione Title BAR
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set Activity con layout platformgame visibile
        setContentView(R.layout.activity_dialogo);
        nomeDialogo = getIntent().getExtras().getString("nomeDialogo");
        Log.i("RTA",nomeDialogo);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        fotoPers2 = findViewById(R.id.fotoPersDialogo2);
        nomePersDialogo2 = findViewById(R.id.nomePersDialogo2);
        fotoPers1 = findViewById(R.id.fotoPersDialogo1);
        nomePersDialogo1 = findViewById(R.id.nomePersDialogo1);

        textDialogo = findViewById(R.id.textDialogo);
        textScelta = findViewById(R.id.textScelta);
        radioGroupScelte = findViewById(R.id.radioGroupScelte);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);

        layoutScelte = findViewById(R.id.layoutScelte);
        layoutTextDialogo = findViewById(R.id.layoutTextDialogo);
        layoutButton = findViewById(R.id.layoutButton);
        btn_avanti = findViewById(R.id.btn_avanti);

        btn_avanti.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Log.i("RTA","LEFT LEFT LEFT true");
                    applyDialog(dialoghi);
                }
                return false;
            }
        });
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        dc = new DialogComposer(nomeDialogo,this);
        setDialoghi(dc.getDialoghi());
        applyDialog(dialoghi);
    }

    public boolean applyDialog(Stack<Dialogo> dialoghi)
    {
        Log.i("RTA","DIALOGHISIZE"+dialoghi.size());
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
                radioButton2.setText(d.getScelte().get(0));
                radioButton3.setText(d.getScelte().get(1));
                layoutScelte.setVisibility(View.VISIBLE);
                layoutTextDialogo.setVisibility(View.INVISIBLE);
                switchScelta = false;
                return true;
            }
            if(dialoghi.size() != 0) {
                d = dialoghi.peek();
                switchScelta = !d.getScelta().equals("");
            }
        }else
        {

            Log.i("RTA","Finish"+dialoghi.size());
            finish();
        }
        return false;
    }

    public void setDialoghi(Stack<Dialogo> dialoghi)
    {
        this.dialoghi = dialoghi;
    }
}
