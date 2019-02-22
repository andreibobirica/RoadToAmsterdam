package com.paper.bob.rta.roadtoamsterdam.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.paper.bob.rta.roadtoamsterdam.R;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Controller;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.EngineGame;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Background;
import com.paper.bob.rta.roadtoamsterdam.gameUtils.Sound;

import java.util.ArrayList;

public class PlatformMainActivity extends AppCompatActivity {

    //Sensori
    protected PowerManager.WakeLock mWakeLock;
    private float lastSensorUpdate = System.currentTimeMillis();
    private PhoneStateListener phoneStateListener;
    //Campi
    private Controller control;
    private EngineGame engineGame;
    private boolean scelta;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        INIZIALIZZAZIONE Sensori e opzioni per l'hardware del dispositivo
         */
        //SCREEN BIGHTNESS
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm != null ? pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "RTA") : null;
        this.mWakeLock.acquire(10 * 60 * 1000L /*10 minutes*/);


        //Avvio della Main Activity in FullScrean
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Eliminazione Title BAR
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set Activity con layout platformgame visibile
        setContentView(R.layout.activity_platform_main);
        /*
        INIZIALIZZAZIONE Campi del PatformMainActivity
         */

        //Individuazione del EngineGame con ID dal LayoutXML
        engineGame = findViewById(R.id.enginegame);
        /*
         * Creo il controller, lo inizializzo, gli passo questa activity, così avra il riferimento
         * e passo il controller al EngineGame, che si occuperà di uttilizzarlo.
         * Insieme setto anche il nome del livello
         */
        control = new Controller();
        String nomeLevel = getIntent().getExtras().getString("platform");
        Log.i("RTA", "\n\n\n\t@PLATFORM\nOnCreate\t@" + nomeLevel);
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
        final ImageButton btn_right = findViewById(R.id.btn_right);
        final ImageButton btn_left = findViewById(R.id.btn_left);
        final ImageButton btn_up = findViewById(R.id.btn_up);
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("RTA", "onsTOP");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("RTA", "OnPause");
        engineGame.stopView();
        //Pausa dei suoni
        ArrayList<Sound> sounds = engineGame.getSounds();
        for (Sound s : sounds) {
            s.pause();
        }
        engineGame.getSoundBG().stop();
        //Gestione chiamata coi suoni
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("RTA", "OnResume");
        engineGame.startView();
        //Play dei suoni
        engineGame.getSoundBG().play();
        //Nel caso di chiamata gestiosco l'audio
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    //Pausa dei suoni
                    ArrayList<Sound> sounds = engineGame.getSounds();
                    for (Sound s : sounds) {
                        s.pause();
                    }
                    engineGame.getSoundBG().stop();
                } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                    engineGame.getSoundBG().play();
                } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    //Pausa dei suoni
                    ArrayList<Sound> sounds = engineGame.getSounds();
                    for (Sound s : sounds) {
                        s.pause();
                    }
                    engineGame.getSoundBG().stop();
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }

    }

    /**
     * Metodo che richiamato dal Controller fa avviare un'altra activity con le informazioni che le servono.
     * In particolare avvia l'activity del dialogo e lo fa scegliere passandogli un parametro stringa
     *
     * @param d parametro che indica il nome del dialogo , il suo identificativo
     */
    public void avviaDialogo(String d) {
        Intent dialogo = new Intent(PlatformMainActivity.this, DialogActivity.class);
        dialogo.putExtra("nomeDialogo", d);
        startActivityForResult(dialogo, 2);
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("scelta", scelta);
        setResult(RESULT_OK, intent);
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

}

/*
Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 */
