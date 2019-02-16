package com.paper.bob.rta.roadtoamsterdam.gameUtils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class SoundPlayer{

    //Campi di configurazione
    private SoundPool soundPool;
    private boolean loaded;
    private AudioManager audioManager;
    // Maximumn sound stream.
    private static final int MAX_STREAMS = 5;
    // Stream type.
    private static final int streamType = AudioManager.STREAM_MUSIC;
    //Volume
    private float volume;

    //Campi
    private Context c;
    private int mainSound;

    public SoundPlayer(Context c, Activity ac) {
        this.c = c;
        // AudioManager audio settings for adjusting the volume
        audioManager = (AudioManager) c.getSystemService(c.AUDIO_SERVICE);
        // Current volumn Index of particular stream type.
        float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);
        // Get the maximum volume index for a particular stream type.
        float maxVolumeIndex  = (float) audioManager.getStreamMaxVolume(streamType);
        // Volumn (0 --> 1)
        this.volume = currentVolumeIndex / maxVolumeIndex;
        // Suggests an audio stream whose volume should be changed by
        // the hardware volume controls.
        ac.setVolumeControlStream(streamType);
        //Configuration Audio Atributes
        AudioAttributes audioAttrib = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        SoundPool.Builder builder= new SoundPool.Builder();
        builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);
        //Inizializzazione SoundPool
        this.soundPool = builder.build();
        // When Sound Pool load complete.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;

            }
        });
    }

    public void setSound(String nomeSound)
    {
        int resId = c.getResources().getIdentifier(nomeSound, "raw", c.getPackageName());
        // Load sound file into SoundPool.
        this.mainSound = this.soundPool.load(c,resId,1);
    }

    public int playSound(boolean loop)
    {
        if(loaded)  {
            float leftVolumn = volume;
            float rightVolumn = volume;
            //Decisione se in loop o no
            int nLoop = loop ? -1 : 0;
            // Play sound. Returns the ID of the new stream.
            return this.soundPool.play(this.mainSound,leftVolumn, rightVolumn, 1, nLoop, 1f);
        }
        return 0;
    }

    public void stopSound(int soundId) {
        this.soundPool.stop(soundId);
    }
}