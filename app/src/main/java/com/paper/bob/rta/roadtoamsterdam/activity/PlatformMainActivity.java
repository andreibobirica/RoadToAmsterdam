package com.paper.bob.rta.roadtoamsterdam.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
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
import com.paper.bob.rta.roadtoamsterdam.engine.EngineGame;

public class PlatformMainActivity extends AppCompatActivity implements Parcelable {

    protected PowerManager.WakeLock mWakeLock;
    protected static Controller control = new Controller();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("RTA","ONCREATE");

        if (savedInstanceState != null)
        {
            control = (Controller) savedInstanceState.getSerializable("control");
        }

        //settaggio del PlatformActivitify per il Controller
        control.setPlActivity(this);

        //SCREEN BIGHTNESS
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();

        //Avvio della Main Activity in FullScrean
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Eliminazione Title BAR
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Activity del PLatform Game
        setContentView(R.layout.activity_platform_main);
    }

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
    protected void onPause()
    {
        super.onPause();
        Log.i("RTA","ONPause");
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

    public void avviaDialogo(String d)
    {
        startActivity(new Intent(PlatformMainActivity.this, DialogoActivity.class));
    }


    public static Controller getController() {return control;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public PlatformMainActivity() {

    }

    protected PlatformMainActivity(Parcel in) {

    }

    public static final Parcelable.Creator<PlatformMainActivity> CREATOR = new Parcelable.Creator<PlatformMainActivity>() {
        @Override
        public PlatformMainActivity createFromParcel(Parcel source) {
            return new PlatformMainActivity(source);
        }

        @Override
        public PlatformMainActivity[] newArray(int size) {
            return new PlatformMainActivity[size];
        }
    };
}
