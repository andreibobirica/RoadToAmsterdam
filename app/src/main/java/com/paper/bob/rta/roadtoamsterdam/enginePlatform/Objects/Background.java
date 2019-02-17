package com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

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
        x=-EngineGame.WIDTH/2;

        coloreSfondo= new Paint();
        coloreSfondo.setColor(Color.WHITE);
        coloreSfondo.setStyle(Paint.Style.FILL);
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
        //Movimento del Background in base al Sensore
        float[] datiMoveSensor = moveBySensor(controller.getSensorY(),moved);
        moved= datiMoveSensor[1];
        //Modifica
        x+=datiMoveSensor[0];

        //Movimento del Background in base al Player
        int xm = ((pl.getX()+pl.getWidth())+pl.getX())/2;
        if(xm>(EngineGame.WIDTH/4)*3)
        {dx = -pl.getDX();}
        else if((xm)<(EngineGame.WIDTH/4))
        {dx = pl.getDX();}
        else { dx=0;}

        int ym = ((pl.getY()+pl.getHeight())+pl.getY())/2;
        if(ym > (EngineGame.HEIGHT/3)*2)
        {dy = -pl.getDY();}
        else if(ym<(EngineGame.HEIGHT/3))
        {dy = pl.getDY();}
        else { dy=0;}

        //Modifica
        x+=this.dx;
        y+=this.dy;

    }

    /**
     * Metodo moveBySesnsor che implementa il movimento dello sfondo in base al sensore accelerometro
     * Tramire i due parametri, il metodo capisce di quanto lo sfondo si è già spostato all'inclinazione e di quanto lo deve spostare ancora.
     * l'algoritmo fa in modo che lo sfondo si possa muovere solo leggermente a destra e a sinistra, di poco e in base all'inclinazione
     * con una velocità differente.
     * @param sensorY parametro che indica di quanto è l'inclinazione sull'asse Y del cellulare(Corrisponde all'asse X del gioco)
     * @param moved parametro che indica di quanto si + già spostato il background, in questo modo il suo movimento non è infinito
     * @return array contenente nel primo valore il vettore di movimento, se possibile ancora il movimento un valore numerico, se non possibile 0
     * Nel secondo valore invece è contenuto la variabile moved aggiornata con l'effettiva distanza che il backgrond ha fatto.
     */
    private float[] moveBySensor(float sensorY, float moved)
    {
        float ret[] = new float[2];
        ret[0] = 0;//Di quanto si deve muovere
        ret[1] = moved;//Di quanto si è già mosso
        int vettore = (EngineGame.WIDTH/300)*(int) Math.abs(sensorY);
        int movMax = EngineGame.WIDTH/20;

        if(moved < movMax && moved > -movMax)
        {
            if(sensorY<0)
            {
                ret[0]=+vettore;
                ret[1]+=vettore;
            }
            else
            {
                ret[0]=-vettore;
                ret[1]-=vettore;
            }
        }
        else if(moved>=movMax)
        {
            if(sensorY>0)
            {
                ret[0]=-vettore;
                ret[1]-=vettore;
            }
        }
        else
        {
            if(sensorY<0)
            {
                ret[0]=+vettore;
                ret[1]+=vettore;
            }
        }
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


    public void setPlayer(Player pl){this.pl = pl;}
    public void setController(Controller controller) {
        this.controller = controller;
    }
}