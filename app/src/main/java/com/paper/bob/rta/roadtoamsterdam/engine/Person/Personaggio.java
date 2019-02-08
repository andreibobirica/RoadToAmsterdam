package com.paper.bob.rta.roadtoamsterdam.engine.Person;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.engine.Background;
import com.paper.bob.rta.roadtoamsterdam.engine.Ostacolo;


public class Personaggio extends Ostacolo {


    private String dialogo;

    private boolean notify = false;
    private Notify not;

    public Personaggio(Bitmap img, int x, int y, int height, int width, int nframe,String dialogo, boolean notify)
    {
        super(img,x,y,height,width,nframe);
        this.dialogo = dialogo;
        //Se è Notified, allora è anche fisico, se non è Notified, non è fisico
        if(notify) {
            this.notify = notify;
            this.setFisico(notify);
            not = new Notify(img, getX(), getY(), getWidth(), getHeight());
        }
    }

    public void draw(Canvas c)
    {
        super.draw(c);
        if(notify)not.draw(c);
    }

    public void update()
    {
        super.update();
        if(notify)not.update();
    }

    public void setNotify(boolean n) {notify = n;}
}
