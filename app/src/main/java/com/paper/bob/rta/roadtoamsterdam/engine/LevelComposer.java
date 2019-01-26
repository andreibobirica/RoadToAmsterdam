package com.paper.bob.rta.roadtoamsterdam.engine;

import android.content.Context;

import com.paper.bob.rta.roadtoamsterdam.gameUtils.DataGraber;

import java.util.ArrayList;


public class LevelComposer {

    private DataGraber dtGraber;
    private String infoLevel;

    public LevelComposer(String infoLevel,Context context)
    {
        dtGraber = new DataGraber("datalevels.xml",context);
        this.infoLevel = infoLevel;
    }



    public ArrayList<Ostacolo> getOstacoli()
    {return dtGraber.getOstacoli(infoLevel);}

    public void getBackGround(){}
    public void getObjects(){}

    @Override
    public String toString() {
        return "";
    }
}
