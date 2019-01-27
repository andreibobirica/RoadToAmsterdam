package com.paper.bob.rta.roadtoamsterdam.engine;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;


public class Player extends Ostacolo {
    //Vettore per un eventuale movimento
    private int dy;
    private int dx;
    private int numFrames;

    //private boolean playing;
    private Animation animation = new Animation();
    private long startTime;

    public Player(Bitmap img) {
        super(img,400,200, 10, 10);
        dy = 0;
        dx = 0;
        numFrames = 3;
        Log.i("RTA", "  Bitmapp");
        Bitmap[] gif = new Bitmap[3];

        for (int i = 0; i < gif.length; i++)
        {gif[i] = Bitmap.createBitmap(img, i*width, 0, width, height);}
        Log.i("RTA", "PlayerConstruct"+gif.length+" dio porco");
        for (int i = 0; i < gif.length; i++)
        {gif[i] = Bitmap.createBitmap(img, i*width, height, width, height*2);}


        animation.setFrames(gif);
        animation.setFrame(numFrames);
        animation.setDelay(250);
        startTime = System.nanoTime();
    }

    public void update()
    {
        animation.update();
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(),x,y,null);
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