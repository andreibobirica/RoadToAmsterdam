package com.paper.bob.rta.roadtoamsterdam.engine;

import android.os.Handler;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.engine.Person.Player;

public class Controller {

    private Player pl;
    private boolean moving;
    private boolean mRight=false,mLeft=false,mUp=false,mDown=true;
    private boolean uping=false;

    public void setMRight(boolean m)
    {mRight = m;moving=m;}
    public void setMLeft(boolean m)
    {mLeft = m;moving=m;}
    public void setMUp(boolean m)
    {
        if(!uping) {
            mUp = m;
            mDown=false;
            moving = m;
            uping = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Log.i("RTA","uping");
                    mUp = false;
                    mDown=true;
                    uping= false;
                }
            }, 450);
        }
    }

    public boolean getMDown()
    {return mDown;}
    public boolean getMRight()
    {return mRight;}
    public boolean getMLeft()
    {return mLeft;}
    public boolean getMUp()
    {return mUp;}

    public String toString()
    {return "bool: "+moving+mLeft+mRight+mUp;}
}
