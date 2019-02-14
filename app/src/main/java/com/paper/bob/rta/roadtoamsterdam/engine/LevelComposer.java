package com.paper.bob.rta.roadtoamsterdam.engine;

import android.content.Context;

import com.paper.bob.rta.roadtoamsterdam.engine.Person.Notify;
import com.paper.bob.rta.roadtoamsterdam.engine.Person.Personaggio;
import com.paper.bob.rta.roadtoamsterdam.engine.Person.Player;
import com.paper.bob.rta.roadtoamsterdam.gameUtils.DataGraber;

import java.util.ArrayList;


public class LevelComposer {

    private DataGraber dtGraber;//Campo che si occupa di Prelevare tutti i dati neccessari al Game, incluse le sue Istanze
    private String infoLevel;//Informazioni del livello

    public LevelComposer(String infoLevel,Context context)
    {
        dtGraber = new DataGraber("datalevels.xml",context);
        this.infoLevel = infoLevel;
    }

    /**
     * Metodo che si occupa di prelevaretutti gli Ostacoli di un dato livello dal Datagraber
     * @return  Arraylist contenente tutti gli ostacoli
     */
    public ArrayList<Ostacolo> getOstacoli() {return dtGraber.getOstacoli(infoLevel);}

    /**
     * Metodo che si occupa prelevare il Background dal Datagraber
     * @return Oggetto Background
     */
    public Background getBackGround() {return dtGraber.getBackground(infoLevel);}

    /**
     * Metodo che si occupa di prelevare il Player dal DataGraber
     * @return Player
     */
    public Player getPlayer() {return dtGraber.getPlayer(infoLevel);}

    /**
     * Metodo che si occupa di prelevare la Base Dal DataGraber
     * @return Base
     */
    public Base getBase() {return dtGraber.getBase(infoLevel);}

    /**
     * Metodo che preleva un Insieme di Personaggi dal Datagraber
     * @return ArrayList di tutti i Personaggi
     */
    public ArrayList<Personaggio> getPersonaggi()
    {
        ArrayList<Personaggio> p = dtGraber.getPersonaggi(infoLevel);
        Notify.setDG(dtGraber);
        return p;
    }

    @Override
    public String toString() {
        return "LevelComposer{" +
                "dtGraber=" + dtGraber +
                ", infoLevel='" + infoLevel + '\'' +
                '}';
    }
}
