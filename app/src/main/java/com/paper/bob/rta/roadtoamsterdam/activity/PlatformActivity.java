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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.paper.bob.rta.roadtoamsterdam.R;
import com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform.Controller;
import com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform.EngineGame;
import com.paper.bob.rta.roadtoamsterdam.gameUtils.Sound;

import java.util.ArrayList;

public class PlatformActivity extends SoundBackgroundActivity {

    /**Variabili adette ai sensori*/
    protected PowerManager.WakeLock mWakeLock;
    private PhoneStateListener phoneStateListener;
    /**Campi che sevono per il motore di gioco*/
    private Controller control;
    private EngineGame engineGame;
    private boolean scelta;
    private ImageButton btn_right;
    private ImageButton btn_left;
    private ImageButton btn_up;

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
        setContentView(R.layout.activity_platform_main);
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("RTA", "OnStart");
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

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("RTA", "onsTOP");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("RTA", "onDestroy");
        //Riciclo gli elementi del EngineGame
        engineGame.recycle();
        engineGame = null;
        //Annullo i listener per i btn
        btn_left.setOnClickListener(null);
        btn_up.setOnClickListener(null);
        btn_right.setOnClickListener(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("RTA", "OnPause");
        //Pausa del engineGame, specialmente del Thred di gioco
        //engineGame.stopView();
        //Pausa dei suoni
        ArrayList<Sound> sounds = engineGame.getSounds();
        for (Sound s : sounds) {s.pause();}
        //Gestione il listener dellle chiamate, stoppandolo perchè non serve più
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (mgr != null) {mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);}

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("RTA", "OnResume");
        engineGame.startView();
    }

    /**
     * Metodo finish, in ovverloading per sovrascrivere il precedente esteso.
     * Oltre che a finire la parte platform si necessita anche di settare come EXTRA al Intent la variabile scelta
     * Da passare al GameComposerAvticity
     */
    @Override
    public void finish() {
        //Stop and claer SoundBackGround
        SoundBackgroundActivity.clear();

        Intent intent = new Intent();
        intent.putExtra("scelta", scelta);
        setResult(RESULT_OK, intent);
        Runtime.getRuntime().gc();
        super.finish();
    }

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
     *
     * @param d parametro che indica il nome del dialogo , il suo identificativo
     */
    public void avviaDialogo(String d) {
        runOnUiThread(new runableAvviaDialogo(d));
    }

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
                Intent dialogo = new Intent(PlatformActivity.this, DialogBackgroundActivity.class);
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

