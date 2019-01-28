package com.paper.bob.rta.roadtoamsterdam.engine.Person;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.paper.bob.rta.roadtoamsterdam.engine.Animation;

public class Personaggio extends PersonaggioObj {
    private Animation animation;

    private Bitmap[] gif;
    private long startTime;

    public Personaggio(Bitmap image, int x, int y) {
        super(image, 1, 3, x, y);
        this.gif = new Bitmap[colCount]; // 3

        for (int col = 0; col < colCount; col++) {
            this.gif[col] = this.createSubImageAt(col);
        }

        animation.setFrames(gif);
        animation.setFrame(colCount);
        animation.setDelay(250);
        startTime = System.nanoTime();

    }

    public void update()
    {animation.update();}

    public void draw(Canvas c)
    {c.drawBitmap(gif[0],x,y,null);}
}
