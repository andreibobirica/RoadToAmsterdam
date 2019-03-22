/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.gameUtils;


import android.content.Context;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.game.engineDialog.Dialogo;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Classe Dataxml Grabber che restituisce i dati relativi ai dialoghi
 */
public class DataXMLGraberDialog extends DataXMLGraber {
    public DataXMLGraberDialog(String file,Context c)
    {super(file,c);}

    /**
     * Metodo che restituisce uno Stack di dialoghi dando come parametro un riferimento stringa del sudetto dialogo.
     * Questo metood fa principalemtne una ricerca del sudetto dialogo e lo ritorna sotto forma di Stack di Dialogo Object
     * @param infoDialog String riferimento al dialogo
     * @return Stack Dialoco , lista di dialoghi consecutivi con un singolo personaggio
     */
    public Stack<Dialogo> getDialoghi(String infoDialog)
    {
        Node dialogNo = getDialogNode(infoDialog);
        NodeList battute = dialogNo.getChildNodes();
        Stack<Dialogo> dialoghi = new Stack<>();
        //Log.i("RTA", String.valueOf(battute.getLength())+"Leght battute");
        for(int i = 0; i< battute.getLength(); i++)
        {
            Log.i("RTA","Battuta");
            Node battuta = battute.item(i);
            String nomePers = battuta.getAttributes().getNamedItem("nomePers").getNodeValue();
            String nomeImmPers = battuta.getAttributes().getNamedItem("immPers").getNodeValue();
            String nomeOtherPers;
            String nomeImmOtherPers;
            if(i==0)
            {
                nomeImmOtherPers = battute.item(i+1).getAttributes().getNamedItem("immPers").getNodeValue();
                nomeOtherPers = battute.item(i+1).getAttributes().getNamedItem("nomePers").getNodeValue();
            } else
            {
                nomeImmOtherPers = battute.item(i-1).getAttributes().getNamedItem("immPers").getNodeValue();
                nomeOtherPers = battute.item(i-1).getAttributes().getNamedItem("nomePers").getNodeValue();
            }

            if(battuta.getAttributes().getNamedItem("scelta").getNodeValue().equals(""))
            {
                String textBattuta = battuta.getFirstChild().getTextContent();
                Dialogo d = new Dialogo(infoDialog,nomePers,nomeOtherPers,nomeImmPers,nomeImmOtherPers,textBattuta,"",null);
                Log.i("RTA",d.toString());
                dialoghi.push(d);
            }
            else
            {
                String scelta = battuta.getAttributes().getNamedItem("scelta").getNodeValue();
                ArrayList<String> scelte = new ArrayList<>();
                NodeList scelteNode = battuta.getChildNodes();
                for(int e = 0; e < scelteNode.getLength();e++)
                {scelte.add(scelteNode.item(e).getFirstChild().getNodeValue());}
                Dialogo d = new Dialogo(infoDialog,nomePers,nomeOtherPers,nomeImmPers,nomeImmOtherPers,"",scelta,scelte);
                Log.i("RTA",d.toString());
                dialoghi.push(d);
            }
        }
        Stack<Dialogo> dialogoAppoggio = new Stack<>();
        int length = dialoghi.size();
        for(int z = 0 ; z < length; z++)
        {dialogoAppoggio.push(dialoghi.pop());}
        dialoghi = dialogoAppoggio;
        return dialoghi;
    }

    /**
     * Metodo che restituisce il Node Dialog dando come riferimento la stringa riferimento infoDialog
     * @param infoDialog String di riferimento che è relativa al dialogo di riferimento
     * @return Node del dialogo nel file XML
     */
    private Node getDialogNode(String infoDialog)
    {
        NodeList dialogs= radice.getChildNodes();
        for (int i = 0; i < dialogs.getLength(); i++)
        {
            Node dialog = dialogs.item(i);
            if(dialog.getAttributes().getNamedItem("nome").getNodeValue().equals(infoDialog)) {
                //Log.i("RTA","getDialogNode");
                return dialog;
            }
        }
        return dialogs.item(0);
    }
}
