/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.gameUtils;

/**
 * Classe EnvirontmentContainet, letteralmente Contenitore di Ambiente, per ambiente si intende un livello, non sotto la sua struttura
 * da videogioco 2d, ma come livello, stage, cioè una fase di gioco, una scena di gioco.
 * Per Enviritment si intende un insieme di video, dialogo, e parte platform, a ogni Envirntment è possibile una diramazione o meno.
 * Per questo motivo è presente la variabile Boolean scelta, true una scelta, false una scelta, null non ancora scelto o non importante.
 * Ci sono poi i riferimenti al EnvirntmentContainer A E B , cioè il riferimento ai prossimi due Envirotment.
 *
 * Ciascuna delle 3 parti di un Environtment può essere opzionale, lasciando libertà nella creazione di un Envirtment
 */
public class EnvironmentContainer {
    //Cambi per il conteggio dei EnvirinmenteContainer
    private static int nEC;
    private int ECId;

    /**
     * Riferiemnto al video
     */
    private String video;
    /**
     * Riferimento al dialogo
     */
    private String dialogo;
    /**
     * Riferimento al platform
     */
    private String platform;
    /**Scelta da fare, o effettuata già*/
    private Boolean scelta;//IF True A Else False B
    /**
     * Riferimento A al successivo Envirtment Container
     */
    private EnvironmentContainer contA;
    /**
     * Riferimento B al successivo Envirtment Container
     */
    private EnvironmentContainer contB;


    /**
     * Costruttore di Copia del EnvirontmentContainer, che costruisce un oggetto copiando le informazioni da un'altro
     * @param cont EnvirtomentContainer da copiare
     */
    public EnvironmentContainer(EnvironmentContainer cont) {
        ECId = cont.getId();
        this.video = cont.getVideo();
        this.dialogo = cont.getDialogo();
        this.platform = cont.getPlatform();
        this.scelta = cont.getScelta();
        this.contA = cont.getContA();
        this.contB = cont.getContB();
    }

    /**
     * Costruttore Del EnvironmentContainer , funziona tramite il passaggio dei 3 parametri opzionali del Envirnment
     * @param video
     * @param dialogo
     * @param platform
     */
    public EnvironmentContainer(String video, String dialogo, String platform)
    {
        ECId = nEC++;

        this.video = video;
        this.dialogo = dialogo;
        this.platform = platform;
    }


    /**
     * Metodo setDialogo che serve per modificare il riferimento string al dialogo
     * @param dialogo dialogo riferimento
     */
    public void setDialogo(String dialogo) {
        this.dialogo = dialogo;
    }
    /**Metodo set Scelta che modifica la variabile Boolean scleta, impostando una propria scelta, oppure annullandola nel caso di null*/
    public void setScelta(Boolean scelta) {
        this.scelta = scelta;
    }
    /**Metodo getVideo che restituisce il riferimento al video */
    public String getVideo() {
        return video;
    }
    /**Metodo getDialogo che restituisce il riferimento al video */
    public String getDialogo() {
        return dialogo;
    }
    /**Metodo getPlatform che restituisce il riferimento alplatform */
    public String getPlatform() {
        return platform;
    }
    /**Metodo getscelta che restituisce il riferimento alla scelta, se true ,false oppure null */
    public Boolean getScelta() {
        return scelta;
    }

    /**
     * Get Cont B restituisce il riferimento al Envirnment della diramazione b
     * @return Environment B riferimento
     */
    public EnvironmentContainer getContB() {
        return contB;
    }
    /**
     * Get Cont A restituisce il riferimento al Envirnment della diramazione A
     * @return Environment A riferimento
     */
    public EnvironmentContainer getContA() {
        return contA;
    }

    public EnvironmentContainer getNext() {
        if(this.scelta)return contA;
        return contB;
    }

    /**
     * Metodo setNext che serve per settare il riferimento ai successivi Nodi del storyline del EnvirnmentContainer
     * @param contA
     * @param contB
     */
    public void setNext(EnvironmentContainer contA, EnvironmentContainer contB)
    {
        this.contA = contA;
        this.contB = contB;
    }

    /**
     * Metodo che verifica se il riferimento al Environment successivi e la scelta sono stati inazializzati oppure no
     * @return variabile booleana che verifica la scelta
     */
    public boolean verifyScelta() {
        return (contA!=null&&contB!=null&&scelta!=null);
    }

    /**
     * Return dell'id del Envirnment, di univoco generato nel Costruttore
     * @return
     */
    public int getId() {
        return ECId;
    }

    /**
     * Metodo toString
     * @return Stringt to String
     */
    @Override
    public String toString() {
        return "EnvironmentContainer{" +
                "Id=" + ECId +
                ", video='" + video + '\'' +
                ", dialogo='" + dialogo + '\'' +
                ", platform='" + platform + '\'' +
                ", scelta=" + (scelta!=null?scelta:"null") +
                ", contA=" + (contA!=null?contA.getId():"null") +
                ", contB=" + (contB!=null?contB.getId():"null") +
                '}';
    }
}
