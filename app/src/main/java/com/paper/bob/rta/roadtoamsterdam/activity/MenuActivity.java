/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paper.bob.rta.roadtoamsterdam.R;

/**
 * Acitivity Adebita alla gestione di un menu, il menu iniziale, con il quale si può gestire il caricamento del livello salvato
 * oppure di una nuova partita.
 * Contiene inoltre anche altre funzioni quali lo share, riferimento alla relazione e altre caratteristiche della app
 */
public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**Campo sharedPreferences che indica un file XML di Android uttilizzato per salvare il savegame*/
    private SharedPreferences prefs;
    /**campo riferimento al button nuova partita*/
    private Button btnNuovaPartita;
    /**campo riferimento al button carica partita*/
    private Button btnCarPartita;

    /**
     * Metodo onCreate richiamato automaticamente dal ActivityLyfeCycle
     * @param savedInstanceState parametro non uttilizzato , usato dal Android Runtime in caso di necessita
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //Inizializzazione del sharedPreferences
        prefs = this.getSharedPreferences("com.paper.bob.rta.roadtoamsterdam", MODE_PRIVATE);
        //Inizializzazione Button
        btnCarPartita = findViewById(R.id.carPartita);
        btnNuovaPartita= findViewById(R.id.nuovaPartita);
        //Riferimento al context
        final Context c = getApplicationContext();
        //Listener per il pulsante carica partita
        btnCarPartita.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(c,"Caricamento In Corso",Toast.LENGTH_LONG).show();
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                launchSaveGame();
                            }
                        },
                        5
                );
            }
        });
        //Listener per il pulsante nuova partita
        btnNuovaPartita.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(c,"Caricamento In Corso",Toast.LENGTH_LONG).show();
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                launchNewGame();
                            }
                        },
                        5
                );
            }
        });

        //Inizializzazione parametri standard del menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    /**Metodo onResume richiamato automaticamente dal ActivityLyfeCycle*/
    @Override
    public void onResume()
    {
        super.onResume();
        //Controllo se esiste un savegame, e in caso appare o no il pulsante carica partita
        if(getSaveData() == 0)
        {btnCarPartita.setEnabled(false);}
    }

    /**
     * Metodo che verifica se esiste o no un savegame memorizzato dentro le sharedPreferences
     * @return valore intero contenente l'id del livello memorizzato dentro il savegame
     * Nel caso non ci sia nessun livello memorizzato ritorna 0, cioè il primo livello.
     */
    public int getSaveData()
    {
        // use a default value using new Date()
        int savegame = prefs.getInt("savegame", 0);
        Log.i("RTA","Level save: n."+ savegame);
        return savegame;
    }

    /**Metodo che lancia l'activity Platform con parametro Intent id del livello memorizzato nel savegame*/
    private void launchSaveGame() {
        Intent intent = new Intent(this, GameComposerActivity.class);
        intent.putExtra("savegame", getSaveData());
        startActivity(intent);
    }

    /**
     * Metodo che lancia l'activity Platform con parametro Intent id del livello 0, cioè il primo livello
     */
    private void launchNewGame() {
        Intent intent = new Intent(this, GameComposerActivity.class);
        intent.putExtra("savegame", 0);
        startActivity(intent);
    }

    /**
     * Ovveride del metodo onBackPressed per non chiadere il menu laterale in caso fosse aperto , oppure chiadere l'aplicazione
     * in caso contrario
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Metodo che crea vari collegamenti nel menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Metodo che crea vari collegamenti nel menu
     * In particolare gestisce i contenuti nel menu laterale, e le relative azioni da eseguire nel caso ne se selezioni uno
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            Log.i("RTA","navShare");
            String shareSub = "ShareSub Soggetto del share";
            String shareBody = "ShareBody body corpo del share";
            Intent in = new Intent(Intent.ACTION_SEND);
            in.putExtra(Intent.EXTRA_SUBJECT,shareSub);
            in.putExtra(Intent.EXTRA_TEXT,shareBody);
            in.setType("text/plain");
            startActivity(Intent.createChooser(in,"Share using"));
        }
        else if(id == R.id.nav_relazione)
        {
            Log.i("RTA","naRelazione");
            Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
