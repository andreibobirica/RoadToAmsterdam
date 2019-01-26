package com.paper.bob.rta.roadtoamsterdam.engine;

import android.content.Context;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.gameUtils.DataGraber;
import com.paper.bob.rta.roadtoamsterdam.gameUtils.xmlParser;

import java.util.ArrayList;


public class LevelComposer {

    private DataGraber dtGraber;
    private String infoLevel;

    public LevelComposer(String infoLevel,Context context)
    {
        Log.i("RTA", "xmlParser richiamto1");
        dtGraber = new DataGraber("datalevels.xml",context);
        this.infoLevel = infoLevel;
        Log.i("RTA", "xmlParser richiamto2");
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
