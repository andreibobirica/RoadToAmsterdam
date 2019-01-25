package com.paper.bob.rta.roadtoamsterdam.engine;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.animation.Animation;

public class Player extends Ostacolo {
    //Vettore per un eventuale movimento
    protected int dy;
    protected int dx;

    //private boolean playing;

    //private Animation animation = new Animation();

    //private long startTime;

    public Player(Bitmap img, int width, int height, int numFrames) {
        super(img,700,600);
        dy = 0;
        dx = 0;
        this.height = height;
        this.width = width;
        Bitmap[] gif = new Bitmap[numFrames];

        for (int i = 0; i < gif.length; i++)
        {gif[i] = Bitmap.createBitmap(img, i*width, 0, width, height);}

        /*
        animation.setFrames(gif);
        animation.setDelay(10);
        startTime = System.nanoTime();
        */
    }

    public void update()
    {
        //animation.update();
    }

    public void draw(Canvas canvas)
    {
        //canvas.drawBitmap(animation.getImage(),x,y,null);
    }
    public void resetDY(){dy = 0;}
    public void resetDX(){dx = 0;}

    @Override
    public String toString() {
        String info = super.toString();
        info += "\ndy: "+dy+"\t";
        info += "dy: "+dy;
        info += "dx: "+dx;
        return info;
    }
}