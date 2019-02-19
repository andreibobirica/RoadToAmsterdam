package com.paper.bob.rta.roadtoamsterdam.gameUtils;


import android.content.Context;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.engineDialog.Dialogo;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Stack;

public class DataXMLGraberDialog extends DataXMLGraber {
    public DataXMLGraberDialog(String file,Context c)
    {super(file,c);}

    public Stack<Dialogo> getDialoghi(String infoDialog)
    {
        Node dialogNo = getDialogNode(infoDialog);
        NodeList battute = dialogNo.getChildNodes();
        Stack<Dialogo> dialoghi = new Stack<>();
        Log.i("RTA", String.valueOf(battute.getLength())+"Leght battute");
        for(int i = 0; i< battute.getLength(); i++)
        {
            Node battuta = battute.item(i);
            if(battuta.getAttributes().getNamedItem("scelta").getNodeValue().equals(""))
            {
                Log.i("RTA","id scelta no");
                String nomeDialogo = infoDialog;
                String nomePers = battuta.getAttributes().getNamedItem("nomePers").getNodeValue();
                String nomeOtherPers;
                if(i==0)
                {nomeOtherPers = battute.item(i+1).getAttributes().getNamedItem("nomePers").getNodeValue();}
                else
                {nomeOtherPers = battute.item(i-1).getAttributes().getNamedItem("nomePers").getNodeValue();}
                String nomeImmPers = battuta.getAttributes().getNamedItem("immPers").getNodeValue();
                String nomeImmOtherPers = battuta.getAttributes().getNamedItem("immOtherPers").getNodeValue();
                String textBattuta = battuta.getFirstChild().getTextContent();
                Dialogo d = new Dialogo(nomeDialogo,nomePers,nomeOtherPers,nomeImmPers,nomeImmOtherPers,textBattuta,"",null);
                Log.i("RTA",d.toString());
                dialoghi.push(d);
            }
            else
            {
                Log.i("RTA","if scelta si");
                String nomeDialogo = infoDialog;
                String nomePers = battuta.getAttributes().getNamedItem("nomePers").getNodeValue();
                String nomeOtherPers;
                if(i==0)
                {nomeOtherPers = battute.item(i+1).getAttributes().getNamedItem("nomePers").getNodeValue();}
                else
                {nomeOtherPers = battute.item(i-1).getAttributes().getNamedItem("nomePers").getNodeValue();}
                String scelta = battuta.getAttributes().getNamedItem("scelta").getNodeValue();
                ArrayList<String> scelte = new ArrayList<>();
                NodeList scelteNode = battuta.getChildNodes();
                for(int e = 0; e < scelteNode.getLength();e++)
                {
                    scelte.add(scelteNode.item(e).getFirstChild().getNodeValue());
                }
                String nomeImmPers = battuta.getAttributes().getNamedItem("immPers").getNodeValue();
                String nomeImmOtherPers = battuta.getAttributes().getNamedItem("immOtherPers").getNodeValue();
                Dialogo d = new Dialogo(nomeDialogo,nomePers,nomeOtherPers,nomeImmPers,nomeImmOtherPers,"",scelta,scelte);
                Log.i("RTA",d.toString());
                dialoghi.push(d);
            }
        }
        Log.i("RTA","Dialoghi returnati DATAXMLGRABBER");
        Log.i("RTA","DIALOGHISIZE"+dialoghi.size());
        Stack<Dialogo> dialogoAppoggio = new Stack<>();
        int length = dialoghi.size();
        for(int z = 0 ; z < length; z++)
        {
            dialogoAppoggio.push(dialoghi.pop());
        }
        dialoghi = dialogoAppoggio;
        return dialoghi;
    }

    private Node getDialogNode(String infoDialog)
    {
        NodeList dialogs= radice.getChildNodes();
        for (int i = 0; i < dialogs.getLength(); i++)
        {
            Node dialog = dialogs.item(i);
            if(dialog.getAttributes().getNamedItem("nome").getNodeValue().equals(infoDialog)) {
                Log.i("RTA","getDialogNode");
                return dialog;
            }
        }
        return dialogs.item(0);
    }
}
