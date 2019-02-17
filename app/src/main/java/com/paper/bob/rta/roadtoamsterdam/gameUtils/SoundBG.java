package com.paper.bob.rta.roadtoamsterdam.gameUtils;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundBG {
    private final MediaPlayer mp;

    public SoundBG(int resId, Context context)
    {
        mp = MediaPlayer.create(context,resId);
        mp.setLooping(true);
    }

    public void play()
    {
        mp.start();
    }

    public void stop()
    {
        mp.pause();
    }
}
