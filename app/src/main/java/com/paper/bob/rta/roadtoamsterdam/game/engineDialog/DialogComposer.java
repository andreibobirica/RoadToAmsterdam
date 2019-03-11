/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.game.engineDialog;


import android.content.Context;

import com.paper.bob.rta.roadtoamsterdam.gameUtils.DataXMLGraberDialog;

import java.util.Stack;

/**
 * Classe che serve per la gestione della creazione di un dialogo, e del sistema dei dialoghi
 * Richiama semplicemente il DataXMLGrabberDialog che ritorna una pila di dialoghi
 */
public class DialogComposer {
    /**Campo infoDialog contenente le informazioni del dialogo*/
    private final String infoDialog;
    /**Campo conente il riferimento al dataGrabber adebito alla creazione dei dialoghi*/
    private DataXMLGraberDialog dtGraber;

    /**Costruttore*/
    public DialogComposer(String infoDialog, Context context)
    {
        dtGraber = new DataXMLGraberDialog("datadialog.xml",context);
        this.infoDialog = infoDialog;
    }

    /**
     * Metodo che ritorno una pila di dialoghi
     * @return Pila di Oggetti Dialogo
     */
    public Stack<Dialogo> getDialoghi()
    {
        return dtGraber.getDialoghi(infoDialog);
    }
}
