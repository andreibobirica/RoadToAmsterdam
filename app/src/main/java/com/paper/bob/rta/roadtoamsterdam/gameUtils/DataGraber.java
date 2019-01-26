package com.paper.bob.rta.roadtoamsterdam.gameUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.engine.Ostacolo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class DataGraber {

    private Element radice;
    /**
     *Costruttore completo
     *@param nomeFile nome del file xml
     */
    public DataGraber(String nomeFile, Context context) {
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
            Log.i("RTA", "DataGraber Creato");
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


    public ArrayList<Ostacolo> getOstacoli(String lvName)
    {
        Log.i("RTA", "DataGraber.sgetOstacoli() Avviato");
        ArrayList<Ostacolo> ostacoli = new ArrayList<Ostacolo>();
        NodeList livelli = radice.getChildNodes();
        Log.i("RTA", String.valueOf(livelli.getLength()));
        /*
        for (int i = 0; i < livelli.getLength(); i++)
        {
            Log.i("RTA", "Ispezione item: "+i);
            Log.i("RTA", "Ispezione item: "+i);
            String x = livelli.item(i).getAttributes().getNamedItem("x").getNodeValue();
            Log.i("RTA", "Ispezione item: "+i);
            int y = Integer.parseInt(livelli.item(i).getAttributes().getNamedItem("y").getNodeValue());
            Log.i("RTA", "Ispezione item: "+i);
            int width = Integer.parseInt(livelli.item(i).getAttributes().getNamedItem("width").getNodeValue());
            int height = Integer.parseInt(livelli.item(i).getAttributes().getNamedItem("height").getNodeValue());
            Log.i("RTA", "Ispezione item: "+i);
            String imgName = livelli.item(i).getFirstChild().getTextContent();


            Uri path = Uri.parse("android.resource://com.paper.bob.rta.roadtoamsterdam.gameUtils"+imgName);
            String pathIMG = path.toString();
            Bitmap img = BitmapFactory.decodeFile(pathIMG);
            ostacoli.add(new Ostacolo(img,Integer.parseInt(x),y,height,width));
            Log.i("RTA", "pathIMG");
            //System.out.println(pathIMG);
            Log.i("RTA", ostacoli.get(i).toString());
            //System.out.println(ostacoli.get(i).toString());
        }*/
        return null;
    }

}