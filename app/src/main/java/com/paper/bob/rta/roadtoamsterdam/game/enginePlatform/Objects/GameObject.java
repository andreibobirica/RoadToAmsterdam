/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Objects;
import android.graphics.Rect;

/**
 * Classe GameObject che contiene le informazioni base di un oggetto in game.
 * Questa classe è Parcelable in quanto dovrà essere passata attraverso più activity nel caso di un saveGame docuto dal onRestoreState() delle activity
 */
public abstract class GameObject{
    //Coordinate della posizione iniziale
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected String tipo;

    /**
     * set Coordinata X
     * @param x coordinata x
     */
    public void setX(int x)
    {
        this.x = x;
    }
    /**
     * set Coordinata Y
     * @param y coordinata y
     */
    public void setY(int y)
    {
        this.y = y;
    }
    /**
     * get Coordinata X
     * @return x coordinata x
     */
    public int getX()
    {
        return x;
    }
    /**
     * get Coordinata Y
     * @return y coordinata y
     */
    public int getY()
    {
        return y;
    }
    /**
     * get Altezza
     * @return height Altezza
     */
    public int getHeight()
    {
        return height;
    }
    /**
     * get width
     * @return width larghezza
     */
    public int getWidth()
    {
        return width;
    }
    /**
     * get Rect Rettangolo
     * @return Rect Crea un Oggetto Rect inizializzato con le propietà del GameObject.
     */
    public Rect getRectangle()
    {return new Rect(x, y, x+width, y+height);}
    /**
     * Metodo che imposta la larghezza del GameObject
     * @param w larghezza del gameobject
     */
    public void setWidth(int w)
    {this.width = w;}

    /**
     * Metodo che imposta L'altezza del GameObject
     * @param h altezza del gameobject
     */
    public void setHeight(int h)
    {this.height = h;}

    /**
     * Metodo che setta il tipo della classe
     * @param s
     */
    public void setTipo(String s)
    {tipo = s;}

    /**
     * Metodo che ritorna il tipo della classe, nelle future estensioni cambiano
     * @return Tipo della classe
     */
    public String getTipo()
    {return tipo;}
}