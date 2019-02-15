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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.paper.bob.rta.roadtoamsterdam.R;
import com.paper.bob.rta.roadtoamsterdam.engine.Controller;

public class PlatformMainActivity extends AppCompatActivity implements SensorEventListener
{

    protected PowerManager.WakeLock mWakeLock;
    protected static Controller control  = new Controller();
    private SensorManager mSensorManager;
    private Sensor sensor;
    private float lastSensorUpdate = System.currentTimeMillis();
    private Sensor accelerometer;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        control= new Controller();

        Log.i("RTA","Costruttore PlatformainActivity");
        Log.i("RTA","ONCREATE");

        //settaggio del PlatformActivitify per il Controller
        control.setPlActivity(this);

        //SCREEN BIGHTNESS
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();

        //Inizializzazione Sensori
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

        //Avvio della Main Activity in FullScrean
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Eliminazione Title BAR
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Activity del PLatform Game
        setContentView(R.layout.activity_platform_main);
    }
    /*
    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelable("control", control);
        Log.i("RTA","onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
        {
            Log.i("RTA","onRestoreInstanceState");
            control = savedInstanceState.getParcelable("control");
        }
    }

    */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart()
    {
        super.onStart();
        Log.i("RTA","ONSTART");
        //GESTION HANDLER PER MOVIMENTO PLAYER, GESTION EVENTI CLICK BUTTON
        Button btn_right = findViewById(R.id.btn_right);
        Button btn_left = findViewById(R.id.btn_left);
        Button btn_up = findViewById(R.id.btn_up);
        btn_right.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Log.i("RTA","RIGHT RIGHT RIGHT false");
                    control.setMRight(false);
                }
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    Log.i("RTA","RIGHT RIGHT RIGHT true");
                    control.setMRight(true);
                }
                return false;
            }
        });

        btn_left.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Log.i("RTA","LEFT LEFT LEFT false");
                    control.setMLeft(false);

                }
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    Log.i("RTA","LEFT LEFT LEFT true");
                    control.setMLeft(true);

                }
                return false;
            }
        });

        btn_up.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.i("RTA","UP UP UP");
                control.setMUp(true);
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
        Log.i("RTA", "applicazione finita");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("RTA","ONPause");
        mSensorManager.unregisterListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, sensor,SensorManager.SENSOR_DELAY_NORMAL);
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
        startActivity(new Intent(PlatformMainActivity.this, DialogoActivity.class));
    }
    /**
     * Metodo getController(), metodo che serve a restituire il controller.
     * Siccome il controller è istanziato su questa activity, altri parte del gioco non possono accedervi in quanto sono istanziate
     * dal Activity ma dal SurfaceEngine, cioè dal EngineGame, che è instanziato dal documento xml manifest.
     * Detta la doppia provenieneza delle classsi Acitivy ed EngineGame, per comunicare tra di loro usano la classe Controller, la quale
     * Per essere raggiunta dal Enginegame Utilizza questo metodo statico
     * @return Controller
     */
    public static Controller getController() {
        return control;
    }
}
