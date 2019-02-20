package com.paper.bob.rta.roadtoamsterdam.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.paper.bob.rta.roadtoamsterdam.R;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Controller;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.EngineGame;
import com.paper.bob.rta.roadtoamsterdam.gameUtils.Sound;

import java.util.ArrayList;

public class PlatformMainActivity extends AppCompatActivity implements SensorEventListener
{

    //Sensori
    protected PowerManager.WakeLock mWakeLock;
    private SensorManager mSensorManager;
    private float lastSensorUpdate = System.currentTimeMillis();
    private Sensor accelerometer;
    private PhoneStateListener phoneStateListener;
    //Campi
    private Controller control;
    private EngineGame engineGame;
    private String nomeLevel;
    private String scelta;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        INIZIALIZZAZIONE Sensori e opzioni per l'hardware del dispositivo
         */
        //SCREEN BIGHTNESS
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "RTA");
        this.mWakeLock.acquire();

        //Inizializzazione Sensori
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

        //Avvio della Main Activity in FullScrean
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        control= new Controller();
        nomeLevel = getIntent().getExtras().getString("platform");
        Log.i("RTA", "@PLATFORM\nOnCreate\t@"+nomeLevel);
        engineGame.setLevelName(nomeLevel);
        control.setPlActivity(this);
        engineGame.setController(control);

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart()
    {
        super.onStart();
        Log.i("RTA","ONSTART");
        //GESTION HANDLER PER MOVIMENTO PLAYER, GESTION EVENTI CLICK BUTTON
        final ImageButton btn_right = findViewById(R.id.btn_right);
        final ImageButton btn_left = findViewById(R.id.btn_left);
        final ImageButton btn_up = findViewById(R.id.btn_up);
        btn_right.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    //Log.i("RTA","RIGHT RIGHT RIGHT false");
                    control.setMRight(false);
                    btn_right.setBackgroundResource(R.drawable.right);
                }
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Log.i("RTA","RIGHT RIGHT RIGHT true");
                    control.setMRight(true);
                    btn_right.setBackgroundResource(R.drawable.rightclick);
                }
                return false;
            }
        });

         btn_left.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    //Log.i("RTA","LEFT LEFT LEFT false");
                    control.setMLeft(false);
                    btn_left.setBackgroundResource(R.drawable.left);
                }
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Log.i("RTA","LEFT LEFT LEFT true");
                    control.setMLeft(true);
                    btn_left.setBackgroundResource(R.drawable.leftclick);
                }
                return false;
            }
        });

        btn_up.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Log.i("RTA","UP UP UP UP up");
                    control.setMUp(true);
                    btn_up.setBackgroundResource(R.drawable.upclick);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    btn_up.setBackgroundResource(R.drawable.up);
                }
                return false;
            }
        });
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        Log.i("RTA","onsTOP");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("RTA","ONPause");
        engineGame.stopView();
        //Eliminazione Listener per accelerometro
        mSensorManager.unregisterListener(this);
        //Pausa dei suoni
        ArrayList<Sound> sounds =engineGame.getSounds();
        for (Sound s: sounds) {s.pause();}
        engineGame.getSoundBG().stop();
        //Gestione chiamata coi suoni
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if(mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("RTA","ONResume");
        engineGame.startView();
        //Registro Listener per Accelerometro
        mSensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        //Play dei suoni
        engineGame.getSoundBG().play();
        //Nel caso di chiamata gestiosco l'audio
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    //Pausa dei suoni
                    ArrayList<Sound> sounds =engineGame.getSounds();
                    for (Sound s: sounds) {s.pause();}
                    engineGame.getSoundBG().stop();
                } else if(state == TelephonyManager.CALL_STATE_IDLE) {
                    engineGame.getSoundBG().play();
                } else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    //Pausa dei suoni
                    ArrayList<Sound> sounds =engineGame.getSounds();
                    for (Sound s: sounds) {s.pause();}
                    engineGame.getSoundBG().stop();
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if(mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if(mySensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            long currentTime = System.currentTimeMillis();
            if((lastSensorUpdate-currentTime)<200) {
                lastSensorUpdate = currentTime;
                control.setSensorX(sensorEvent.values[0]);
                control.setSensorY(sensorEvent.values[1]);
                control.setSensorZ(sensorEvent.values[2]);
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    /**
     * Metodo che richiamato dal Controller fa avviare un'altra activity con le informazioni che le servono.
     * In particolare avvia l'activity del dialogo e lo fa scegliere passandogli un parametro stringa
     * @param d parametro che indica il nome del dialogo , il suo identificativo
     */
    public void avviaDialogo(String d)
    {
        Intent dialogo = new Intent(PlatformMainActivity.this, DialogoActivity.class);
        dialogo.putExtra("nomeDialogo", d);
        startActivityForResult(dialogo,2);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if(resultCode == RESULT_OK) {
                scelta = data.getStringExtra("scelta");
                Log.i("RTA","Il valore scelto è: "+scelta);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        super.onKeyDown(keyCode,event);
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5 && keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("RTA", "onBackPressed Called");
        Intent intent = new Intent();
        intent.putExtra("scelta", scelta);
        setResult(RESULT_OK, intent);
        finish();
    }

}

/*
Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 */
