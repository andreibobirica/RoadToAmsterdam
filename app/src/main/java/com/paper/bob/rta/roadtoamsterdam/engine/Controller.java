package com.paper.bob.rta.roadtoamsterdam.engine;

import android.os.Handler;

public class Controller {


    private boolean moving;
    private boolean mRight=false,mLeft=false,mUp=false,mDown=true;
    private boolean uping=false;

    public void setMRight(boolean m)
    {mRight = m;moving=m;}
    public void setMLeft(boolean m)
    {mLeft = m;moving=m;}
    public void setMDown(boolean m)
    {mDown = m;moving=m;}
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
                    mUp = false;
                    mDown=true;
                    uping= false;
                }
            }, 500);
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
