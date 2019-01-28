package com.paper.bob.rta.roadtoamsterdam.engine;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;

public class Ostacolo extends GameObject {
    private Bitmap img;
    private int nframe;
    private Animation animation = new Animation();

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
            animation.setDelay(200);
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
        Rect src = new Rect(0,0,img.getWidth()-1, img.getHeight()-1);
        Rect dest = new Rect(x,y,x+width, y+height);
        canvas.drawBitmap(img, src, dest, null);

    }

    /**
    Metodo update che viene richiamato ogni volta che si deve updatare l'oggetto Ostacolo, cioè ogni frame
     Senza parametri e senza valori di ritorno.
     */
    public void update()
    {
        if(nframe>1) {
           animation.update();
        }
    }

    /**
     * Metodo toString() che ritorna una stringa con tutte le informazioni principali e le propietà dell'oggetto.
     * @return info info Oggetto
     */
    @Override
    public String toString() {
        String info = super.toString();
        info += "\nnFrame"+nframe+"\tanimation: "+animation.toString();
        info += "\nIMG: \t"+img.toString();
        return info;
    }
}
