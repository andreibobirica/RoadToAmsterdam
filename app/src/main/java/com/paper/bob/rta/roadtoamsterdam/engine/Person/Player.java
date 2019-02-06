
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
    private EngineGame engineGame;

    public Player(Bitmap img, int x, int y, int height, int width, int nframe){
        super(img,x,y,height,width,nframe,"",false);
        setFisico(true);
        setNotify(false);
    }

    public void update()
    {
        super.update();
        int dx = 10;
        int dy = 5;
        int dDown = 2;
        //Numero che indica dove non ci si può muovere
        int nDMove = engineGame.verCollision();

        if(control.getMDown() && nDMove!=2)
        {
            y+=dDown;
            if (control.getMRight() && nDMove!=1)
            {
                x+=dx;
            }
            else if (control.getMLeft() && nDMove!=3)
            {
                x+=-dx;
            }
        }
        else if(control.getMUp()&& nDMove!=4)
        {
            y+=-dy;
            if (control.getMRight() && nDMove!=1)
            {
                x += dx;
            }
            else if (control.getMLeft() && nDMove!=3)
            {
                x += -dx;
            }
        }
        else
        {
            if (control.getMRight() && nDMove!=1)
            {
                x += dx;
            }
            else if (control.getMLeft() && nDMove!=3)
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

    public void setEngineGame(EngineGame eng)
    {engineGame = eng;}

}