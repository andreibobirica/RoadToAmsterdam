package com.paper.bob.rta.roadtoamsterdam.engine.Person;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.engine.Animation;

public class Notify extends Animation {

    private final int height;
    private final int width;
    private final int y;
    private final int x;
    private final Bitmap img;
    private Rect destNot,srcNot;
    private Bitmap imgNot;

    public Notify(Bitmap img,int x, int y, int width, int height)
    {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setContext(Context context)
    {
        int resId = context.getResources().getIdentifier("notify", "drawable", context.getPackageName());
        imgNot = BitmapFactory.decodeResource(context.getResources(), resId);
        srcNot = new Rect(0,0,imgNot.getWidth()-1, imgNot.getHeight()-1);
        destNot = new Rect(((width/3)+x),y-100,x+width-(width/3),y);

        //4 rame per questa ANIMAZIONE
        int nframe = 5;
        Bitmap[] gif = new Bitmap[nframe];
        for (int i = 0; i < gif.length; i++) {
            gif[i] = Bitmap.createBitmap(imgNot, i * (img.getWidth()/nframe), 0, img.getWidth()/nframe, img.getHeight());
        }
        setFrames(gif);
        //Se 2 frame = 100 delay - Se 6 frame = 200 delay
        //Per ogni frame 25 di delay in più
        int delay = 50+nframe*25;
        setDelay(delay);
    }

    public void draw(Canvas c)
    {
        c.drawBitmap(getImage(), srcNot, destNot, null);
    }
}
