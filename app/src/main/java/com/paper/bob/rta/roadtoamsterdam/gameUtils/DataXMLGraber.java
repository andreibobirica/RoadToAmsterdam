/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.gameUtils;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXParseException;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Classe DataXMLGraber che è una classe che verrà estesta, questa classe ha motivo solamente puramente sintattico, in quanto per dividere codice
 * del XML DOM dalle richieste che si fanno utilizzando l'XML Dom
 */
public class DataXMLGraber {

    /**
     * Elmento radice del documento
     */
    public Element radice;
    /**Cotex di riferimento del EngineGame*/
    Context context;
    /**
     *Costruttore completo
     *@param nomeFile nome del file xml
     */
    public DataXMLGraber(String nomeFile, Context context) {
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
}