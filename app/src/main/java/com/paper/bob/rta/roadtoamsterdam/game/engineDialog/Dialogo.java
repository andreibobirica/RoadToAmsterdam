/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.game.engineDialog;

import java.util.ArrayList;

/**
 * Classe Dialogo, classe fondamentale per la gestione dei dialoghi.
 * Questa classe è la rapresentazione astratta di un dialogo contenente tutti i campi per la visualizzazione di un dialogo senza scelta
 * e se presente , del successivo dialogo con scelte.
 * Sono presenti infatti i campi per l'attuale dialogo, e nel casso la battuata successiva conenga una scelta, sono presenti all'interno
 * anche i campi inizializzati delle scelte.
 */
public class Dialogo {
    /**Campo nomeDialogo, Stringa conentente il nome del dialogo*/
    private String nomeDialogo;
    /**Campo nomePers, Stringa conetenente il nome del Pers della battuta corrente*/
    private String nomePers;
    /**Campo nomeOtherPers, Stringa conetenente il nome del Pers della battuta Successiva*/
    private String nomeOtherPers;
    /**Campo contente il riferimento al nome dell'immagine del Pers della battuta corrente*/
    private String nomeImmPers;
    /**Campo contente il riferimento al nome dell'immagine del Pers della battuta Successiva, quindi quello che ascolta*/
    private String nomeImmOtherPers;
    /**Campo contenente il testo della battuta, del dialogo*/
    private String battuta;
    /**Campo scelta che se inizializzato contiene il testo ripetitivo di che scelta si dovrà effettuare*/
    private String scelta;
    /**ArrayList conentente due stringhe, cioè le due eventuali scelte*/
    private ArrayList<String> scelte;


    /**
     * Costruttore
     */
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

    /**
     * Metodo che tirona il testo della battuta
     * @return String contenente il testo della battuta
     */
    public String getBattuta() {
        return battuta;
    }

    /**
     * Metodo che ritorna una arrayList contenente le due scelte
     * @return Array list parametrizzato a stringa conentente le due scelte
     */
    public ArrayList<String> getScelte() {
        return scelte;
    }

    /**
     * Metodo che ritorna il nome del personaggio dialogante
     * @return String nome del personaggio
     */
    public String getNomePers() {
        return nomePers;
    }

    /**
     * Metodo che ritorna il testo della scelta da effettuare
     * @return String contenente il testo ripetitivo dellla eventuale scelta da effetuare
     */
    public String getScelta() {
        return scelta;
    }

    /**
     * Metodo che ritorna il nome della Immagine del personaggio dialogante
     * @return String contenente il nome del immaggine del personaggio dialogante
     */
    public String getNomeImmPers() {
        return nomeImmPers;
    }

    /**
     * Metodo che ritorna il nome dell personaggio che ascolta
     * @return String contenente il nome del personaggio che ascolta
     */
    public String getNomeOtherPers() {
        return nomeOtherPers;
    }
    /**
     * Metodo che ritorna il nome della Immagine del personaggio che ascolta
     * @return String contenente il nome del immaggine del personaggio che ascolta
     */
    public String getNomeImmOtherPers() {
        return nomeImmOtherPers;
    }

    /**
     * Metodo toString
     * @return String contenente le info principali del Dialogo
     */
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
