package com.paper.bob.rta.roadtoamsterdam.engine;
import android.graphics.Bitmap;
import android.graphics.Canvas;

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
        canvas.drawBitmap(image, x, y, null);
    }
    public void update(int dx)
    {
        x+=dx;
        if(x<-(EngineGame.WIDTH*2)){
            x=0;
        }
    }
}