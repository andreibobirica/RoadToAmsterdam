
package com.paper.bob.rta.roadtoamsterdam.engine.Person;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.paper.bob.rta.roadtoamsterdam.engine.Controller;


public class Player extends Personaggio {

    private Bitmap leftAnim;
    private Bitmap rightAnim;
    private Bitmap jumpLAnim;
    private Bitmap jumpRAnim;
    private Bitmap img;
    //0 normal ; 1 left ; 2 right ;
    private int cambioImg = 0;

    private Controller control;

    //VETTORI DI MOVIMENTO
    private int dx;
    private int dy;
    private int dDown;

    public Player(Bitmap img, int x, int y, int height, int width, int nframe) {
        super(img, x, y, height, width, nframe, null, false);
        this.img = img;
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

        //Controllo se in basso o in alto
        //In più per variare le animazioni se verifica se si salta a destra o a sinistra
        if(control.getMDown())
        {
            y+=dDown;
        }
        else if(control.getMUp())
        {
            y+=-dy;
        }

        //CONTROLLO  se a destra o a sinistra
        if (control.getMRight())
        {
            x += dx;
            if(cambioImg != 2) {
                cambioImg=2;
                this.setImage(rightAnim);
            }
        }
        else if (control.getMLeft())
        {
            x += -dx;
            if(cambioImg != 1) {
                cambioImg = 1;
                this.setImage(leftAnim);
            }
        }
        else
        {
            if(cambioImg != 0) {
                cambioImg = 0;
                this.setImage(img);
            }
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