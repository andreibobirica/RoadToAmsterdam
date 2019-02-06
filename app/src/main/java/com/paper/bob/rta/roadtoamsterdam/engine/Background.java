package com.paper.bob.rta.roadtoamsterdam.engine;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Background {

    private Bitmap image;
    private int x, y, dx, dy;

    /**
     * Costruttore della classe Background, questo costruttore inizializza un background ricevendo come parametro una img Bitmap
     * @param res res, sta per resource, cioè l'immagine Bitmap da stampare sul kanvas come background
     */
    public Background(Bitmap res,int dx,int dy)
    {
        this.dx = -10;
        this.dy = -5;
        image = res;
        y=-5;
        x=-10;
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
        Rect dest = new Rect(x,y,x+5500, EngineGame.HEIGHT+y+200);
        canvas.drawBitmap(image, src, dest, null);
    }
    /**
     Metodo update che viene richiamato ogni volta che si deve updatare l'oggetto Ostacolo, cioè ogni frame
     Senza parametri e senza valori di ritorno.
     */
    public void update()
    {
        //Parametri provisori
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
        //Modifica
        x+=this.dx;
        y+=this.dy;

    }

    public int getDX()
    {return dx;}
    public int getDY()
    {return dy;}

    public int getY()
    {return y;}
    public int getX()
    {return x;}

}