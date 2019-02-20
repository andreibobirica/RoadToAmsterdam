package com.paper.bob.rta.roadtoamsterdam.engineGame;


public class EnvironmentContainer {

    private String video;
    private String dialogo;
    private String platform;
    private Boolean scelta;//IF True A Else False B
    private EnvironmentContainer contA;
    private EnvironmentContainer contB;
    private EnvironmentContainer contPrec;

    public EnvironmentContainer(EnvironmentContainer cont) {
        this.video = cont.getVideo();
        this.dialogo = cont.getDialogo();
        this.platform = cont.getPlatform();
        this.scelta = cont.getScelta();
        this.contA = cont.getContA();
        this.contB = cont.getContB();
    }

    public EnvironmentContainer(String video, String dialogo, String platform, Boolean scelta, EnvironmentContainer contA, EnvironmentContainer contB,EnvironmentContainer contPrec)
    {
        this.video = video;
        this.dialogo = dialogo;
        this.platform = platform;
        this.scelta = scelta;
        this.contA = contA;
        this.contB = contB;
        this.contPrec = contPrec;
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

    public void setContPrec(EnvironmentContainer cont)
    {
        this.contPrec = cont;
    }

    public EnvironmentContainer getPrev() {
        return contPrec;
    }
    public EnvironmentContainer getNext(boolean scelta) {
        if(scelta)return contA;
        return contB;
    }
}
