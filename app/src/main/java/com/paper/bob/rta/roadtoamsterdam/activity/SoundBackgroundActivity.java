package com.paper.bob.rta.roadtoamsterdam.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.gameUtils.SoundBG;

public class SoundBackgroundActivity extends AppCompatActivity {

    private static SoundBG soundBG;
    private static boolean soundBGplayed = false;

    public static void setSoundBG(SoundBG soundBG) {
        SoundBackgroundActivity.soundBG = soundBG;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void clear()
    {
        Log.i("RTAbg","playcanzone");
        soundBGplayed=false;
        soundBG.stop();
        soundBG.clear();
        soundBG=null;
    }

    public static void play() {
        if(!soundBGplayed) {
            Log.i("RTAbg","playcanzone");
            soundBGplayed = true;
            soundBG.play();
        }
    }

    public static void stop()
    {
        if(soundBGplayed) {
            Log.i("RTAbg","stopcanzone");
            soundBGplayed = false;
            soundBG.stop();
        }
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        if(soundBGplayed){
            Log.i("RTAbg","onPuase bgsound");
            soundBG.stop();
            soundBGplayed = false;
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(!soundBGplayed && soundBG!=null)
        {
            Log.i("RTAbg","onResume bgsound");
            soundBG.play();
            soundBGplayed = true;
        }
    }
}
