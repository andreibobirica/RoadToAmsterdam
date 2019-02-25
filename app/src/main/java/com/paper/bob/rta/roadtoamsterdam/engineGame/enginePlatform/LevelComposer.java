package com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform;

import android.content.Context;

import com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform.Objects.Background;
import com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform.Objects.Base;
import com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform.Objects.Ostacolo;
import com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform.Objects.Person.Notify;
import com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform.Objects.Person.Personaggio;
import com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform.Objects.Person.Player;
import com.paper.bob.rta.roadtoamsterdam.gameUtils.DataXMLGraberPlatform;
import com.paper.bob.rta.roadtoamsterdam.gameUtils.Sound;
import com.paper.bob.rta.roadtoamsterdam.gameUtils.SoundBG;

import java.util.ArrayList;


public class LevelComposer {

    private DataXMLGraberPlatform dtGraber;//Campo che si occupa di Prelevare tutti i dati neccessari al Game, incluse le sue Istanze
    private String infoLevel;//Informazioni del livello

    public LevelComposer(String infoLevel,Context context)
    {
        dtGraber = new DataXMLGraberPlatform("datalevels.xml",context);
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
     * Metodo che si occupa di prelevare il Player dal DataXMLGraber
     * @return Player
     */
    public Player getPlayer() {return dtGraber.getPlayer(infoLevel);}

    /**
     * Metodo che si occupa di prelevare la Base Dal DataXMLGraber
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


    public ArrayList<Sound> getSounds() {
        return dtGraber.getSounds(infoLevel);
    }

    public SoundBG getSoundBG() {
        return dtGraber.getSoundBG(infoLevel);
    }
}
