/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Objects.Person;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Objects.Ostacolo;

/**
 * Classe Personaggio, estensione di Ostacolo, concettualmente a differenza dell'ultimo il Personaggio ha anche la posibilità
 * di essere Notify, quindi di essere interagibile e ha anche il riferimento a un dialogo, che farà attivare una activity dialogo.
 * Inoltre ha un oggetto Notify, che rappresenta una immagine sopra il personaggio che indica che esso è interagibile
 */
public class Personaggio extends Ostacolo {

    /**String contenente il riferimento al Dialogo*/
    private String dialogo;
    /**Variabile booleana che indica se è notofy oppure no, se è interagibile*/
    private boolean notify = false;
    /**Oggetto Notofy che indica l'icona del interagibile di un personaggio*/
    private Notify not;

    /**
     * metodo costruttore che dati tutti i parametri di un ostacolo, e in più anche dialogo e notify, crea tutte le istanze.
     * Inoltre di base se è passato il valore true su notify viene messo true anche il parametro fisico, altrimenti no.
     * @param img Img di riferimento
     * @param x coordinata x
     * @param y coordinata y
     * @param height larghezza
     * @param width altezza
     * @param nframe numero di frame della propria animaziokne
     * @param dialogo Riferimento al dialogo
     * @param notify variabile booleana che indica se è interagibile oppure no
     */
    public Personaggio(Bitmap img, int x, int y, int height, int width, int nframe,String dialogo, boolean notify)
    {
        super(img,x,y,height,width,nframe);
        //tipo
        this.setTipo("Personaggio");
        //Dialogo
        this.dialogo = dialogo;
        //Se è Notified, allora è anche fisico, se non è Notified, non è fisico
        if(notify) {
            this.setNotify(true);
            this.setFisico(true);
            not = new Notify(img, getX(), getY(), getWidth(), getHeight());
        }
        else
        {
            this.setNotify(false);
            this.setFisico(false);
        }
    }

    /**
     * Metodo Draw richiamato ciclicamente dal macro metodo draw del EngineGame
     * Si occupa di diseganre il Personaggio
     * @param c Ogetto Canvas su cui disegnare il Personaggio
     */
    public void draw(Canvas c)
    {
        super.draw(c);
        if(notify)not.draw(c);
    }

    /**
     * Metodo Update richiamato ciclicamente che aggiorna il personaggio
     */
    public void update()
    {
        super.update();
        if(notify)not.update();
    }

    /**
     * Metodo set Notify che imposta la variabile booleana notify
     * @param n variabile booleana notify
     */
    public void setNotify(boolean n) {notify = n;}

    /**
     * Metodo get che ritorna la avriabile booleana notify
     * @return notify variabile booleana che indica se è interagibile oppure no
     */
    public boolean getNotify()
    {return notify;}

    public String getDialogo()
    {return dialogo;}

    /**
     * Metodo che ritorna il riferimento al Oggetto Notify
     * @return Riferimento al oggetto notify il quale viene ritornato
     */
    public Notify getNot() {
        return not;
    }
}
