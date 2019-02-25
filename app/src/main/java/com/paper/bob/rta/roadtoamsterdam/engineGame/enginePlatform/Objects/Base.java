package com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform.Objects;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform.EngineGame;

/**
 * Base è un elemento del EngineGame il quale ha come scopo il bloccare il player dalla caduta eterna verso il basso
 * è dotato di immagine anche se in molti casi non viene vista, immagine trasparente o unicolore
 * Ha un riferimento al Background in quanto lo deve seguire come tutti gli elementi di gioco.
 * Essendo un elemento ha i metodi draw e update richiamati ogni FPS
 */
public class Base extends Ostacolo {
    private Bitmap img;
    private static Background bgCoord;

    public Base(Bitmap res)
    {
        super(res,0, EngineGame.HEIGHT,(EngineGame.HEIGHT+200),(EngineGame.WIDTH*2),1);
        img = res;
    }
    public void draw(Canvas canvas)
    {
        Rect src = new Rect(0,0,img.getWidth()-1, img.getHeight()-1);
        Rect dest = new Rect(x, y, width + x, height + y);
        canvas.drawBitmap(img, src, dest, null);
        if(x<-EngineGame.WIDTH)
        {
            dest = new Rect(x+(EngineGame.WIDTH*2), y,x+EngineGame.WIDTH+width, height+y);
            //dest = new Rect(x,y,(EngineGame.WIDTH*2)+x, height);
            canvas.drawBitmap(img, src, dest, null);
        }
        if(x>0)
        {
            dest = new Rect(x,y,-(EngineGame.WIDTH+x), height);
            canvas.drawBitmap(img, src, dest, null);
        }
    }
    public void update()
    {
        this.x += bgCoord.getDX();
        this.y += bgCoord.getDY();
        if(x<-EngineGame.WIDTH){
            x=0;
        }
        if(x>0){
            x=-EngineGame.WIDTH;
        }
    }
    public static void setBgCoord(Background bg)
    {bgCoord = bg;}

}
