/*
 * Copyright (c) Andrei Cristian Bobirica Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.paper.bob.rta.roadtoamsterdam.R;
import com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Controller;
import com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.EngineGame;
import com.paper.bob.rta.roadtoamsterdam.gameUtils.Sound;

import java.util.ArrayList;

/**
 * Acitivity Platform, questa acitivity gestisce la fase platform del gioco, il gioco 2d vero e proprio.
 * è estensione di SoundBackgroundAcitivity in quanto deve avere anche lui il suono di background del Environment
 */
public class PlatformActivity extends SoundBackgroundActivity {

    /**Variabili adette ai sensori*/
    protected PowerManager.WakeLock mWakeLock;
    private PhoneStateListener phoneStateListener;
    /**Campi che sevono per il motore di gioco*/

    /**Campo controller con il riferimento al controller, che gestisce i movimenti del player e tutte le analogie al concetto di movimento*/
    private Controller control;
    /**Motore di gioco vero e proprio*/
    private EngineGame engineGame;
    /**Campo che indica la scelta effetuata dall'utente nella acitivity Dialog*/
    private boolean scelta;

    /**Riferimenti ai button per il movimento*/
    private ImageButton btn_right;
    private ImageButton btn_left;
    private ImageButton btn_up;

    /**
     * Metodo onCreate richiamto automaticamente dal ActivityLyfeCycle
     * @param savedInstanceState parametro non uttilizzato, si utilizza per gestire le risorse nel Android Runtime
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*INIZIALIZZAZIONE Sensori e opzioni per l'hardware del dispositivo*/
        //SCREEN BIGHTNESS
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm != null ? pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "RTA") : null;
        if (this.mWakeLock != null) {this.mWakeLock.acquire(10 * 60 * 1000L /*10 minutes*/);}

        //Avvio della Main Activity in FullScrean
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Eliminazione Title BAR
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set Activity con layout platformgame visibile
        setContentView(R.layout.activity_platform);
        /*INIZIALIZZAZIONE Campi del PatformMainActivity*/
        //Individuazione del EngineGame con ID dal LayoutXML
        engineGame = findViewById(R.id.enginegame);
        /* Creo il controller, lo inizializzo, gli passo questa activity, così avra il riferimento
         * e passo il controller al EngineGame, che si occuperà di uttilizzarlo.
         * Insieme setto anche il nome del livello*/
        control = new Controller();
        String nomeLevel = getIntent().getExtras().getString("platform");
        Log.i("RTA", "\n\t@PLATFORM\tOnCreate\t@" + nomeLevel);
        engineGame.setLevelName(nomeLevel);
        control.setPlActivity(this);
        engineGame.setController(control);
        engineGame.setContext(this);
    }

    /**
     * Metodo onStart richiamto automaticamente dal ActivityLyfeCycle
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("RTA", "OnStart");
        //Nel caso di chiamata gestiosco l'audio nel senso che se arriva una chiamata
        //So gestire l'audio e metterlo in pausa per permettere la ricezzione solo dell'audio della chiamata
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    //Pausa dei suoni
                    ArrayList<Sound> sounds = engineGame.getSounds();
                    for (Sound s : sounds) {s.pause();}
                    SoundBackgroundActivity.stop();
                } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                    SoundBackgroundActivity.play();
                } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    //Pausa dei suoni
                    ArrayList<Sound> sounds = engineGame.getSounds();
                    for (Sound s : sounds) {s.pause();}
                    SoundBackgroundActivity.stop();
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (mgr != null) {mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);}
    }

    /**
     * Metodo onDestroy richiamto automaticamente dal ActivityLyfeCycle
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("RTA", "onDestroy");
        //Riciclo gli elementi del EngineGame
        engineGame.recycle();
        engineGame = null;
    }

    /**
     * Metodo onPause richiamto automaticamente dal ActivityLyfeCycle
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("RTA", "OnPause");
        //Pausa del engineGame, specialmente del Thred di gioco
        engineGame.stopView();
        //Pausa dei suoni
        ArrayList<Sound> sounds = engineGame.getSounds();
        for (Sound s : sounds) {s.pause();}
        //Gestione il listener dellle chiamate, stoppandolo perchè non serve più
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (mgr != null) {mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);}

    }

    /**
     * Metodo onResume richiamto automaticamente dal ActivityLyfeCycle
     */
    @Override
    protected void onResume() {
        super.onResume();
        //setContentView(R.layout.activity_platform_loading);
        Log.i("RTA", "OnResume");
        //GESTION HANDLER PER MOVIMENTO PLAYER, GESTION EVENTI CLICK BUTTON
        btn_right = findViewById(R.id.btn_right);
        btn_left = findViewById(R.id.btn_left);
        btn_up = findViewById(R.id.btn_up);

        btn_right.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    control.setMRight(false);
                    btn_right.setBackgroundResource(R.drawable.right);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    control.setMRight(true);
                    btn_right.setBackgroundResource(R.drawable.rightclick);
                }
                return false;
            }
        });

        btn_left.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    control.setMLeft(false);
                    btn_left.setBackgroundResource(R.drawable.left);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    control.setMLeft(true);
                    btn_left.setBackgroundResource(R.drawable.leftclick);
                }
                return false;
            }
        });

        btn_up.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    control.setMUp(true);
                    btn_up.setBackgroundResource(R.drawable.upclick);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    btn_up.setBackgroundResource(R.drawable.up);
                }
                return false;
            }
        });
        engineGame.startView();
    }

    /**
     * Metodo finish, in ovverloading per sovrascrivere il precedente esteso.
     * Oltre che a finire la parte platform si necessita anche di settare come EXTRA al Intent la variabile scelta
     * Da passare al GameComposerAvticity
     */
    @Override
    public void finish() {
        final Context c = getApplicationContext();
        runOnUiThread(new Runnable() {
            public void run() {Toast.makeText(c, "Caricamento in Corso", Toast.LENGTH_LONG).show();}
        });
        //Stop and claer SoundBackGround
        SoundBackgroundActivity.clear();
        Intent intent = new Intent();
        intent.putExtra("scelta", scelta);
        setResult(RESULT_OK, intent);
        Runtime.getRuntime().gc();
        super.finish();
    }

    /**
     * Metodo che registra il parametro di ritorno passato dalla chiusura della actiivy Dialog.
     * Il parametro contiene la scelta , se necessaria, effettuata durante il dialogo.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                scelta = data.getBooleanExtra("scelta", false);
                Log.i("RTA", "Il valore scelto è: " + scelta);
            }
        }
    }


    /**
     * Metodo che richiamato dal Controller fa avviare un'altra activity con le informazioni che le servono.
     * In particolare avvia l'activity del dialogo e lo fa scegliere passandogli un parametro stringa
     * @param d parametro che indica il nome del dialogo , il suo identificativo
     */
    public void avviaDialogo(String d) {
        runOnUiThread(new runableAvviaDialogo(d));
    }

    /**
     * Classe privata che serve solamente per creare una animazione di caricamento prima di avviare un dialogo
     * Il caricamento di una certa acitity necessita di tempo, e se il tempo su detterminati dispositivi è lungo
     * può risultare molto fatico per l'utente avere una esperienza ottimale.
     * Per questo motivo si uttilizzano tecnologie come i Task o i Thread per eseguire animazioni assestanti al Thread principale della Ui
     * Utilizzando queste escamotage, si può implementare un caricamento come in questo caso con una barra di caricamento e un emssaggio illustrativo.
     * Viene utilizzato una classe per il semplice motivo che è molto più immediato rispetto all'utilizzo di una funzione adebita allo stesso ragionamento.
     */
    private class runableAvviaDialogo implements Runnable {
        private String d;
        public runableAvviaDialogo(String d) {this.d= d;}
        @Override
        public void run() {new RunDialogActivity(PlatformActivity.this,d).execute();}
        private class RunDialogActivity extends AsyncTask<Void, Void, Void>
        {
            private final String d;
            ProgressDialog dialog;
            Context context;
            public RunDialogActivity(Context context, String d)
            {
                Log.i("RTA", "RunDialogActivity");
                this.context=context;
                this.d = d;
            }
            protected void onPreExecute() {
                //create the progress dialog as
                dialog=new ProgressDialog(context);
                dialog.setMessage("Caricamento Dialogo");
                dialog.show();
            }
            protected Void doInBackground(Void... JSONArray) {
                Intent dialogo = new Intent(PlatformActivity.this, DialogActivity.class);
                dialogo.putExtra("nomeDialogo",d);
                startActivityForResult(dialogo, 2);
                return null;
            }

            protected void onPostExecute(Void unused) {
                Log.i("RTA","postexe");
                dialog.dismiss();
            }
        }
    }




}

