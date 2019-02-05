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
        this.dx = -20;
        this.dy = dy;
        image = res;
        y=0;
        x=0;
    }
    /**
     Metodo draw che richiamato da EngineGame.draw(Canvas c) disegna sul Canvas c la propietà IMG , cioè l'immggine.
     Questo metodo non ha valori di return in quando non fornisce dati, ma attua solo l'azione di disegnare se stesso su un canvas
     fornito.
     @param canvas Canvas oggetto canvas su cui si deve disegnare l'immmagine img, che sta al background.
     */
    public void draw(Canvas canvas)
    {
/*
        Rect src = new Rect(0,0,image.getWidth(), image.getHeight());
        Rect dest = new Rect(x,y,image.getWidth()+1, EngineGame.HEIGHT+1);
        canvas.drawBitmap(image, src, dest, null);
*/
        canvas.drawBitmap(image,x,y,null);
    }
    /**
     Metodo update che viene richiamato ogni volta che si deve updatare l'oggetto Ostacolo, cioè ogni frame
     Senza parametri e senza valori di ritorno.
     */
    public void update()
    {
        int limite = 4000;
        x+=this.dx;
        if(x==-(limite)){
            this.dx = -(this.dx);
        }
        else if(x == 0)
        {
            this.dx = -(this.dx);
        }

    }

    public int getDX()
    {return dx;}
    public int getDY()
    {return dy;}

}