package com.paper.bob.rta.roadtoamsterdam.gameUtils;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.R;
import com.paper.bob.rta.roadtoamsterdam.engine.Ostacolo;

import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by HP on 26/01/2019.
 */

public class DataGraber {
    private XmlResourceParser parser;
    public DataGraber(String nomeFile, Context context)
    {

        parser = context.getResources().getXml(R.xml.datalevels);
        if(nomeFile == "datalevels.xml")
        {parser = context.getResources().getXml(R.xml.datalevels);}
    }

    public void getOstacoli(String lvName) {

        try {

            int eventType = -1;
            boolean findLvl = false;
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String locationValue = parser.getName();
                    Log.i("RTA",  parser.getName());
                    if (locationValue.equals(lvName)) {
                        findLvl = true;
                        eventType = parser.nextTag();
                        //String city = parser.getAttributeValue(null, "city");
                    }
                    else if(findLvl)
                    {
                        ArrayList<Ostacolo> ostacoli = new ArrayList<Ostacolo>();
                        parser.
                        for (int i = 0; i < livelli.getLength(); i++)
                        {
                            int x = Integer.parseInt(livelli.item(i).getAttributes().getNamedItem("x").getNodeValue());
                            int y = Integer.parseInt(livelli.item(i).getAttributes().getNamedItem("y").getNodeValue());
                            int width = Integer.parseInt(livelli.item(i).getAttributes().getNamedItem("width").getNodeValue());
                            int height = Integer.parseInt(livelli.item(i).getAttributes().getNamedItem("height").getNodeValue());
                            String imgName = livelli.item(i).getFirstChild().getTextContent();

                            Uri path = Uri.parse("android.resource://com.paper.bob.rta.roadtoamsterdam.gameUtils"+imgName);
                            String pathIMG = path.toString();
                            Bitmap img = BitmapFactory.decodeFile(pathIMG);
                            ostacoli.add(new Ostacolo(img,x,y,height,width));
                            Log.i("xmlParser", "pathIMG");
                            //System.out.println(pathIMG);
                            Log.i("xmlParser", ostacoli.get(i).toString());
                            //System.out.println(ostacoli.get(i).toString());
                        }
                    }
                }
                eventType = parser.next();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
/*
    public ArrayList<Ostacolo> getOstacoli(String lvName)
    {
        ArrayList<Ostacolo> ostacoli = new ArrayList<Ostacolo>();
        NodeList livelli = radice.getChildNodes();
        for (int i = 0; i < livelli.getLength(); i++)
        {
            int x = Integer.parseInt(livelli.item(i).getAttributes().getNamedItem("x").getNodeValue());
            int y = Integer.parseInt(livelli.item(i).getAttributes().getNamedItem("y").getNodeValue());
            int width = Integer.parseInt(livelli.item(i).getAttributes().getNamedItem("width").getNodeValue());
            int height = Integer.parseInt(livelli.item(i).getAttributes().getNamedItem("height").getNodeValue());
            String imgName = livelli.item(i).getFirstChild().getTextContent();

            Uri path = Uri.parse("android.resource://com.paper.bob.rta.roadtoamsterdam.gameUtils"+imgName);
            String pathIMG = path.toString();
            Bitmap img = BitmapFactory.decodeFile(pathIMG);
            ostacoli.add(new Ostacolo(img,x,y,height,width));
            Log.i("xmlParser", "pathIMG");
            //System.out.println(pathIMG);
            Log.i("xmlParser", ostacoli.get(i).toString());
            //System.out.println(ostacoli.get(i).toString());
        }
        return null;
    }

*/
}
