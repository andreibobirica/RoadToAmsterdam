
package com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Person;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Controller;


public class Player extends Personaggio{

    private Bitmap leftAnim;
    private Bitmap rightAnim;
    private Bitmap jumpLAnim;
    private Bitmap jumpRAnim;
    private Bitmap img;
    //0 normal ; 1 left ; 2 right ; 3updown Left; 4 updown Right;
    private int cambioImg = 0;

    private Controller control;

    //VETTORI DI MOVIMENTO
    private int dx;
    private int dy;
    private int dDown;

    public Player(Bitmap img, int x, int y, int height, int width, int nframe) {
        super(img, x, y, height, width, nframe, null, false);
        setTipo("Player");
        this.img = img;
        setFisico(true);
        setNotify(false);
    }

    public Player(Player pl){
        super(pl.getImage(),pl.getX(),pl.getY(),pl.getHeight(),pl.getWidth(),pl.getNFrame(),null,false);
        setTipo("Player");
        this.img = pl.getImage();
        setFisico(true);
        setNotify(false);
    }

    public void update()
    {
        super.update();
        boolean up = false;
        boolean down = false;
        //Controllo se in basso o in alto
        if(control.getMDown())
        {   /*Se in down*/
            y+=dDown;
            down=true;
        }
        else if(control.getMUp())
        {   /*Se in UP*/
            y+=-dy;
            up = true;
        }
        else
        {
            y+=control.getMDownPerfect();
        }


        //CONTROLLO  se a destra o a sinistra
        if (control.getMRight())
        {
            x += dx;
            if (up && cambioImg!=3) {
                cambioImg = 3;
                this.setImage(jumpRAnim);
            } else if(cambioImg!=1 && !down && !up) {
                cambioImg = 1;
                this.setImage(rightAnim);
            }else if (down && cambioImg!=3) {
                cambioImg = 3;
                this.setImage(jumpRAnim);
            }
        }
        else if (control.getMLeft())
        {
            x += -dx;
            if (up && cambioImg!=3) {
                cambioImg = 3;
                this.setImage(jumpLAnim);
            } else if(cambioImg!=1 && !down && !up) {
                cambioImg = 1;
                this.setImage(leftAnim);
            }else if (down && cambioImg!=3) {
            cambioImg = 3;
            this.setImage(jumpLAnim);
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
    {super.draw(canvas);}

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


    public Controller getController() {
        return control;
    }

    /////////////////////////////////////

}