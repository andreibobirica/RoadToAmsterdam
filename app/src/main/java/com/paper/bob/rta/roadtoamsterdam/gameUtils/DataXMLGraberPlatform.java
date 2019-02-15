package com.paper.bob.rta.roadtoamsterdam.gameUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.R;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.EngineGame;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Background;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Base;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Ostacolo;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Person.Personaggio;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Person.Player;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class DataXMLGraberPlatform extends DataXMLGraber {

    public DataXMLGraberPlatform(String file,Context c)
    {super(file,c);}

    private Node getLevel(String lvName)
    {
        NodeList livelli = radice.getChildNodes();
        for (int i = 0; i < livelli.getLength(); i++)
        {
            Node lv = livelli.item(i);
            if(lv.getAttributes().getNamedItem("name").getNodeValue().equals(lvName)) {
                return lv;
            }
        }
        return livelli.item(0);
    }

    private int[] getGrandezza(String tipo)
    {
        int width,height;
        switch (tipo) {
            case "piccolo":
                width = context.getResources().getDimensionPixelSize(R.dimen.ostacolo_piccolo_width);
                height= context.getResources().getDimensionPixelSize(R.dimen.ostacolo_piccolo_height);
                break;
            case "normale":
                width = context.getResources().getDimensionPixelSize(R.dimen.ostacolo_normale_width);
                height= context.getResources().getDimensionPixelSize(R.dimen.ostacolo_normale_height);
                break;
            case "normale_oriz":
                width = context.getResources().getDimensionPixelSize(R.dimen.ostacolo_normale_oriz_width);
                height= context.getResources().getDimensionPixelSize(R.dimen.ostacolo_normale_oriz_height);
                break;
            case "grande":
                width = context.getResources().getDimensionPixelSize(R.dimen.ostacolo_grande_width);
                height= context.getResources().getDimensionPixelSize(R.dimen.ostacolo_grande_height);
                break;
            case "piattaforma_normale":
                width = context.getResources().getDimensionPixelSize(R.dimen.piattaforma_normale_width);
                height= context.getResources().getDimensionPixelSize(R.dimen.piattaforma_normale_height);
                break;
            case "muro":
                width = context.getResources().getDimensionPixelSize(R.dimen.muro_width);
                height= context.getResources().getDimensionPixelSize(R.dimen.muro_height);
                break;
            default:
                width = context.getResources().getDimensionPixelSize(R.dimen.ostacolo_normale_width);
                height= context.getResources().getDimensionPixelSize(R.dimen.ostacolo_normale_height);
                break;
        }
        return new int[]{width,height};
    }

    private int[] positionAdapter(int x, int y)
    {
        int[] ret= new int[2];
        int screenWidth = EngineGame.WIDTH;
        int screenHeight = EngineGame.HEIGHT;
        //Position x and y stay for Coordinates in 1280 and 720 pxs
        if(x==0)
        {ret[0]=0;}
        else
        {ret[0]=(x*screenWidth)/1280;}

        if(y==0)
        {ret[1]=0;}
        else
        {ret[1]=(y*screenHeight)/720;}
        return  ret;
    }

    public Background getBackground(String lvName)
    {
        int resId = context.getResources().getIdentifier(getLevel(lvName).getFirstChild().getNextSibling().getFirstChild().getTextContent(), "drawable", context.getPackageName());
        Bitmap img = BitmapFactory.decodeResource(context.getResources(), resId);
        Background bg = new Background(img,0,0);
        Log.i("RTA", "- Background");
        return bg;
    }

    public ArrayList<Ostacolo> getOstacoli(String lvName)
    {
        ArrayList<Ostacolo> ostacoli = new ArrayList<Ostacolo>();
        NodeList ost = getLevel(lvName).getFirstChild().getChildNodes();//Ostacoli Lista
        for (int e = 0; e < ost.getLength(); e++) {
            //IMAGE
            int resId = context.getResources().getIdentifier(ost.item(e).getFirstChild().getTextContent(), "drawable", context.getPackageName());
            Bitmap img = BitmapFactory.decodeResource(context.getResources(), resId);
            //Posizione
            int x = Integer.parseInt(ost.item(e).getAttributes().getNamedItem("x").getNodeValue());
            int y = Integer.parseInt(ost.item(e).getAttributes().getNamedItem("y").getNodeValue());
            //Grandezza Relativa
            String tipo = ost.item(e).getAttributes().getNamedItem("tipo").getNodeValue();
            int width = getGrandezza(tipo)[0];
            int height = getGrandezza(tipo)[1];
            //Individuazione n. frame
            int nFrame = Integer.parseInt(ost.item(e).getAttributes().getNamedItem("frame").getNodeValue());
            //Individuazione se fisico
            boolean fisico = Boolean.parseBoolean(ost.item(e).getAttributes().getNamedItem("fisico").getNodeValue());
            //Adattamento della risoluzione relativa
            x = positionAdapter(x,y)[0];
            y = positionAdapter(x,y)[1];
            //Creazione Ostacolo
            Ostacolo o = new Ostacolo(img, x, y, height, width, nFrame);
            o.setFisico(fisico);
            ostacoli.add(o);
        }

        Log.i("RTA", "- Ostacoli");
        return ostacoli;
    }

    public Bitmap getNotifyImage()
    {
        int resId = context.getResources().getIdentifier("notify", "drawable", context.getPackageName());
        Bitmap imgNot = BitmapFactory.decodeResource(context.getResources(), resId);
        return imgNot;
    }

    public ArrayList<Personaggio> getPersonaggi(String lvName)
    {
        ArrayList<Personaggio> personaggi = new ArrayList<>();
        NodeList prs = getLevel(lvName).getFirstChild().getNextSibling().getNextSibling().getChildNodes();
        for (int e = 0; e < prs.getLength(); e++) {
            //Image
            int resId = context.getResources().getIdentifier(prs.item(e).getFirstChild().getTextContent(), "drawable", context.getPackageName());
            Bitmap img = BitmapFactory.decodeResource(context.getResources(), resId);
            //Posizione
            int x = Integer.parseInt(prs.item(e).getAttributes().getNamedItem("x").getNodeValue());
            int y = Integer.parseInt(prs.item(e).getAttributes().getNamedItem("y").getNodeValue());
            //Grandezza Relativa
            int width,height;
            width = context.getResources().getDimensionPixelSize(R.dimen.person_width);
            height= context.getResources().getDimensionPixelSize(R.dimen.person_height);
            //Individuazione n. frame
            int nFrame = Integer.parseInt(prs.item(e).getAttributes().getNamedItem("frame").getNodeValue());
            //Adattamento della risoluzione relativa
            x = positionAdapter(x,y)[0];
            y = positionAdapter(x,y)[1];
            //Individuazione del dialogo
            String dialogo = prs.item(e).getAttributes().getNamedItem("dialogo").getNodeValue();
            //Individuazione notify
            boolean notify = Boolean.parseBoolean(prs.item(e).getAttributes().getNamedItem("notify").getNodeValue());
            //Creazione Personaggio
            Personaggio p = new Personaggio(img, x, y, height, width, nFrame,dialogo,notify);
            personaggi.add(p);
        }
        Log.i("RTA", "- Personaggi");
        return personaggi;
    }

    public Player getPlayer(String lvName)
    {
        Node pl = getLevel(lvName).getFirstChild().getNextSibling().getNextSibling().getNextSibling();
        //Estrapolazione immagini
        //Immagine di base
        String imgName = pl.getFirstChild().getTextContent();
        int resId = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
        Bitmap img = BitmapFactory.decodeResource(context.getResources(), resId);
        //IMMAGINI LEFT E RIGHT
        int resIdL = context.getResources().getIdentifier(imgName+"left", "drawable", context.getPackageName());
        Bitmap imgL = BitmapFactory.decodeResource(context.getResources(), resIdL);
        int resIdR = context.getResources().getIdentifier(imgName+"right", "drawable", context.getPackageName());
        Bitmap imgR = BitmapFactory.decodeResource(context.getResources(), resIdR);
        //Posizione
        int x = Integer.parseInt(pl.getAttributes().getNamedItem("x").getNodeValue());
        int y = Integer.parseInt(pl.getAttributes().getNamedItem("y").getNodeValue());
        //Grandezza Relativa
        int width,height;
        width = context.getResources().getDimensionPixelSize(R.dimen.person_width);
        height= context.getResources().getDimensionPixelSize(R.dimen.person_height);
        //Individuazione n. frame
        int nFrame = Integer.parseInt(pl.getAttributes().getNamedItem("frame").getNodeValue());
        //Adattamento della risoluzione relativa
        x = positionAdapter(x,y)[0];
        y = positionAdapter(x,y)[1];
        //CREATING ANIMATION GIF

        //Creazione Player
        Player play = new Player(img,x,y,height,width,nFrame);
        play.setJumpLAnim(imgL);
        play.setJumpRAnim(imgR);
        play.setLeftAnim(imgL);
        play.setRightAnim(imgR);
        Log.i("RTA", "- Player");
        return play;
    }

    public Base getBase(String lvName)
    {
        Node pl = getLevel(lvName).getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling();
        int resId = context.getResources().getIdentifier(pl.getFirstChild().getTextContent(), "drawable", context.getPackageName());
        Bitmap img = BitmapFactory.decodeResource(context.getResources(), resId);
        Base base = new Base(img);
        Log.i("RTA", "- Base");
        return base;
    }
}


