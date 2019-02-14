
package com.paper.bob.rta.roadtoamsterdam.engine.Person;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.engine.Controller;


public class Player extends Personaggio implements Parcelable {

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

    @Override
    public String toString() {
        return "Player{" +
                "leftAnim=" + leftAnim +
                ", rightAnim=" + rightAnim +
                ", jumpLAnim=" + jumpLAnim +
                ", jumpRAnim=" + jumpRAnim +
                ", img=" + img +
                ", cambioImg=" + cambioImg +
                ", control=" + control +
                ", dx=" + dx +
                ", dy=" + dy +
                ", dDown=" + dDown +
                '}';
    }

    /////////////////////////////////////

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
        dest.writeParcelable(this.leftAnim, flags);
        dest.writeParcelable(this.rightAnim, flags);
        dest.writeParcelable(this.jumpLAnim, flags);
        dest.writeParcelable(this.jumpRAnim, flags);
        dest.writeParcelable(this.img, flags);
        dest.writeInt(this.cambioImg);
        dest.writeParcelable(this.control, flags);
        dest.writeInt(this.dx);
        dest.writeInt(this.dy);
        dest.writeInt(this.dDown);
    }

    protected Player(Parcel in) {
        super(null,in.readInt(),in.readInt(),in.readInt(),in.readInt(),in.readInt(),null,false);
        this.leftAnim = in.readParcelable(Bitmap.class.getClassLoader());
        this.rightAnim = in.readParcelable(Bitmap.class.getClassLoader());
        this.jumpLAnim = in.readParcelable(Bitmap.class.getClassLoader());
        this.jumpRAnim = in.readParcelable(Bitmap.class.getClassLoader());
        this.img = in.readParcelable(Bitmap.class.getClassLoader());
        this.cambioImg = in.readInt();
        this.control = in.readParcelable(Controller.class.getClassLoader());
        this.dx = in.readInt();
        this.dy = in.readInt();
        this.dDown = in.readInt();
    }

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}