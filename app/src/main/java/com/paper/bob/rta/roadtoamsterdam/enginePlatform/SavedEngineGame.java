package com.paper.bob.rta.roadtoamsterdam.enginePlatform;


import android.os.Parcel;
import android.os.Parcelable;

import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Background;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.GameObject;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Person.Player;

import java.util.ArrayList;

public class SavedEngineGame implements Parcelable {
    private int plY;
    private int plX;
    private int bgY;
    private int bgX;
    private ArrayList<GameObject> objColl;

    public SavedEngineGame(Background bg, Player pl, ArrayList<GameObject> objColl)
    {
        bgX = bg.getX();
        bgY = bg.getY();
        plX = pl.getX();
        plY = pl.getY();
        this.objColl = objColl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.plY);
        dest.writeInt(this.plX);
        dest.writeInt(this.bgY);
        dest.writeInt(this.bgX);
        dest.writeTypedList(this.objColl);
    }

    protected SavedEngineGame(Parcel in) {
        this.plY = in.readInt();
        this.plX = in.readInt();
        this.bgY = in.readInt();
        this.bgX = in.readInt();
        this.objColl = in.createTypedArrayList(GameObject.CREATOR);
    }

    public static final Parcelable.Creator<SavedEngineGame> CREATOR = new Parcelable.Creator<SavedEngineGame>() {
        @Override
        public SavedEngineGame createFromParcel(Parcel source) {
            return new SavedEngineGame(source);
        }

        @Override
        public SavedEngineGame[] newArray(int size) {
            return new SavedEngineGame[size];
        }
    };
}
