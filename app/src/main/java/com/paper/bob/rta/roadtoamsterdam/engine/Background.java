package com.paper.bob.rta.roadtoamsterdam.engine;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Background {

    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res)
    {
        image = res;
        y=0;
    }
    public void draw(Canvas canvas)
    {
        Rect src = new Rect(0,0,image.getWidth()-1, image.getHeight()-1);
        Rect dest = new Rect(x,y,image.getWidth()+1, EngineGame.HEIGHT+1);
        canvas.drawBitmap(image, src, dest, null);
    }
    public void update(int dx)
    {
        x+=dx;
        if(x<-(EngineGame.WIDTH)){
            x=0;
        }
    }
}