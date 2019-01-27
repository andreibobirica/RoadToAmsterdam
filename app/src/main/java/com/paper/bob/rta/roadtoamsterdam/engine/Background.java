package com.paper.bob.rta.roadtoamsterdam.engine;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.paper.bob.rta.roadtoamsterdam.engine.EngineGame;

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
        canvas.drawBitmap(image, x, y,null);
        if(x<0)
        {
            canvas.drawBitmap(image, x+EngineGame.WIDTH, y, null);
        }
    }
    public void update()
    {
        x+=dx;
        if(x<-EngineGame.WIDTH){
            x=0;
        }
    }
    public void setVector(int dx)
    {
        this.dx = dx;
    }
}