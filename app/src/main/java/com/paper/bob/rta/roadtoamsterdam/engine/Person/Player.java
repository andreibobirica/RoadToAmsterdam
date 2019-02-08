
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
    private Bitmap jumpRAnim;

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
        {
            y+=dDown;
            if (control.getMRight())
            {
                this.setImage(jumpRAnim);
            }
            else if (control.getMLeft())
            {
                this.setImage(jumpLAnim);
            }
        }
        else if(control.getMUp())
        {
            y+=-dy;
            if (control.getMRight())
            {
                this.setImage(jumpRAnim);
            }
            else if (control.getMLeft())
            {
                this.setImage(jumpLAnim);
            }
        }

            if (control.getMRight())
            {
                x += dx;
                this.setImage(rightAnim);
            }
            else if (control.getMLeft())
            {
                x += -dx;
                this.setImage(leftAnim);
            }

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

    public int getDX(){return dx;}
    public int getDY(){return dy;}

    public void setLeftAnim(Bitmap i){leftAnim = i;}
    public void setRightAnim(Bitmap i){rightAnim = i;}
    public void setJumpLAnim(Bitmap i){jumpLAnim = i;}
    public void setJumpRAnim(Bitmap i){jumpRAnim = i;}
}