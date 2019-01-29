package com.paper.bob.rta.roadtoamsterdam.gameUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.R;
import com.paper.bob.rta.roadtoamsterdam.engine.Background;
import com.paper.bob.rta.roadtoamsterdam.engine.EngineGame;
import com.paper.bob.rta.roadtoamsterdam.engine.Ostacolo;
import com.paper.bob.rta.roadtoamsterdam.engine.Person.Personaggio;

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

    public ArrayList<Ostacolo> getOstacoli(String lvName)
    {
        ArrayList<Ostacolo> ostacoli = new ArrayList<Ostacolo>();
        NodeList livelli = radice.getChildNodes();

        for (int i = 0; i < livelli.getLength(); i++)
        {
            Node lv = livelli.item(i);
            Log.i("RTA", "Nome lv: "+lv.getAttributes().getNamedItem("name").getNodeValue());
            if(lv.getAttributes().getNamedItem("name").getNodeValue().equals(lvName)) {
                NodeList ost = lv.getFirstChild().getChildNodes();
                for (int e = 0; e < ost.getLength(); e++) {
                    String imgName = ost.item(e).getFirstChild().getTextContent();
                    int resId = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
                    Bitmap img = BitmapFactory.decodeResource(context.getResources(), resId);

                    //Posizione
                    int x = Integer.parseInt(ost.item(e).getAttributes().getNamedItem("x").getNodeValue());
                    int y = Integer.parseInt(ost.item(e).getAttributes().getNamedItem("y").getNodeValue());

                    //Grandezza Relativa
                    String tipo = ost.item(e).getAttributes().getNamedItem("tipo").getNodeValue();
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
                        default:
                            width = context.getResources().getDimensionPixelSize(R.dimen.ostacolo_normale_width);
                            height= context.getResources().getDimensionPixelSize(R.dimen.ostacolo_normale_height);
                            break;
                    }
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
            }else{}
        }

/*
        for(int i=0; i < ostacoli.size(); i++)
        {Log.i("RTA", ostacoli.get(i).toString());}
       */
        Log.i("RTA", "  Ostacoli");
        return ostacoli;
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

    public ArrayList<Personaggio> getPersonaggi(String lvName)
    {
        ArrayList<Personaggio> personaggi = new ArrayList<>();
        NodeList livelli = radice.getChildNodes();
        for (int i = 0; i < livelli.getLength(); i++)
        {
            Node lv = livelli.item(i);
            if(lv.getAttributes().getNamedItem("name").getNodeValue().equals(lvName)) {
                NodeList prs = lv.getFirstChild().getNextSibling().getNextSibling().getChildNodes();
                for (int e = 0; e < prs.getLength(); e++) {
                    String imgName = prs.item(e).getFirstChild().getTextContent();
                    int resId = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
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
                    personaggi.add(new Personaggio(img, x, y, height, width, nFrame,dialogo,notify));
                }
            }else{}
        }


        Log.i("RTA", "  Personaggi");
        return personaggi;
    }

}