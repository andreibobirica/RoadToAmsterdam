package com.paper.bob.rta.roadtoamsterdam.engine.Person;

import android.graphics.Bitmap;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.engine.Ostacolo;


public class Personaggio extends Ostacolo {

    private boolean notify = false;
    private String dialogo;
    public Personaggio(Bitmap img, int x, int y, int height, int width, int nframe,String dialogo, boolean notify)
    {
        super(img,x,y,height,width,nframe);
        this.dialogo = dialogo;
        //Se è Notified, allora è anche fisico, se non è Notified, non è fisico
        this.notify = notify;
        this.setFisico(notify);
    }

    public boolean getNotify()
    {
        this.setFisico(false);
        return notify;
    }

    public void setNotify(boolean n)
    {notify = n;}
}
