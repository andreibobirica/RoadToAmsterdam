/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.R;
import com.paper.bob.rta.roadtoamsterdam.gameUtils.EnvironmentContainer;

import java.util.ArrayList;

/**
 * Activity GameComposerActivity, questa Activity non ha una view vera e prorpio, o meglio, non dovrebbe mai apparire in quanto ha solo
 * lo scopo di caricare e gestire il passaggio tra le altre View.
 * In pratica questa Acitivity gestisce il cambiamento di EnvirnmentContaine, quindi livelli, e avvia automaticamente ogni parte di essi
 * quali VideoAcitivyt, DialogAcitity, e PlatformAcitivyt.
 */
public class GameComposerActivity extends SoundBackgroundActivity {

    /**
     * Variabile container principale che contiene il primo Environment da esaminare e far eseguire al gioco
     */
    private EnvironmentContainer contPrincipale;
    /**
     * variabili di controllo per verificare le fasi del Environment
     */
    private boolean checkVideo, checkDialogo, checkPlatform = false;
    /**
     * ArrayList contenente tutti i Environment di gioco, in altre parole tutti i livelli possibili;
     */
    ArrayList<EnvironmentContainer> conts;

    /**
     * Variabile contenente la scelta effetuata dal giocatore su che strada intraprendere e che decisioni fare
     * In base a questa cambierà il flusso di gioco e verrano scelti Environment diversi
     */
    private Boolean scelta;

    /**
     * Campo sharedPreferences, contiene le informazioni salvate in un file XML apposito di Android, sul quale si salverà anche il savegame
     */
    private SharedPreferences prefs;

    /**
     * Metodo onCreate richiamato dall'activityLyfeCycle
     * @param savedInstanceState Parametro non uttilizzato, servirebbe per la gestione delle risorse di javaRuntime
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_composer);
        prefs = this.getSharedPreferences("com.paper.bob.rta.roadtoamsterdam", MODE_PRIVATE);
        //Creazione dei contenitori di Ambiente EnviromentContainer
        this.createEnviroments();
        //Ripristino del livello salvato e salvataggio del livello corrente
        this.restoreSaveLevel();
    }

    /**
     * Metodo che ripristina il livello dal savegame.
     * Implementando il concetto di salvataggio, l'id del environmentContainer, del livello per semplificare, viene salvato
     * nelle sharedPreferences, un file XML di android apposito.
     * Il controllo delle sharedPreferences avviene nel menu, questo metodo raccoglie il parametro passato tramite Intent.
     * una volta raccolto l'id del livello, viene inizializzato contPrincipale col livello corretto. successivamente viene sovrascitto
     * il savegame, con le sharedPreferences, per aggiornare il savegame nel caso si volesse iniziare una NUOVA PARTITA.
     */
    private void restoreSaveLevel() {
        //Ripristino Salvataggi
        int level = getIntent().getExtras().getInt("savegame");
        contPrincipale = conts.get(level);
        //Aggiornamento savegame con il livello
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("savegame", level);
        editor.apply();
    }

    /**
     * Metodo senza parametri e senza valori di ritorno.
     * Creato per sfruttare la ricorsione dei metodi.
     * Questo metodo inizializza un array list di Environment con i parametri forniti dal programmatore.
     * In pratica è da qui che si creano tutti gli Environment, quindi si crea la storia e tutti i filoni possibili di gioco.
     */
    private void createEnviroments() {
        conts = new ArrayList<>();
        //Lista EnvirnmentContainer, livelli quindi
        conts.add(new EnvironmentContainer("vid0", null, "padovacasello")); //0
        conts.add(new EnvironmentContainer("vid1", "2d0", "austria")); //1
        conts.add(new EnvironmentContainer("vid2", null, "svizzera")); //2
        conts.add(new EnvironmentContainer(null, null, "padovacasello")); //3
        conts.add(new EnvironmentContainer(null, null, "padovacasello")); //4
        conts.add(new EnvironmentContainer(null, null, "padovacasello")); //5
        conts.add(new EnvironmentContainer(null, null, "padovacasello")); //6
        conts.add(new EnvironmentContainer("fin1", null, null)); //7
        // Inizializzazione del filone narrattivo, impostando per ogni livello le possibili diramazioni.
        //Nel caos esistano delle diramazioni diverse vengono impostati livelli diversi, altrimenti viene
        //assegnato lo stesso livello a entrambe le due diramazioni.
        for (int i = 0; i < conts.size(); i++) {
            if (conts.get(i).getId() == 0) {
                //Se true Austria, se False Svizzera
                conts.get(i).setNext(conts.get(1), conts.get(2));
            }
            //Sempre Germania
            if (conts.get(i).getId() == 1) {
                conts.get(i).setNext(conts.get(2), conts.get(2));
            }
            //Se True FINE, se false Germania
            if (conts.get(i).getId() == 2) {
                conts.get(i).setNext(conts.get(7), conts.get(3));
            }
            if (conts.get(i).getId() == 3) {
                conts.get(i).setNext(conts.get(4), conts.get(4));
            }
            if (conts.get(i).getId() == 4) {
                conts.get(i).setNext(conts.get(5), conts.get(5));
            }
            if (conts.get(i).getId() == 5) {
                conts.get(i).setNext(conts.get(6), conts.get(6));
            }
        }
    }

