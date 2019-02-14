package com.paper.bob.rta.roadtoamsterdam.engine;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.activity.DialogoActivity;
import com.paper.bob.rta.roadtoamsterdam.activity.PlatformMainActivity;
import com.paper.bob.rta.roadtoamsterdam.engine.Person.Personaggio;
import com.paper.bob.rta.roadtoamsterdam.engine.Person.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class Controller implements Parcelable {

    //Variabili con cui verificare le collisioni
    private Player pl;
    private ArrayList<GameObject> objColl;
    private Base b;
    private PlatformMainActivity plActivity;

    //VETTORI DI MOVIMENTO Player
    private final int dx = 35;
    private final int dy = 35;
    private final int dDown = 20;

    private boolean mRight=false,mLeft=false,mUp=false,mDown=true;
    /**Variabile uping che indica se si sta ancora effetuando l'azione di salto oppure no
     * inolte la variabile dTiime indica il dELAY TIME con cui il salto deve essere interroto*
     * La variabile numSalti indica il numero massimo di salti che il player può fare
     * La variabile alreadyUp serve per capire se il numero di salti sono stati completati*/
    private boolean uping=false;
    private final int dTime = 300;
    private final int numSalti = 2;
    private int alreadyUp = numSalti;

    private boolean debugMode = true;

    public Controller(){}
    /**
     * Metodo che ritorna il valore di dX
     * Il valore di dX è il vettore di movimento verso  destra e sinistra, cioè di movimento
     * @return vettore di movimento
     */
    public int getDX(){return dx;}
    /**
     * Metodo che ritorna il valore di dY
     * Il valore di dY è il vettore di movimento verso l'alto, cioè di salto
     * @return vettore di movimento
     */
    public int getDY(){return dy;}
    /**
     * Metodo che ritorna il valore di dDown
     * Il valore di dDown è il vettore di movimento verso il Basso, cioè di caduta
     * @return vettore di movimento
     */
    public int getDDown(){return dDown;}
    /**
     * Metodo che serve per settare la possibilità di movimento del Player a destra
     * @param m boolean che rappresenta la possibilità di movimento
     */
    public void setMRight(boolean m) {mRight = m;}
    /**
     * Metodo che serve per settare la possibilità di movimento del Player a sinistra
     * @param m boolean che rappresenta la possibilità di movimento
     */
    public void setMLeft(boolean m) {mLeft = m;}
    /**
     * Metodo che serve per settare la possibilità di movimento del Player in basso
     * @param m boolean che rappresenta la possibilità di movimento
     */
    public void setMDown(boolean m) {mDown = m;}
    /**
     * Metodo che serve per settare la possibilità di movimento del Player in alto
     * A differenza di tutti gli altri metodo set Movimento, questo ha un algoritmo che interferisce con la
     * variabile mUp solo per un certo periodo di tempo, il che provoca il fatto che il salto è solo una azione spontanea, durevole, e inspammabile.
     * Cioè che mentre si decide di saltare, e si preme il pulsante per saltare, una volta iniziata l'azione di salto si deve aspettare che finisca, per
     * Ripoter saltare, per che l'azione finisca devono passare il delayMillis impostato nel Handler.
     * Per capire se è passato il delay o no si fa appoggio sulla variabile uping, che indica se si sta ancora nel bel mezzo della azione salto
     * Oppure si ha finito l'azione salto.
     * @param m boolean che rappresenta la possibilità di movimento
     */
    public void setMUp(boolean m)
    {
        if(!uping  &&  alreadyUp<numSalti) {
            if(!debugMode)
                alreadyUp++;
            mUp = m;
            mDown=false;
            uping = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    mUp = false;
                    mDown=true;
                    uping= false;
                }
            }, dTime);
        }
    }
    /**
     * Metodo che ritorna se il Player può muoversi in basso
     * @return boolean che rappresenta la possibilità di movimento
     */
    public boolean getMDown()
    {
        boolean col = verCol(0,dDown);
        boolean colBase = verColBase();
        if(col || colBase){alreadyUp=numSalti-1;}
        return (!col&& mDown && !colBase);
    }
    /**
     * Metodo che ritorna se il Player può muoversi a destra
     * @return boolean che rappresenta la possibilità di movimento
     */
    public boolean getMRight()
    {return (!verCol(dx,0)&& mRight);}
    /**
     * Metodo che ritorna se il Player può muoversi a sinistra
     * @return boolean che rappresenta la possibilità di movimento
     */
    public boolean getMLeft()
    {return (!verCol(-dx,0)&& mLeft);}
    /**
     * Metodo che ritorna se il Player può muoversi in alto
     * @return boolean che rappresenta la possibilità di movimento
     */
    public boolean getMUp()
    {return (!verCol(0,-dy)&& mUp);}

    /**
     * Metodo setPlayer che serve a settare il Player, Successivamente si uttilizzerà il player per verificare la sua collisione con altri oggetti o con la Base
     * Il Rapporto tra Controller e Player è 1 a 1, esiste una istanza di Player nel Controller e d eesiste una istanza di Controller nel Player.
     * Questo perchè il controller ha bisogno delle coordinate di Player per verificare la sua collisione, e decidere i suoi possibili movimenti.
     * Questo perchè il Player ha bisogno delle decisioni del Controller, per effetuare i movimenti.
     * @param pl Player di gioco.
     */
    public void setPlayer(Player pl) {this.pl = pl;}
    /**
     * Metodo che setta gli objCol, questo metodo serve a settare tutti gli oggetti con cui il Player si potrà collidere.
     * Una volta memorizzati nel Controller gli Oggetti con cui si potrà collidere , successivi controlli verificheranno le collisioni con essi.
     * @param o ArrayList di GameObject, Lista di Oggetti con cui si potrà collidere il Player
     */
    public void setObjColl(ArrayList<GameObject> o) {this.objColl = o;}
    /**
     * Metodo set Base, il metodo serve per settare la Base con cui fare successivamente il ocntrollo di collisione
     * @param b Base del EngineGame
     */
    public void setBase(Base b){this.b = b;}
    /**
     * Metodo che confronta due Oggetti Rect e verifica se è avvenuta una collisione fra i due.
     * Il metodo ritorna un valore booleano che specifica se è avvenuta una collisione tra i due.
     * Non specifica su che lato o da che parte, solo che le aree dei due rettangoli si sono sovrapposte.
     * @param a GameObject a da confrontare con b
     * @param b GameObject b da confrontare con a
     * @return valore booleano, se true significa che è avvenuta una collisione, se false non è avvenuta una collisione
     */
    private boolean collision(GameObject a, GameObject b)
    {
        if(Rect.intersects(a.getRectangle(), b.getRectangle())) {
            return true;
        }
        return false;
    }
    /**
     * Metodo che confronta tutti gli GameObject Fisici con il Player, e ritorna se in un ipotetico movimento collidono oppure no.
     * Il metodo contronta tutti gli GameObject objCol, oggetti preventivamente creati che rappresentato tutti gli GameOnject Fisici e che possono collidere.
     * Il ipotetico movimento viene ipotizzato tramite i due vettori dx e dy, che sono parametri aggiuntivi alla effettiva posizione del Player.
     * Se il Player con i parametri aggiuntivi effetua una collisione, non gli viene permesso di muoversi, in pratica viene ritornato True, cioè collisione effetuata.
     * Se return true, effetua una collisione, se false no.
     * Sarà poi il Controller che guardando il risultato permetterà al Player di muoversi oppure no.
     * @param dx Vettore di movimento sull'asse X, viene aggiunto in caso di ipotetico movimento a sinistra o a destra
     * @param dy Vettore di movimento sull'asse Y, viene aggiunto in caso di ipotetico movimento in giù o su
     * @return valore booleano che indica se nel ipotetico movimento avviene una collisione o meno.
     */
    private boolean verCol(int dx, int dy)
    {
        boolean ret = false;
        for(GameObject g : objColl) {
            if(g.getWidth()>-50 && g.getX()<EngineGame.WIDTH && g.getHeight()>-50 && g.getY()<EngineGame.HEIGHT+50) {
                ret = (collision(new Ostacolo(null, pl.getX() + dx, pl.getY() + dy, pl.getHeight(), pl.getWidth(), 0), g));
                if (ret){
                    if(g.getTipo().equals("Personaggio"))
                    {
                        Personaggio p = (Personaggio) g;
                        Log.i("RTA","Not: "+p.getNotify());
                        this.avviaDialogo(p.getDialogo());
                        p.setFisico(false);
                        p.setNotify(false);
                        objColl.remove(g);
                    }
                    break;
                }
            }
        }
        return ret;
    }
    /**
     * Metodo che verifica se il Player collide con la base oppure no.
     * Il metodo è autonomo e non è integrato dentro verCol() perchè la collisione con la Base è una cosa assestante, inanzitutto se non collidesse
     * con la base si creerebbe un Bug grande e per questo motivo si da priorità alla base, controllando il palyer ed esse univocamente insieme.
     * @return
     */
    private boolean verColBase()
    {
        Player pl2 = new Player(pl);
        pl2.setY(pl.getY()+dy);
        pl2.setHeight(pl.getHeight()+dDown);
        return(collision(pl2,b));
    }

    public void setPlActivity(PlatformMainActivity pl)
    {plActivity = pl;}

    private void avviaDialogo(String d)
    {
        plActivity.avviaDialogo(d);
    }

    /**
     * Metodo to String che restituisce in formato stringa tutte le informazioni principali di Controller
     * Principalmente dove e dove non si può effetuare un movimento
     * Oltre che i parametri di movimento del Player.
     * @return
     */
    public String toString() {return "m Left: "+mLeft+"\tm Right: "+mRight+"\tm Up: "+mUp+"\ndx: "+dx+"\tdy: "+dy+"\tdDown: "+dDown;}
    ////////////////////////////////////////////////////////////

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable((Parcelable) this.pl, flags);
        dest.writeList(this.objColl);
        dest.writeParcelable((Parcelable) this.b, flags);
        dest.writeParcelable((Parcelable) this.plActivity, flags);
    }

    protected Controller(Parcel in) {
        this.pl = in.readParcelable(Player.class.getClassLoader());
        this.objColl = new ArrayList<GameObject>();
        in.readList(this.objColl, GameObject.class.getClassLoader());
        this.b = in.readParcelable(Base.class.getClassLoader());
        this.plActivity = in.readParcelable(PlatformMainActivity.class.getClassLoader());
    }

    public static final Parcelable.Creator<Controller> CREATOR = new Parcelable.Creator<Controller>() {
        @Override
        public Controller createFromParcel(Parcel source) {
            return new Controller(source);
        }

        @Override
        public Controller[] newArray(int size) {
            return new Controller[size];
        }
    };
}
