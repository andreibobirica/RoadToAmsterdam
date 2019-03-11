/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.gameUtils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import com.paper.bob.rta.roadtoamsterdam.activity.PlatformActivity;

public class Sound {


    //Campi di configurazione
    private SoundPool soundPool;
    private float volume;
    private boolean loaded = false;
    private Context context;

    //Campi
    private boolean loop;
    private int mainSound;
    private String soundName;
    private String tipoSound;
    private boolean paused = true;

    public Sound(String soundName, boolean loop, String tipoSound)
    {this.soundName = soundName; this.loop=loop;this.tipoSound=tipoSound;}

    public void setSoundPlayer(Context c) {
        context = c;
        AudioManager audioManager = (AudioManager) c.getSystemService(c.AUDIO_SERVICE);
        int streamType = AudioManager.STREAM_MUSIC;
        float currentVolumeIndex = (float) (audioManager != null ? audioManager.getStreamVolume(streamType) : 0);
        float maxVolumeIndex  = (float) (audioManager != null ? audioManager.getStreamMaxVolume(streamType) : 0);
        volume = currentVolumeIndex / maxVolumeIndex;
        PlatformActivity plat = (PlatformActivity)c;
        plat.setVolumeControlStream(streamType);
        AudioAttributes audioAttrib = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        SoundPool.Builder builder= new SoundPool.Builder();
        int MAX_STREAMS = 5;
        builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);
        //Inizializzazione SoundPool
        soundPool = builder.build();
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
            if(loaded)
            {
                int nLoop = loop ? -1 : 0;
                soundPool.play(mainSound,volume, volume, 1, nLoop, 1f);
            }
            }
        });
    }

    public boolean play()
    {

        if (!loaded) {
            int resId = context.getResources().getIdentifier(soundName, "raw", context.getPackageName());
            // Load sound file into SoundPool.
            this.mainSound = soundPool.load(context, resId, 1);
            loaded = true;
            paused = false;
            //Log.i("RTA","Loaded Play");
            return true;
        } else if (paused) {
            paused=false;
            soundPool.resume(mainSound);
            //Log.i("RTA","resume");
            return true;
        }
        return false;
    }

    public boolean replay()
    {
        if(play()){return true;}
        soundPool.play(mainSound,volume, volume, 1,0, 1f);
        //Log.i("RTA","replay");
        return false;
    }

    public void pause() {
        paused=true;
        soundPool.pause(mainSound);
        //Log.i("RTA","pause");
    }

    public String getTipoSound() {
        return tipoSound;
    }
}