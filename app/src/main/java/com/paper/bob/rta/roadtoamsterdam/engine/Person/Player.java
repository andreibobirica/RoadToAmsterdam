
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
        Log.i("RTA",control.toString());
        super.update();
        int dx = 15;
        int dy = 10;
        int dDown = 3;

        if(control.getMDown())
        {
            y+=dDown;
            if (control.getMRight())
            {
                x+=dx;
            }
            else if (control.getMLeft())
            {
                x+=-dx;
            }
        }
        else if(control.getMUp())
        {
            y+=-dy;
            if (control.getMRight())
            {
                x += dx;
            }
            else if (control.getMLeft())
            {
                x += -dx;
            }
        }
        else
        {
            if (control.getMRight())
            {
                x += dx;
            }
            else if (control.getMLeft())
            {
                x += -dx;
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