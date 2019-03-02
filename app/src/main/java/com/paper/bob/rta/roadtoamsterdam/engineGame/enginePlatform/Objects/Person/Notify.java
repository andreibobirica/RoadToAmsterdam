package com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform.Objects.Person;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform.Objects.Background;
import com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform.EngineGame;
import com.paper.bob.rta.roadtoamsterdam.gameUtils.DataXMLGraberPlatform;

/**
 * Notify è sia un elemento del EngineGame che una classe speciale in quanto lavoro per far vedere
 * al di sopra del personaggio un contrasegno per indicare se è interagibile o no, nel gergo della programmazione
 * del gioco Notified oppure no.
 * Ha anche esso come gli ObjectGame coordinate , immagine.
 * Oltre a questo ma l'immagine riferimento del player in quanto ne deve estrapolare le informazioni.
 */
public class Notify{

    private final int height;
    private final int width;
    private int y;
    private int x;
    private final Bitmap img;
    private Bitmap imgNot;

    private static DataXMLGraberPlatform dg;
    private static Background bgCoord;
    private boolean prima =true;

    /**
     * Costruttore
     */
    public Notify(Bitmap img,int x, int y, int width, int height)
    {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Metodo draw richiamato ogni FPS , incaricato a disegnare su un canvas passato da parametro
     * Il metodo draw disegna il notify con un diley impostato su: int delay = 50+nframe*25;
     * @param c Canvas
     */
    public void draw(Canvas c)
    {
        if(prima)
        {
            prima = false;
            imgNot = dg.getNotifyImage();
        }
        if(x<EngineGame.WIDTH && y <EngineGame.HEIGHT) {
            Rect srcNot = new Rect(0, 0, imgNot.getWidth() - 1, imgNot.getHeight() - 1);
            Rect destNot = new Rect(x+(width/4), y - 150, x + (width-width/4), y);
            c.drawBitmap(imgNot, srcNot, destNot, null);
        }
    }

    /**
     * Metodo update che viene richiamto ogni FPS e serve per modificare i vettori del Notify in caso di spostamento
     */
    public void update()
    {
        this.x += bgCoord.getDX();
        this.y += bgCoord.getDY();
    }

    /**
     * Essendo al di fuori del controllo del EngineGame , ma essendo dipendente univocamente del Personaggio
     * ha bisogno comunque anche lui di dati , i quali se li prende autonomamente dal DataXMLGraberPlatform
     * Metodo set del DataXmlPat
     * @param datag graber
     */
    public static void setDG(DataXMLGraberPlatform datag)
    {dg = datag;}

    /**
     * Essendo in parte anche un elemtno, ha bisogno come tutti di seguire il background, per questo motivo gli si imposta un riferimento
     * ad esso.
     * @param bg
     */
    public static void setBgCoord(Background bg)
    {bgCoord = bg;}

    public void setX(int x)
    {this.x = x;}
    public void setY(int y)
    {this.y = y;}
    public int getX(){return x;}
    public int getY(){return y;}


}
