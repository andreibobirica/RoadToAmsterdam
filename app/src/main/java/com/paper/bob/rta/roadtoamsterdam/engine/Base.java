package com.paper.bob.rta.roadtoamsterdam.engine;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Base extends Ostacolo implements Parcelable {
    private Bitmap img;
    private static Background bgCoord;

    public Base(Bitmap res)
    {
        super(res,0,EngineGame.HEIGHT-100,(EngineGame.HEIGHT+200),(EngineGame.WIDTH*2),1);
        img = res;
    }
    public void draw(Canvas canvas)
    {
        Rect src = new Rect(0,0,img.getWidth()-1, img.getHeight()-1);
        Rect dest = new Rect(x, y, width + x, height + y);
        canvas.drawBitmap(img, src, dest, null);
        if(x<-EngineGame.WIDTH)
        {
            dest = new Rect(x+(EngineGame.WIDTH*2), y,x+EngineGame.WIDTH+width, height+y);
            //dest = new Rect(x,y,(EngineGame.WIDTH*2)+x, height);
            canvas.drawBitmap(img, src, dest, null);
        }
        if(x>0)
        {
            dest = new Rect(x,y,-(EngineGame.WIDTH+x), height);
            canvas.drawBitmap(img, src, dest, null);
        }
    }
    public void update()
    {
        this.x += bgCoord.getDX();
        this.y += bgCoord.getDY();
        if(x<-EngineGame.WIDTH){
            x=0;
        }
        if(x>0){
            x=-EngineGame.WIDTH;
        }
    }
    public static void setBgCoord(Background bg)
    {bgCoord = bg;}

    @Override
    public String toString() {
        return "Base{" +
                "img=" + img +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getX());
        dest.writeInt(this.getY());
        dest.writeInt(this.getWidth());
        dest.writeInt(this.getHeight());
        dest.writeInt(this.getNFrame());
        dest.writeParcelable(this.img, flags);
    }

    protected Base(Parcel in) {
        super(null,in.readInt(),in.readInt(),in.readInt(),in.readInt(),in.readInt());
        this.img = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Parcelable.Creator<Base> CREATOR = new Parcelable.Creator<Base>() {
        @Override
        public Base createFromParcel(Parcel source) {
            return new Base(source);
        }

        @Override
        public Base[] newArray(int size) {
            return new Base[size];
        }
    };
}
