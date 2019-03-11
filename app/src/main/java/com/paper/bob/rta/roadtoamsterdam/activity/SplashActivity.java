/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Acitivyt Splah, appena viene caricata l'applicazione si avvia questa acitivity contente solamente una View preimpostata con una immagine
 * statica.
 * Serve per il caricamento iniziale dell'aplicazione
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * Metodo onCreate richiamto automaticamente dall'actiivyt Life Cycle
     * @param savedInstanceState parametro non utillizzato, serve per la gestione delle risorse di Android Runtime
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Avvio della Main Activity in FullScrean
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Eliminazione Title BAR
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * Metodo onStart richiamto automaticamente dall'actiivyt Life Cycle
     */
    @Override
    protected void onStart() {
        super.onStart();
        //Start Acivity Principale
        startActivity(new Intent(this, MenuActivity.class));
        finish();
    }
}
