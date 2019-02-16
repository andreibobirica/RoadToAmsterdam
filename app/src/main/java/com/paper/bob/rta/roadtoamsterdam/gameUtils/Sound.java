package com.paper.bob.rta.roadtoamsterdam.gameUtils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.activity.PlatformMainActivity;

public class Sound {


    //Campi di configurazione
    private SoundPool soundPool;
    private AudioManager audioManager;
    private final int MAX_STREAMS = 5;
    private final int streamType = AudioManager.STREAM_MUSIC;
    private float volume;

    //Campi
    private Context c;
    private boolean loop;
    private int mainSound;
    private String soundName;
    private boolean loaded = false;
    private String tipoSound;

    public Sound(String soundName, boolean loop, String tipoSound)
    {this.soundName = soundName; this.loop=loop;this.tipoSound=tipoSound;}

    public void setSoundPlayer(Context c) {
        Log.i("RTA","----setSouondPlayer");
        this.c = c;
        audioManager = (AudioManager) c.getSystemService(c.AUDIO_SERVICE);
        float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);
        float maxVolumeIndex  = (float) audioManager.getStreamMaxVolume(streamType);
        this.volume = currentVolumeIndex / maxVolumeIndex;
        PlatformMainActivity plat = (PlatformMainActivity)c;
        plat.setVolumeControlStream(streamType);
        AudioAttributes audioAttrib = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        SoundPool.Builder builder= new SoundPool.Builder();
        builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);
        //Inizializzazione SoundPool
        this.soundPool = builder.build();
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if(loaded)
                {
                    Log.i("RTA","----setSouondPlayer Play");
                    int nLoop = loop ? -1 : 0;
                    soundPool.play(mainSound,volume, volume, 1, nLoop, 1f);
                }
            }
        });
    }

    public void setContext(Context c)
    {this.c=c;}

    public void play()
    {
        if(!loaded)  {
            Log.i("RTA","----2Play");
            int resId = c.getResources().getIdentifier(soundName, "raw", c.getPackageName());
            // Load sound file into SoundPool.
            this.mainSound = this.soundPool.load(c,resId,1);
            loaded=true;
        }
    }

    public void stop() {
        this.soundPool.stop(mainSound);
    }
}