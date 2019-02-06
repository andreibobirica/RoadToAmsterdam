
package com.paper.bob.rta.roadtoamsterdam.engine.Person;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.engine.Controller;


public class Player extends Personaggio {

    private Bitmap leftAnim;
    private Bitmap rightAnim;
    private Bitmap jumpLAnim;
    private Bitmap jumpRLAnim;

    private Controller control;

    public Player(Bitmap img, int x, int y, int height, int width, int nframe){
        super(img,x,y,height,width,nframe,"",false);
        setFisico(true);
        setNotify(false);
    }

    public void update()
    {
        super.update();
        if(control.getMUp())
        {
            if (control.getMRight())
            {

            }
            else if (control.getMLeft())
            {

            }
        }
        else
        {
            if (control.getMRight())
            {
                x += 5;
            }
            else if (control.getMLeft())
            {
                x += -5;
            }
        }
    }

    public void draw(Canvas canvas)
    {
        super.draw(canvas);
    }

    public void setController(Controller control)
    {this.control = control;}

}