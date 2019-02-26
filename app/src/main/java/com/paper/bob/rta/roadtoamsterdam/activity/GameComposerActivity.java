package com.paper.bob.rta.roadtoamsterdam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.paper.bob.rta.roadtoamsterdam.R;
import com.paper.bob.rta.roadtoamsterdam.engineGame.EnvironmentContainer;

import java.util.ArrayList;

public class GameComposerActivity extends AppCompatActivity {

    /**Variabile container principale che contiene il primo Environment da esaminare e far eseguire al gioco*/
    private EnvironmentContainer contPrincipale;
    /**variabili di controllo per verificare le fasi del Environment*/
    private boolean checkVideo, checkDialogo, checkPlatform = false;
    /**ArrayList contenente tutti i Environment di gioco, in altre parole tutti i livelli possibili;*/
    ArrayList<EnvironmentContainer> conts;
    /**Barra di caricamento*/
    ProgressBar bar;
    /**Variabile contenente la scelta effetuata dal giocatore su che strada intraprendere e che decisioni fare
     * In base a questa cambierà il flusso di gioco e verrano scelti Environment diversi*/
    private Boolean scelta;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_composer);
        //Creazione dei contenitori di Ambiente EnviromentContainer
        this.createEnviroments();
    }

    /**
     * Metodo senza parametri e senza valori di ritorno.
     * Creato per sfruttare la ricorsione dei metodi.
     * Questo metodo inizializza un array list di Environment con i parametri forniti dal programmatore.
     * In pratica è da qui che si creano tutti gli Environment, quindi si crea la storia e tutti i filoni possibili di gioco.
     */
    private void createEnviroments() {
        conts = new ArrayList<>();
        conts.add(new EnvironmentContainer(null,null,"padovacasello")); //0
        for(int i = 0; i < conts.size(); i++)
        {
            //if(conts.get(i).getId() == 0)
            //{conts.get(i).setNext(conts.get(1),conts.get(2));}
        }
        //Si parte dal primo Enviroment
        contPrincipale = conts.get(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /* Inizio il metodo stratgame() per avviare tutte le meccaniche di gioco.*/
        //Viene richiamato ogni volta che un envirnment termina.
        this.startGame();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                scelta = data.getBooleanExtra("scelta",false);
                Log.i("RTA","Il valore scelto GameComposer è : "+scelta);
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
    public void startGame()
    {
        if(!checkVideo && contPrincipale.getVideo() != null)
        {
            checkVideo=true;
            Intent i = new Intent(this, VideoActivity.class);
            i.putExtra("video", contPrincipale.getVideo());
            startActivity(i);
        }
        else if(!checkDialogo && contPrincipale.getDialogo() != null)
        {
            checkDialogo=true;
            Intent i = new Intent(this, DialogBackgroundActivity.class);
            i.putExtra("dialogo", contPrincipale.getDialogo());
            startActivity(i);
        }
        else if (!checkPlatform && contPrincipale.getPlatform() != null)
        {
            checkPlatform=true;
            Intent i = new Intent(this, PlatformMainBackgroundActivity.class);
            i.putExtra("platform", contPrincipale.getPlatform());
            startActivityForResult(i,1);
        }
        else
        {
            contPrincipale.setScelta(scelta);
            if(contPrincipale.verifyScelta())
            {
                Log.i("RTA",contPrincipale.toString());
                contPrincipale = new EnvironmentContainer(contPrincipale.getNext());
                checkPlatform = checkDialogo = checkVideo = false;
                startGame();
            }
            else
            {
                Log.i("RTA","\n\t@END GAME");
                finishAffinity();
                System.exit(0);
            }
        }
    }
}
