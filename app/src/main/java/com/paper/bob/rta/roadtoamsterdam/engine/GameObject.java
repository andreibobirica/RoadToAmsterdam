package com.paper.bob.rta.roadtoamsterdam.engine;
import android.graphics.Rect;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;

    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public int getHeight()
    {
        return height;
    }
    public int getWidth()
    {
        return width;
    }
    public Rect getRectangle()
    {
        return new Rect(x, y, x+width, y+height);
    }
    @Override
    public String toString() {
        String info = "";
        info += "x = "+x+"\t";
        info += "y = "+y+"\t";
        info += "dx = "+dx+"\t";
        info += "dy = "+dy+"\t";
        info += "width = "+width+"\t";
        info += "height = "+height+"\t";
        return info;
    }
}