    /**
     * Metodo onStart richiamato dall'activityLyfeCycle
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Metodo onResume richiamato dall'activityLyfeCycle
     */
    @Override
    protected void onResume() {
        super.onResume();
        /* Inizio il metodo stratgame() per avviare tutte le meccaniche di gioco.*/
        //Viene richiamato ogni volta che un envirnment termina.
        this.startGame();
    }

    /**
     * Metodo richiamato dall'activity Lyfe Cycle, questo metodo viene richiamato perchè si utilizza un Intent per passare un
     * parametro tra due activity, una volta chiasa la activity del dialogo, essa , se necessario, ritorna un parametro tramite intent
     * che è la scelta effettuata dal giocatore.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Request code deve essere uguale a 1, in questo caso l'Intent è stato passatto correttamente e non ci sono stati errori
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                scelta = data.getBooleanExtra("scelta", false);
                Log.i("RTA", "Il valore scelto GameComposer è : " + scelta);
            }
        }
    }

    /**
     * Metodo senza parametri e senza valori di ritorno, che utilizza il concetto di ricorsione.
     * Richiama se stesso ogni volta che un Envirnment Viene completato.
     * Controlla attraverso i check se le varie parti del Environment sono state completate.
     * Una volta avviata ogni parte del environment, ne estrae un'altro dai riferimenti del precedente in base alla scelta.
     * quando tutti gli Envirnment vengono estratti e non ne esistono più, il gioco finisce.
     * Prima controlla la parte video , poi il dialogo, e successivamente la fase platform.
     * Tutte e 3 le fasi sono opzionali, questo significa che un Envirnment può avere indipendentemente qualsiasi di queste parti.
     * Questo permette al Motore di gioco di essere adattabile ad ogni esigenza narrativa.
     */
    public void startGame() {
        //Inizio operazioni di scelta parti del EnvironmentContainer
        if (!checkVideo && contPrincipale.getVideo() != null) {
            checkVideo = true;
            Intent i = new Intent(this, VideoActivity.class);
            i.putExtra("video", contPrincipale.getVideo());
            startActivity(i);
        } else if (!checkDialogo && contPrincipale.getDialogo() != null) {
            checkDialogo = true;
            Intent i = new Intent(this, DialogActivity.class);
            i.putExtra("nomeDialogo", contPrincipale.getDialogo());
            startActivity(i);
        } else if (!checkPlatform && contPrincipale.getPlatform() != null) {
            checkPlatform = true;
            Intent i = new Intent(this, PlatformActivity.class);
            i.putExtra("platform", contPrincipale.getPlatform());
            startActivityForResult(i, 1);
        } else {
            contPrincipale.setScelta(scelta);
            if (contPrincipale.verifyScelta()) {
                //Prelevare il livello successivo
                Log.i("RTA", contPrincipale.toString());
                contPrincipale = new EnvironmentContainer(contPrincipale.getNext());
                //Aggiornamento savegame ocn il livello
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("savegame", contPrincipale.getId());
                editor.apply();
                int savegame = prefs.getInt("savegame", 0);
                Log.i("RTA", "SaveGame Aggiornato:" + savegame);
                //Annullamento dei vari check
                checkPlatform = checkDialogo = checkVideo = false;
                //Ripartire con il metodo ricorsivo
                startGame();
            } else {
                Log.i("RTA", "\n\t@END GAME");
                finishAffinity();
                System.exit(0);
            }
        }
    }

}
