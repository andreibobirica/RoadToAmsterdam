package com.paper.bob.rta.roadtoamsterdam.engine;

import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.engine.Person.Player;

import java.util.ArrayList;

public class Controller {

    private Player pl;
    private ArrayList<GameObject> objColl;

    //VETTORI DI MOVIMENTO
    private int dx = 10;
    private int dy = 5;
    private int dDown = 2;

    private boolean moving;
    private boolean mRight=false,mLeft=false,mUp=false,mDown=true;
    private boolean uping=false;

    public int getDX(){return dx;}
    public int getDY(){return dy;}
    public int getDDown(){return dDown;}

    public void setMRight(boolean m)
    {mRight = m;}
    public void setMLeft(boolean m)
    {mLeft = m;}
    public void setMDown(boolean m)
    {mDown = m;}
    public void setMUp(boolean m)
    {
        if(!uping) {
            mUp = m;
            mDown=false;
            moving = m;
            uping = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    mUp = false;
                    mDown=true;
                    uping= false;
                }
            }, 500);
        }
    }

    public boolean getMDown()
    {return (!verCol(0,dy)&& mDown);}
    public boolean getMRight()
    {return (!verCol(dx,0)&& mRight);}
    public boolean getMLeft()
    {return (!verCol(-dx,0)&& mLeft);}
    public boolean getMUp()
    {return (!verCol(0,-dy)&& mUp);}

    public void setPlayer(Player pl)
    {this.pl = pl;}
    public void setObjColl(ArrayList<GameObject> o)
    {this.objColl = o;}

    /**
     * Metodo che confronta due Oggetti Rect e verifica se è avvenuta una collisione fra i due.
     * Il metodo ritorna un valore booleano che specifica se è avvenuta una collisione tra i due.
     * Non specifica su che lato o da che parte, solo che le aree dei due rettangoli si sono sovrapposte.
     * @param a GameObject a da confrontare con b
     * @param b GameObject b da confrontare con a
     * @return valore booleano, se true significa che è avvenuta una collisione, se false non è avvenuta una collisione
     */
    private boolean collision(GameObject a, GameObject b)
    {return Rect.intersects(a.getRectangle(), b.getRectangle());}
    /**
     * Metodo che confronta tutti gli GameObject Fisici con il Player, e ritorna se in un ipotetico movimento collidono oppure no.
     * Il metodo contronta tutti gli GameObject objCol, oggetti preventivamente creati che rappresentato tutti gli GameOnject Fisici e che possono collidere.
     * Il ipotetico movimento viene ipotizzato tramite i due vettori dx e dy, che sono parametri aggiuntivi alla effettiva posizione del Player.
     * Se il Player con i parametri aggiuntivi effetua una collisione, non gli viene permesso di muoversi, in pratica viene ritornato True, cioè collisione effetuata.
     * Se return true, effetua una collisione, se false no.
     * Sarà poi il Controller che guardando il risultato permetterà al Player di muoversi oppure no.
     * @param dx Vettore di movimento sull'asse X, viene aggiunto in caso di ipotetico movimento a sinistra o a destra
     * @param dy Vettore di movimento sull'asse Y, viene aggiunto in caso di ipotetico movimento in giù o su
     * @return valore booleano che indica se nel ipotetico movimento avviene una collisione o meno.
     */
    private boolean verCol(int dx, int dy)
    {
        for(GameObject g : objColl)
        {
            Player pl2 = new Player(pl);
            pl2.setX(pl.getX()+dx);
            pl2.setY(pl.getY()+dy);
            pl2.setWidth(pl.getWidth()+dx);
            pl2.setHeight(pl.getHeight()+dy);
            return(collision(pl2,g));
        }
        return false;
    }

    public String toString()
    {return "bool: "+moving+mLeft+mRight+mUp;}
}
