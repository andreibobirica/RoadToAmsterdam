package com.paper.bob.rta.roadtoamsterdam.gameUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.engine.Background;
import com.paper.bob.rta.roadtoamsterdam.engine.Ostacolo;
import com.paper.bob.rta.roadtoamsterdam.engine.Player;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class DataGraber {

    private Element radice;
    private Context context;
    /**
     *Costruttore completo
     *@param nomeFile nome del file xml
     */
    public DataGraber(String nomeFile, Context context) {
        this.context = context;
		/*
		* Cosa servono queste 4 righe?
		* Servono ad allocare il campo radice con il riferimento del nodo radice del documento XML con nome passato come parametro costruttore
		*/
        try {
            InputStream is = context.getAssets().open(nomeFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document documento = dBuilder.parse(is);
            radice = documento.getDocumentElement();
            radice.normalize();
        }//try
		/*
		*DOM E SAX sono due interfacce che si completano, servono anche per il Parser XML e per gestire vari errori come la ricerca del riferimento del file
		*
		*/
        catch (SAXParseException e) {
            System.out.println("Errore di parsing: " + e.getMessage());
            Log.i("RTA","Errore di parsing: " + e.getMessage());
        }//catch
        catch (FileNotFoundException e) {
            System.out.println("File " + nomeFile + " non trovato");
            Log.i("RTA", "File " + nomeFile + " non trovato"+e.getMessage());
            System.exit(1);
        }//catch
		/*
		* Eccezione madre, superclasse delle ecezzioni, serve per verificare che in ultimo, nel caso nessuna delle eccezzionie
		* precedente non sia stata soddisfatta, serve per essere sicuri che venga eseguita una eccezzione
		*/
        catch (Exception e) {
            e.printStackTrace();
        }//catch
    }


    public Background getBackground(String lvName)
    {
        Background bg = null;
        String bgName = "";
        NodeList livelli = radice.getChildNodes();
        for (int i = 0; i < livelli.getLength(); i++)
        {
            Node lv = livelli.item(i);
            if(lv.getAttributes().getNamedItem("name").getNodeValue().equals(lvName)) {
                bgName = lv.getFirstChild().getNextSibling().getFirstChild().getTextContent();
                break;
            }else{}
        }

        int resId = context.getResources().getIdentifier(bgName, "drawable", context.getPackageName());
        Bitmap img = BitmapFactory.decodeResource(context.getResources(), resId);
        bg = new Background(img);
        Log.i("RTA", "Background");
        return bg;
    }

    public Player getPlayer(String lvName)
    {
        Player pl = null;
        String plName = "";
        NodeList livelli = radice.getChildNodes();
        for (int i = 0; i < livelli.getLength(); i++)
        {
            Node lv = livelli.item(i);
            if(lv.getAttributes().getNamedItem("name").getNodeValue().equals(lvName)) {
                plName = lv.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getTextContent();
                break;
            }else{}
        }
        int resId = context.getResources().getIdentifier(plName, "drawable", context.getPackageName());
        Bitmap img = BitmapFactory.decodeResource(context.getResources(), resId);
        pl = new Player(img);
        Log.i("RTA", "Player");
        return pl;
    }


    public ArrayList<Ostacolo> getOstacoli(String lvName)
    {
        ArrayList<Ostacolo> ostacoli = new ArrayList<Ostacolo>();
        NodeList livelli = radice.getChildNodes();

        for (int i = 0; i < livelli.getLength(); i++)
        {
            Node lv = livelli.item(i);
            if(lv.getAttributes().getNamedItem("name").getNodeValue().equals(lvName)) {
                NodeList ost = lv.getFirstChild().getChildNodes();
                for (int e = 0; e < ost.getLength(); e++) {
                    String imgName = ost.item(e).getFirstChild().getTextContent();
                    int resId = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
                    Bitmap img = BitmapFactory.decodeResource(context.getResources(), resId);

                    int x = Integer.parseInt(ost.item(e).getAttributes().getNamedItem("x").getNodeValue());
                    int y = Integer.parseInt(ost.item(e).getAttributes().getNamedItem("y").getNodeValue());
                    int width = Integer.parseInt(ost.item(e).getAttributes().getNamedItem("width").getNodeValue());
                    int height = Integer.parseInt(ost.item(e).getAttributes().getNamedItem("height").getNodeValue());

                    ostacoli.add(new Ostacolo(img, x, y, height, width));
                }
            }else{}
        }
        //DEBUG METODO
        /*
        for(int i=0; i < ostacoli.size(); i++)
        {Log.i("RTA", ostacoli.get(i).toString());}*/
        Log.i("RTA", "  Ostacoli");
        return ostacoli;
    }

}