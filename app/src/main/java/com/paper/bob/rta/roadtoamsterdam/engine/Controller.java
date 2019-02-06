package com.paper.bob.rta.roadtoamsterdam.engine;

import com.paper.bob.rta.roadtoamsterdam.engine.Person.Player;

public class Controller {

    private Player pl;
    private boolean moving;
    private boolean mRight,mLeft,mUp;

    public void setMRight(boolean m)
    {mRight = m;moving=m;}
    public void setMLeft(boolean m)
    {mLeft = m;moving=m;}
    public void setMUp(boolean m)
    {mUp = m;moving=m;}

    public boolean getMRight()
    {return mRight;}
    public boolean getMLeft()
    {return mLeft;}
    public boolean getMUp()
    {return mUp;}

}
