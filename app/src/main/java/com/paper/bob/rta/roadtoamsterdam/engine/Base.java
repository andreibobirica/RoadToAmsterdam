package com.paper.bob.rta.roadtoamsterdam.engine;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Base {
    private Bitmap img;
    private static Background bgCoord;
    private int x, y;

    public Base(Bitmap res)
    {
        x=0;
        y=EngineGame.HEIGHT+100;
        img = res;
    }
    public void draw(Canvas canvas)
    {
        Rect src = new Rect(0,0,img.getWidth()-1, img.getHeight()-1);
        Rect dest = new Rect(x,y,EngineGame.WIDTH+x, EngineGame.HEIGHT+y+200);
        canvas.drawBitmap(img, src, dest, null);
        if(x<EngineGame.WIDTH)
        {
            dest = new Rect(x,y,(EngineGame.WIDTH*2)+x, EngineGame.HEIGHT+y+200);
            canvas.drawBitmap(img, src, dest, null);
        }
        if(x>0)
        {
            dest = new Rect(x,y,-(EngineGame.WIDTH+x), EngineGame.HEIGHT+y+200);
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
