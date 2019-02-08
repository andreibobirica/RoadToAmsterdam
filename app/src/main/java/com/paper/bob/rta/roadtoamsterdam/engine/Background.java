package com.paper.bob.rta.roadtoamsterdam.engine;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.engine.Person.Player;

public class Background {

    private Bitmap image;
    private int x, y, dx, dy;
    private Player pl;

    /**
     * Costruttore della classe Background, questo costruttore inizializza un background ricevendo come parametro una img Bitmap
     * @param res res, sta per resource, cioè l'immagine Bitmap da stampare sul kanvas come background
     */
    public Background(Bitmap res,int dx,int dy)
    {
        this.dx = 0;
        this.dy = 0;
        image = res;
        y=-EngineGame.HEIGHT;
        x=-EngineGame.WIDTH/2;
    }
    /**
     Metodo draw che richiamato da EngineGame.draw(Canvas c) disegna sul Canvas c la propietà IMG , cioè l'immggine.
     Questo metodo non ha valori di return in quando non fornisce dati, ma attua solo l'azione di disegnare se stesso su un canvas
     fornito.
     @param canvas Canvas oggetto canvas su cui si deve disegnare l'immmagine img, che sta al background.
     */
    public void draw(Canvas canvas)
    {

        Rect src = new Rect(0,0,image.getWidth(), image.getHeight());
        Rect dest = new Rect(x,y,x+EngineGame.WIDTH*8, EngineGame.HEIGHT*2+y);
        canvas.drawBitmap(image, src, dest, null);
    }
    /**
     Metodo update che viene richiamato ogni volta che si deve updatare l'oggetto Ostacolo, cioè ogni frame
     Senza parametri e senza valori di ritorno.
     */
    public void update()
    {
        /*
        //Parametri provisori Movimento Di Crociera di DEBUG
        int limite = 4000;
        int limiteY = 200;
        //Controllo sul movimento provisorio
        if(x==-(limite))
        {this.dx = -(this.dx);}
        else if(x == 0)
        {this.dx = -(this.dx);}
        if(y==-(limiteY))
        {this.dy = -(this.dy);}
        else if(y == 0)
        {this.dy = -(this.dy);}
        */

        int xm = ((pl.getX()+pl.getWidth())+pl.getX())/2;
        if(xm>(EngineGame.WIDTH/4)*3)
        {dx = -pl.getDX();}
        else if((xm)<(EngineGame.WIDTH/4))
        {dx = pl.getDX();}
        else { dx=0;}

        int ym = ((pl.getY()+pl.getHeight())+pl.getY())/2;
        if(ym > (EngineGame.HEIGHT/4)*3)
        {dy = -pl.getDY();}
        else if(ym<(EngineGame.HEIGHT/4))
        {dy = pl.getDY();}
        else { dy=0;}

        //Modifica
        x+=this.dx;
        y+=this.dy;

    }

    public int getDX()
    {return dx;}
    public int getDY()
    {return dy;}

    public void setPlayer(Player pl){this.pl = pl;}

    public int getY()
    {return y;}
    public int getX()
    {return x;}

}