package com.paper.bob.rta.roadtoamsterdam.engine;
import android.graphics.Rect;

public abstract class GameObject {
    //Coordinate della posizione iniziale
    protected int x;
    protected int y;
    protected int width;
    protected int height;

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
     * Metodo toString() che ritorna una stringa con tutte le informazioni principali e le propietà dell'oggetto.
     * @return info info Oggetto
     */
    @Override
    public String toString() {
        String info = "";
        info += "x = "+x+"\t";
        info += "y = "+y+"\t";
        info += "width = "+width+"\t";
        info += "height = "+height+"\t";
        return info;
    }
}