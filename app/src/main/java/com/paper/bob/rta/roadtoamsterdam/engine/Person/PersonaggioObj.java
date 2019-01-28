package com.paper.bob.rta.roadtoamsterdam.engine.Person;

import android.graphics.Bitmap;

import com.paper.bob.rta.roadtoamsterdam.engine.GameObject;

public class PersonaggioObj extends GameObject {

    private Bitmap image;
    private int rowCount;
    int colCount;
    private int WIDTH;
    private int HEIGHT;

    public PersonaggioObj(Bitmap image, int rowCount, int colCount, int x, int y)
    {
        this.image = image;
        this.rowCount= rowCount;
        this.colCount= colCount;

        this.x= x;
        this.y= y;

        this.WIDTH = image.getWidth();
        this.HEIGHT = image.getHeight();

        this.width = this.WIDTH/ colCount;
        this.height= this.HEIGHT/ rowCount;
    }



    public Bitmap createSubImageAt(int col)  {
        // createBitmap(bitmap, x, y, width, height).
        Bitmap subImage = Bitmap.createBitmap(image, col* width,0,width,height);
        return subImage;
    }

}
