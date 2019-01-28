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

    public Background getBackGround()
    {return dtGraber.getBackground(infoLevel);}

    /*
    public ArrayList<Personaggio> getPersonaggi()
    {return dtGraber.getPersonaggi(infoLevel);}
*/
    @Override
    public String toString() {
        return "";
    }
}
