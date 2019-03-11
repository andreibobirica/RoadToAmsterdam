/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.gameUtils.SoundBG;

/**
 * Acitivyt , pseudo Astratta, non è una ascitivyt vera e proprio, non ha View e non viene mai richiamata dalgi intent o dalgi startAcitivity.
 * Questa pseudo Acitivity Astratta serve solo per essere poi successivamente estesa da altre activity.
 * Per implementare il Suono comune della soundTrack di background per più acitivity in maniera comune e senza interruzioni si è trovato
 * questa modalità per la quale gestendo il riferimento al campo statico SoundBG da questa actiivty, il suono della musica è su tutte le acitivty che estendono questa
 * pseudo acitivity.
 */
public class SoundBackgroundActivity extends AppCompatActivity {

    /**Campo SoundBG contenente il riferimento SoundBG adebito al play o al pause della musica di background*/
    private static SoundBG soundBG;
    /**Campo che indica se la musica di background è stata già avviata oppure no*/
    private static boolean soundBGplayed = false;

    /**
     * Metodo che serve per settare un oggetto SoundBG, il riferimento al oggetto che fa partire la musica di background
     * @param soundBG
     */
    public static void setSoundBG(SoundBG soundBG) {
        SoundBackgroundActivity.soundBG = soundBG;
    }

    /**
     * Metodo onCreate richiamto automaticamente dall'actiivyt Life Cycke
     * @param savedInstanceState parametro non utilizzato che serve per la gestione delle risorse di Android Runtime
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Metodo clear che serve per pulire le celle di memoria del SoundBg in maniera da non lasciare tracce e memoria corrotta sul dispositivo
     */
    public static void clear()
    {
        //Log.i("RTAbg","playcanzone");
        soundBGplayed=false;
        soundBG.stop();
        soundBG.clear();
        soundBG=null;
    }

    /**
     * Metodo play che avvia la musica di background
     */
    public static void play() {
        if(!soundBGplayed) {
            //Log.i("RTAbg","playcanzone");
            soundBGplayed = true;
            soundBG.play();
        }
    }

    /**
     * Metodo stop che stoppa la musica di background
     */
    public static void stop()
    {
        if(soundBGplayed) {
            //Log.i("RTAbg","stopcanzone");
            soundBGplayed = false;
            soundBG.stop();
        }
    }

    /**
     * Metodo onPause richiamto automaticamente dall'actiivyt Life Cycle
     */
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

    /**
     * Metodo onResume richiamto automaticamente dall'actiivyt Life Cycke
     */
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
