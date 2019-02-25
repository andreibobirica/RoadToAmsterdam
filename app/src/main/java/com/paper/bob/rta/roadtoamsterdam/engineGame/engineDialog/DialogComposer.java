package com.paper.bob.rta.roadtoamsterdam.engineGame.engineDialog;


import android.content.Context;

import com.paper.bob.rta.roadtoamsterdam.gameUtils.DataXMLGraberDialog;

import java.util.Stack;

public class DialogComposer {
    private final String infoDialog;
    private DataXMLGraberDialog dtGraber;

    public DialogComposer(String infoDialog, Context context)
    {
        dtGraber = new DataXMLGraberDialog("datadialog.xml",context);
        this.infoDialog = infoDialog;
    }

    public String getInfoDialog() {
        return infoDialog;
    }

    public Stack<Dialogo> getDialoghi()
    {
        return dtGraber.getDialoghi(infoDialog);
    }
}
