/*
 * Copyright (c) Andrei Cristian Bobirica Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Objects;
import android.graphics.Bitmap;

/**
 * Animation non è un elemnto di gioco ma una funzione legata propriamente agli Ostacoli in quanto essi usano Animation
 * Per modificare una spreedshit e trarne uno speudoGIF
 */
public class Animation {
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean switchImg = false;

    /**
     * Metodo che setta un array di immagini , la suddivisione delle spreedshit
     * @param frames
     */
    public void setFrames(Bitmap[] frames)
    {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }
    /**Metodo che imposta il tempo di attesa tra un frame del animation e l'altro*/
    public void setDelay(long d){delay = d;}
   /**Metodo update che viene richiamto ogni volta al FPS, dal Ostacolo*/
    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>delay)
        {
            if(!switchImg) {
                if(currentFrame==frames.length-1)
                {switchImg=true;}
                else
                {currentFrame++;}
            }
            else
            {
                if(currentFrame == 0)
                {switchImg=false;}
                else
                {currentFrame--;}
            }
            startTime = System.nanoTime();
        }

    }

    /**
     * Metodo che resituisce l'immaggine in base al frame da mostrare dell'animazione.
     * In base al tempo di delay di attesa e dal numero di frame fa vedere un frame diverso ogni chiamata per
     * formare una GIF
     * @return Bitmap immagine effettiva dell'ostacolo
     */
    public Bitmap getImage(){
        return frames[currentFrame];
    }
}