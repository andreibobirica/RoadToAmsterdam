package com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform.Objects.Person;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform.Objects.Ostacolo;


public class Personaggio extends Ostacolo {


    private String dialogo;

    private boolean notify = false;
    private Notify not;

    public Personaggio(Bitmap img, int x, int y, int height, int width, int nframe,String dialogo, boolean notify)
    {
        super(img,x,y,height,width,nframe);
        //tipo
        this.setTipo("Personaggio");
        //Dialogo
        this.dialogo = dialogo;
        //Se è Notified, allora è anche fisico, se non è Notified, non è fisico
        if(notify) {
            this.setNotify(true);
            this.setFisico(true);
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

    public boolean getNotify()
    {return notify;}

    public String getDialogo()
    {return dialogo;}


    public Notify getNot() {
        return not;
    }
}
