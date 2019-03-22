/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.gameUtils;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Classe Sound Bg , sound backgorund, che serve per far suonare un audio molto più lunga rispetto a SoundPool, infatti quest'ultimo utilizza
 * la tecnologia MediaPlayer, viene utilizzata questa quale per far eseguire le canzoni di Background
 */
public class SoundBG {
    /**Campo che indica il MediaPlayer che fa parteire gli audio*/
    private final MediaPlayer mp;

    /**Cstruttore*/
    public SoundBG(int resId, Context context)
    {
        mp = MediaPlayer.create(context,resId);
        mp.setLooping(true);
    }

    /**Metodo che fa partire l'audio*/
    public void play()
    {
        mp.start();
    }

    /**Metodo che fa rimanere in pausa l'audio*/
    public void stop()
    {
        mp.pause();
    }

    /**Metodo che pulisce le celle di memoria degli audio*/
    public void clear() {mp.stop();mp.release();}
}
