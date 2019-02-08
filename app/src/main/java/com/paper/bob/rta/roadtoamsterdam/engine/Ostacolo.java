package com.paper.bob.rta.roadtoamsterdam.engine;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Ostacolo extends GameObject {
    //Campi che definiscono l'ostacolo
    private Bitmap img;
    private int nframe;
    private Animation animation = new Animation();
    private boolean fisico = false;

    //Campo bgCoord, cioè riferimento al Background per seguirne i movimenti e le sue coordinate
    private static Background bgCoord;

    //Campi contatore per tenere traccia di quanti Ostacoli ci sono
    private static int n = 0;
    private int contatore;
    /**
    Costruttore della classe Ostacolo.
    L'oggetto ostacolo contiene tutte le informazioni di GameObject ed in più contiene l'immagine Bitmap che rapresenta la sua grafica.
    @param img l'immagine che farà da grafica per l'ostacolo
    @param x coordinata x iniziale su cui starà l'ostacolo;
    @param y coordinata y iniziale su cui starà l'ostacolo
    @param height altezza dell'ostacolo
    @param width larghezza dell'ostacolo
     @param nframe numero di frame di una animazione, 1 se non è presente, quindi è una immagine statica
     */
    public Ostacolo(Bitmap img, int x, int y, int height, int width,int nframe)
    {
        //Contatore Ostacoli
        n++;
        contatore = n;
        //Grandezza
        this.height =height;
        this.width = width;
        //Coordinate
        this.x = x;
        this.y = y;
        //Immagine
        this.img = img;
        this.nframe = nframe;
        //Animazione se presente
        if(nframe>1) {
            Bitmap[] gif = new Bitmap[nframe];
            for (int i = 0; i < gif.length; i++) {
                gif[i] = Bitmap.createBitmap(img, i * (img.getWidth()/nframe), 0, img.getWidth()/nframe, img.getHeight());
            }
            animation.setFrames(gif);
            //Se 2 frame = 100 delay - Se 6 frame = 200 delay
            //Per ogni frame 25 di delay in più
            int delay = 50+nframe*50;
            animation.setDelay(delay);
        }
    }
    /**
    Metodo draw che richiamato da EngineGame.draw(Canvas c) disegna sul Canvas c la propietà IMG , cioè l'immggine.
    Questo metodo non ha valori di return in quando non fornisce dati, ma attua solo l'azione di disegnare se stesso su un canvas
    fornito.
    @param canvas Canvas oggetto canvas su cui si deve disegnare l'immmagine img.
     */
    public void draw(Canvas canvas)
    {
        if(nframe>1) {
           img = animation.getImage();
        }
        if(x<EngineGame.WIDTH && y <EngineGame.HEIGHT)
        {
            Rect src = new Rect(0,0,img.getWidth()-1, img.getHeight()-1);
            Rect dest = new Rect(x,y,x+width, y+height);
            canvas.drawBitmap(img, src, dest, null);
        }
    }
    /**
    Metodo update che viene richiamato ogni volta che si deve updatare l'oggetto Ostacolo, cioè ogni frame
     Senza parametri e senza valori di ritorno.
     */
    public void update()
    {
        this.x += bgCoord.getDX();
        this.y += bgCoord.getDY();
        if(nframe>1) {
           animation.update();
        }
    }
    /**
     * Metodo che setta la propietà booleana fisico, cioè se l'ostacolo è fisico, cioè collide, oppure è immateriale, cioè rimane alle spalle
     * Nel caso la propietà fisico della classe è true, l'ostacolo può collidere con il Player.
     * Nel caso la propietà fisico della classe è false, l'ostacolo rimane inmateriale, e non collide, rimane semplicemente alle
     * spalle del Player al suo passaggio.
     * @param p valore boleano che spiega se l'ostacolo è fisico, cioè collide, oppure è immateriale, cioè rimane alle Spalle del Player
     */
    public void setFisico(boolean f){fisico = f;}
    /**
     * Metodo che restituisce un valore booleano che sta a significare se l'oggetto è fisico o no, sta a significare se l'oggetto ha
     * la possibilità di collidere col player, bloccandolo.
     * Nel caso la propietà fisico della classe è true, l'ostacolo può collidere con il Player.
     * Nel caso la propietà fisico della classe è false, l'ostacolo rimane inmateriale, e non collide, rimane semplicemente alle
     * spalle del Player al suo passaggio.
     * @return fisico propietà booleana che spiega se l'ostacolo è fisico, cioè collide, oppure è immateriale, cioè rimane alle Spalle del Player
     */
    public boolean getFisico(){return fisico;}
    /**
        Metodo che setta il Background per gli ostacoli, il riferimento al background per gli ostacoli serve, perchè se la posizione del Background
        Cambia, deve cambiare anche quella degli ostacoli, perchè a ruota gli ostacoli stanno fissi sul background, quindi devono muoversi con esse
        @param bg Background da settare, da salvarsi il riferimento, in maniera tale da avere le sue coordinate
     */
    public static void setBgCoord(Background bg) {bgCoord = bg;}
    /**
     * Metodo che ritorna il riferimento al Background in caso altri elementi abbiano bisogno delle coordinate del background
     * Per esempio l'oggetto Notify ha bisogno delle coordinate epr sporstarsi conseguitivamente anche lui.
     * @return bgCoord Oggetto background che ritorna per le sue coordinate
     */
    public static Background getBgCoord() {return bgCoord;}
    /**
     * Metodo toString() che ritorna una stringa con tutte le informazioni principali e le propietà dell'oggetto.
     * @return info info Oggetto
     */
    @Override
    public String toString() {
        String info = super.toString();
        info += "\nnFrame: "+nframe+"\tanimation: "+animation.toString();
        info += "\nIMG: \t"+img.toString();
        return info;
    }
    /**
     * Metodo che ritorna l'immagine, serve principalmente per costruttori di copia
     * @return img Bitmap con l'immagine dell'ostacolo
     */
    public Bitmap getImage()
    {return img;}
    public void setImage(Bitmap img){this.img = img;}
    /**
     * Metodo che ritorna il numero di frame
     * @return numero int di frame
     */
    public int getNFrame()
    {return nframe;}
}
