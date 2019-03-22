
/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Objects.Person;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Controller;

/**
 * Classe Player, classe estate da Personaggio, la quale ha in più dell'ultima riferimenti a più Bitmap, un riferimento ad un Controller e
 * dei vettori di movitmento.
 * Intermini concreti il Player si muove , quindi grazie a diverse Bitmap di animazione appaiono animazioni diverse.
 * Il Player si muove grazie al Controller, che gli Permette di muoversi.
 * Il Player si muove grazie ai vettori, che impostati ed aggiornati costantaneamente gli indicano di quanto e come si possono muovre
 */
public class Player extends Personaggio{

    //Varibili Bitmap che comprendono le animazioni di gioco
    private Bitmap leftAnim;
    private Bitmap rightAnim;
    private Bitmap jumpLAnim;
    private Bitmap jumpRAnim;
    private Bitmap jumpAnim;
    private Bitmap img;
    //0 normal ; 1 left ; 2 right ; 3updown Left; 4 updown Right; 5 up normal
    private int cambioImg = 0;

    //Riferimento al controller che fa muovere il Player
    private Controller control;

    //VETTORI DI MOVIMENTO
    private int dx;
    private int dy;
    private int dDown;

    /**
     * Metodo Costruttore che costruisce fornendo i parametri un oggetto Player
     */
    public Player(Bitmap img, int x, int y, int height, int width, int nframe) {
        super(img, x, y, height, width, nframe, null, false);
        setTipo("Player");
        this.img = img;
        setFisico(true);
        setNotify(false);
        int delay2 = 50+nframe*10;
        getAnimation().setDelay(delay2);
    }


    /**
     * Metodo update richiamato dal macro elemento update che viene richimato ciclicamente e che aggiorna le posizioni del Player
     * insieme ai suoi vettori e nel metodo update viene controllato se sono possibili i movimenti.
     * è il Controller che concede i movimenti, se li concede il Player si muove, se no sta fermo, in base al movimento Ci sono animaizoni
     * Diverse
     */
    public void update()
    {
        super.update();

        dx = control.getDX();
        dy = control.getDY();
        dDown = control.getDDown();
        boolean up = false;
        boolean down = false;
        //Controllo se in basso o in alto e DownPerfect
        if(control.getMDown())
        {   /*Se in down*/
            y+=dDown;
            down=true;
        }
        else if(control.getMUp())
        {   /*Se in UP*/
            y+=-dy;
            up = true;
        }
        else
        {
            y+=control.getMDownPerfect();
        }


        //CONTROLLO  se a destra o a sinistra
        if (control.getMRight())
        {
            x += dx;
            if (up && cambioImg!=3) {
                cambioImg = 3;
                this.setImage(jumpRAnim);
            } else if(cambioImg!=1 && !down && !up) {
                cambioImg = 1;
                this.setImage(rightAnim);
            }else if (down && cambioImg!=3) {
                cambioImg = 3;
                this.setImage(jumpRAnim);
            }
        }
        else if (control.getMLeft())
        {
            x += -dx;
            if (up && cambioImg!=3) {
                cambioImg = 3;
                this.setImage(jumpLAnim);
            } else if(cambioImg!=1 && !down && !up) {
                cambioImg = 1;
                this.setImage(leftAnim);
            }else if (down && cambioImg!=3) {
                cambioImg = 3;
                this.setImage(jumpLAnim);
            }
        }
        else if(control.getStopRLUD())
        {
            control.stopRL();
            if(cambioImg != 0) {
                cambioImg = 0;
                this.setImage(img);
            }
        }
        else
        {
            if(cambioImg!=5) {
                cambioImg=5;
                this.setImage(jumpAnim);
                Log.i("RTA","up");
            }
        }

    }

    /**
     * Metodo draw che disegna il Player sul Cnìanvas
     * @param canvas Canvas su cui disegnare il Player
     */
    public void draw(Canvas canvas)
    {super.draw(canvas);}

    /**
     * Metodo set che serve per settare il Riferimento del Controller
     * @param control Controller che darà i permessi di muoversi al Player
     */
    public void setController(Controller control)
    {
        this.control = control;
    }

    /**
     * Metodo get DX che ritorna il vettore DX del Player
     * @return dx vettore dx per il movimento dx e sx
     */
    public int getDX(){return dx;}
    /**
     * Metodo get DX che ritorna il vettore DX del Player
     * @return dy vettore dy per il salto
     */
    public int getDY(){return dy;}
//Metodo set PER le immaggini
    public void setLeftAnim(Bitmap i){leftAnim = i;}
    public void setRightAnim(Bitmap i){rightAnim = i;}
    public void setJumpLAnim(Bitmap i){jumpLAnim = i;}
    public void setJumpRAnim(Bitmap i){jumpRAnim = i;}
    public void setUpAnima(Bitmap upAnima) {jumpAnim= upAnima;}

    /**
     * Metodo getDDown che ritorna il vettore ddown vettore per la caduta
     * @return vettore ddown per la caduta
     */
    public int getDDown() {
        return dDown;
    }


}