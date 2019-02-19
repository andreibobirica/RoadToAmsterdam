package com.paper.bob.rta.roadtoamsterdam.engineDialog;


import java.util.ArrayList;

public class Dialogo {
    private String nomeDialogo;
    private String nomePers;
    private String nomeOtherPers;
    private String nomeImmPers;
    private String nomeImmOtherPers;
    private String battuta;
    private String scelta;
    private ArrayList<String> scelte;


    public Dialogo(String nomeDialogo, String nomePers, String nomeOtherPers,String nomeImmPers,String nomeImmOtherPers, String battuta, String scelta, ArrayList<String> scelte)
    {
        this.nomeDialogo=nomeDialogo;
        this.nomePers = nomePers;
        this.nomeOtherPers = nomeOtherPers;
        this.nomeImmPers = nomeImmPers;
        this.nomeImmOtherPers=nomeImmOtherPers;
        this.battuta=battuta;
        this.scelta = scelta;
        this.scelte=scelte;
    }

    public String getNomeDialogo() {
        return nomeDialogo;
    }

    public String getBattuta() {
        return battuta;
    }

    public ArrayList<String> getScelte() {
        return scelte;
    }

    public String getNomePers() {
        return nomePers;
    }

    public String getScelta() {
        return scelta;
    }

    public String getNomeImmPers() {
        return nomeImmPers;
    }

    public String getNomeOtherPers() {
        return nomeOtherPers;
    }


    public String getNomeImmOtherPers() {
        return nomeImmOtherPers;
    }

    @Override
    public String toString() {
        return "Dialogo{" +
                "nomeDialogo='" + nomeDialogo + '\'' +
                ", nomePers='" + nomePers + '\'' +
                ", nomeOtherPers='" + nomeOtherPers + '\'' +
                ", nomeImmPers='" + nomeImmPers + '\'' +
                ", nomeImmOtherPers='" + nomeImmOtherPers + '\'' +
                ", battuta='" + battuta + '\'' +
                ", scelta='" + scelta + '\'' +
                ", scelte=" + scelte +
                '}';
    }
}
