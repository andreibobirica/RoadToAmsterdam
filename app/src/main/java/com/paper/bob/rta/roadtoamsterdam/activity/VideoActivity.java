/*
 * Copyright (c) Andrei Cristian Bobirica Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.activity;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.paper.bob.rta.roadtoamsterdam.R;

public class VideoActivity extends AppCompatActivity {

    private VideoView view;
    private ScaleGestureDetector mScaleDetector;
    private ProgressBar pbSkip;
    private int timerSkip = 0;
    private boolean stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Avvio della Main Activity in FullScrean
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Eliminazione Title BAR
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video);
        pbSkip = findViewById(R.id.skipProgressBar);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        //Inizializzazione propietà della videoView
        final Context c = getApplicationContext();
        view = findViewById(R.id.videoView);
        String video = getIntent().getExtras().getString("video");
        String path = "android.resource://" + getPackageName() + "/raw/" + video;
        Log.i("RTA","@VIDEO\n\tPath: "+path);
        view.setVideoURI(Uri.parse(path));
        view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(c, "Caricamento in Corso", Toast.LENGTH_LONG).show();}
                });
                finish();
            }
        });
        //Inizializzazioni sistema SKIP Video
        pbSkip.setProgress(0);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Log.i("RTA","Action Down");
                    stop=false;

                    final Handler handler = new Handler();
                    handler.post( new Runnable(){
                        public void run() {
                            timerSkip+=1;
                            pbSkip.setProgress(timerSkip);
                            Log.i("RTA","pb "+timerSkip);
                            if(timerSkip==100)
                            {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(c, "Caricamento in Corso", Toast.LENGTH_LONG).show();}
                                });
                                finish();
                            }
                            if(!stop && timerSkip<100)
                            {handler.postDelayed(this, 3);}
                        }
                    });

                }
                if (action == MotionEvent.ACTION_MOVE) {}
                if (action == MotionEvent.ACTION_UP) {
                    Log.i("RTA","Action UP");
                    stop = true;
                    timerSkip=0;
                    pbSkip.setProgress(timerSkip);
                }
                if (action == MotionEvent.ACTION_CANCEL) {
                    Log.i("RTA","Action Cancel");
                    stop=true;
                    timerSkip=0;
                    pbSkip.setProgress(timerSkip);
                }

                if (action == MotionEvent.ACTION_POINTER_UP) {}
                return true;
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        view.start();
    }
}
