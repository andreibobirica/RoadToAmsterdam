/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.gameUtils;


public class EnvironmentContainer {
    //Cambi per il conteggio dei EnvirinmenteContainer
    private static int nEC;
    private int ECId;

    private String video;
    private String dialogo;
    private String platform;
    private Boolean scelta;//IF True A Else False B
    private EnvironmentContainer contA;
    private EnvironmentContainer contB;


    public EnvironmentContainer(EnvironmentContainer cont) {
        ECId = cont.getId();
        this.video = cont.getVideo();
        this.dialogo = cont.getDialogo();
        this.platform = cont.getPlatform();
        this.scelta = cont.getScelta();
        this.contA = cont.getContA();
        this.contB = cont.getContB();
    }

    public EnvironmentContainer(String video, String dialogo, String platform)
    {
        ECId = nEC++;

        this.video = video;
        this.dialogo = dialogo;
        this.platform = platform;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setDialogo(String dialogo) {
        this.dialogo = dialogo;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setScelta(Boolean scelta) {
        this.scelta = scelta;
    }

    public String getVideo() {
        return video;
    }

    public String getDialogo() {
        return dialogo;
    }

    public String getPlatform() {
        return platform;
    }

    public Boolean getScelta() {
        return scelta;
    }

    public EnvironmentContainer getContB() {
        return contB;
    }

    public EnvironmentContainer getContA() {
        return contA;
    }

    public EnvironmentContainer getNext() {
        if(this.scelta)return contA;
        return contB;
    }

    public void setNext(EnvironmentContainer contA, EnvironmentContainer contB)
    {
        this.contA = contA;
        this.contB = contB;
    }

    public boolean verifyScelta() {
        return (contA!=null&&contB!=null&&scelta!=null);
    }

    public int getId() {
        return ECId;
    }

    public static int getTotalEC()
    {
        return nEC;
    }

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
