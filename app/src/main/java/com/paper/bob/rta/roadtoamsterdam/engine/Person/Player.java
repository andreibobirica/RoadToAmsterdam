
package com.paper.bob.rta.roadtoamsterdam.engine.Person;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.engine.Controller;
import com.paper.bob.rta.roadtoamsterdam.engine.EngineGame;


public class Player extends Personaggio {

    private Bitmap leftAnim;
    private Bitmap rightAnim;
    private Bitmap jumpLAnim;
    private Bitmap jumpRLAnim;

    private Controller control;

    //VETTORI DI MOVIMENTO
    private int dx;
    private int dy;
    private int dDown;

    public Player(Bitmap img, int x, int y, int height, int width, int nframe) {
        super(img, x, y, height, width, nframe, null, false);
        setFisico(true);
        setNotify(false);
    }

    public Player(Player pl){
        super(pl.getImage(),pl.getX(),pl.getY(),pl.getHeight(),pl.getWidth(),pl.getNFrame(),null,false);
        setFisico(true);
        setNotify(false);
    }

    public void update()
    {
        super.update();

        if(control.getMDown())
        {y+=dDown;}
        else if(control.getMUp())
        {y+=-dy;}

        if (control.getMRight())
        {x += dx;}
        else if (control.getMLeft())
        {x += -dx;}

    }

    public void draw(Canvas canvas)
    {
        super.draw(canvas);
    }

    public void setController(Controller control)
    {
        this.control = control;
        dx = control.getDX();
        dy = control.getDY();
        dDown = control.getDDown();
    }
}