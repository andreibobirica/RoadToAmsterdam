package com.paper.bob.rta.roadtoamsterdam.engine;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Base extends GameObject{
    private Bitmap img;
    private static Background bgCoord;
    private Rect dest;
    public Base(Bitmap res)
    {
        x=0;
        y=EngineGame.HEIGHT-100;
        width = (EngineGame.WIDTH*2);
        height = (EngineGame.HEIGHT+200);
        img = res;
    }

    public void draw(Canvas canvas)
    {
        Rect src = new Rect(0,0,img.getWidth()-1, img.getHeight()-1);
        dest = new Rect(x, y, width+x, height+y);
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

    public Rect getRect()
    {return dest;}

    public static void setBgCoord(Background bg)
    {bgCoord = bg;}
}
