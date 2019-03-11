/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

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

    public void clear() {mp.stop();mp.release();}
}
