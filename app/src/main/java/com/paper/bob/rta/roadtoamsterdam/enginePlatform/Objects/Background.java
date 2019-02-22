package com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Controller;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.EngineGame;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Person.Player;

public class Background {

    private Bitmap image;
    private int x, y, dx, dy;
    private Player pl;
    private Paint coloreSfondo;
    private Controller controller;
    //Variabile per gestire movimento sfondo in base al sensore
    private float moved = 0;

    /**
     * Costruttore della classe Background, questo costruttore inizializza un background ricevendo come parametro una img Bitmap
     * @param res res, sta per resource, cioè l'immagine Bitmap da stampare sul kanvas come background
     */
    public Background(Bitmap res,int dx,int dy)
    {
        this.dx = 0;
        this.dy = 0;
        image = res;
        y=-EngineGame.HEIGHT*2;
        x=-EngineGame.WIDTH*2;

        coloreSfondo= new Paint();
        coloreSfondo.setColor(Color.WHITE);
        coloreSfondo.setStyle(Paint.Style.FILL);
    }
    /**
     Metodo draw che richiamato da EngineGame.draw(Canvas c) disegna sul Canvas c la propietà IMG , cioè l'immggine.
     Questo metodo non ha valori di return in quando non fornisce dati, ma attua solo l'azione di disegnare se stesso su un canvas
     fornito.
     Lo sfondo ha una risoluzione di 10u/5u, dove l'unita di misura u è la grandezza dello schermo.
     @param canvas Canvas oggetto canvas su cui si deve disegnare l'immmagine img, che sta al background.
     */
    public void draw(Canvas canvas)
    {
        //Log.i("RTA",EngineGame.WIDTH+" "+EngineGame.HEIGHT);
        Rect src = new Rect(0,0,image.getWidth(), image.getHeight());
        Rect dest = new Rect(x,y,x+EngineGame.WIDTH*8, EngineGame.HEIGHT*3+y);
        canvas.drawPaint(coloreSfondo);
        canvas.drawBitmap(image, src, dest, null);
    }
    /**
     Metodo update che viene richiamato ogni volta che si deve updatare l'oggetto Ostacolo, cioè ogni frame
     Senza parametri e senza valori di ritorno.
     */
    public void update()
    {
        verifyMovementPlayer();
        y+=this.dy;
        x+=this.dx;
    }

    public boolean verifyMovementPlayer()
    {
        boolean ret = true;
        //Movimento del Background in base al Player
        int xm = ((pl.getX()+pl.getWidth())+pl.getX())/2;
        if(xm>(EngineGame.WIDTH/4)*3)
        {dx = -pl.getDX();ret=false;}
        else if((xm)<(EngineGame.WIDTH/4))
        {dx = pl.getDX();ret=false;}
        else { dx=0;}

        int ym = ((pl.getY()+pl.getHeight())+pl.getY())/2;
        int ymb = (pl.getY()+pl.getHeight());
        if(ymb > EngineGame.HEIGHT)
        {dy = -pl.getDY();ret=false;}
        else if(ym<(EngineGame.HEIGHT/3))
        {dy = pl.getDY();ret=false;}
        else { dy=0;}
        return ret;
    }

    public int getDX()
    {return dx;}
    public int getDY()
    {return dy;}
    public int getY()
    {return y;}
    public int getX()
    {return x;}
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}


    public void setPlayer(Player pl){this.pl = pl;}
    public void setController(Controller controller) {
        this.controller = controller;
    }



}