/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Objects;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Controller;
import com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.EngineGame;
import com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Objects.Person.Player;

/**
 * Classe Background che è un elemento del EngineGame, è lo sfondo del platformgame, è lo sfondo da visualizzare.
 * Essendo l'elemento in game più grande è anche il più difficile da gestire.
 * Esso ha i campi che ne definiscono le coordinate, un riferimento al player il quale deve seguire per un movimento
 * corretto in tutta la mappa.
 * Essendo un elemento è dotato di metodi update e draw richiamati all'esterno ogni FPS
 */
public class Background {

    /*Campi del background*/
    /**Immaggine*/
    private Bitmap image;
    /**Coordinate e vettori di movimento*/
    private int x, y, dx, dy;
    /**Riferimento al player da seguirne i movimenti*/
    private Player pl;
    /**Colore sfondo , impostato su bianco*/
    private Paint coloreSfondo;
    private Controller controller;


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
     Lo sfondo ha una risoluzione di 8u/3u, dove l'unita di misura u è la grandezza dello schermo.
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
     Il metodo verifica anche ogni volta se il player ha ancora la possibilità di movimento.
     Fa partire a ripetizione anche il metodo verifyMovementPlayer
     */
    public void update()
    {
        verifyMovementPlayer();
        y+=this.dy;
        x+=this.dx;
    }

    /**
     * Metodo che serve a verificare lo spostamento del player, ne segue i movimenti e la sua posizione.
     Imposta i limiti entro i quali il player si può muovere nella superficie dello schermo, superati i quali
     viene automaticamente fatto muovere il Background per compesare al movimento del player.
     Il vettore background -dx e il vettore player dx si compensano azzerandosi e il player rimane fermo, muovendo solamente tutti
     gli elementi del gioco, seguendo tutti il background.
     * @return valore booleano che indica il possibile movimento del player
     */
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
        {dy = -pl.getDDown();ret=false;}
        else if(ym<(EngineGame.HEIGHT/3))
        {dy = pl.getDY();ret=false;}
        else { dy=0;}
        return ret;
    }

    /**
     * Metodo set che  serve per settare il riferimento del player da cui prendere le coordinate di movimento
     * @param pl Player pl
     */
    public void setPlayer(Player pl){this.pl = pl;}

    /**
     * Metodo getDX che restituisce il vettore DX
     * @return vettore DX
     */
    public int getDX()
    {return dx;}
    /**
     * Metodo getDX che restituisce il vettore Dy
     * @return vettore DY
     */
    public int getDY()
    {return dy;}
    /**
     * Metodo che restituisce la coordinata Y
     * @return coordinata Y
     */
    public int getY()
    {return y;}
    /**
     * Metodo che restituisce la coordinata X
     * @return coordinata X
     */
    public int getX()
    {return x;}

    /**
     * Metodo che setta la coordinata X
     * @param x coordinata X
     */
    public void setX(int x){this.x = x;}
    /**
     * Metodo che setta la coordinata Y
     * @param y coordinata Y
     */
    public void setY(int y){this.y = y;}

    /**
     * Metodo che serve per settare il riferimento al Controller del EngineGame
     * @param controller Riferimento del COntroller del EngineGame
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Metodo che serve per prendere un bitmap IMG che rappresenta lo sfondo del bg
     * @return Bitmap sfondo
     */
    public Bitmap getImage() {
        return image;
    }
}