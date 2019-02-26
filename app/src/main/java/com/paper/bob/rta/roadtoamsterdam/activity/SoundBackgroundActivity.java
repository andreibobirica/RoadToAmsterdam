package com.paper.bob.rta.roadtoamsterdam.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.gameUtils.SoundBG;

public class SoundBackgroundActivity extends AppCompatActivity {

    private static SoundBG soundBG;

    public static void setSoundBG(SoundBG soundBG) {
        SoundBackgroundActivity.soundBG = soundBG;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void play() {
        Log.i("RTA","playcanzone");
        if(soundBG!=null)
        soundBG.play();
    }

    public static void stop()
    {
        if(soundBG!=null)
        soundBG.stop();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        if(soundBG!=null)
        soundBG.stop();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(soundBG!=null)
        soundBG.play();
    }
}
