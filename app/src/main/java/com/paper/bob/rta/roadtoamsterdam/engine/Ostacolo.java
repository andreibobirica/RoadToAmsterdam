package com.paper.bob.rta.roadtoamsterdam.engine;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

public class Ostacolo extends GameObject {
    private Bitmap img;

    /**
    Costruttore della classe Ostacolo.
    L'oggetto ostacolo contiene tutte le informazioni di GameObject ed in più contiene l'immagine Bitmap che rapresenta la sua grafica.
    @param img l'immagine che farà da grafica per l'ostacolo
    @param x coordinata x iniziale su cui starà l'ostacolo;
    @param y coordinata y iniziale su cui starà l'ostacolo
    @param height altezza dell'ostacolo
    @param width larghezza dell'ostacolo
     */
    public Ostacolo(Bitmap img, int x, int y, int height, int width)
    {
        //Di base tutti gli ostacoli hanno la forma rettangolare con grandezza predefinita
        this.height =height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.img = img;
    }

    /**
    Metodo draw che richiamato da EngineGame.draw(Canvas c) disegna sul Canvas c la propietà IMG , cioè l'immggine.
    Questo metodo non ha valori di return in quando non fornisce dati, ma attua solo l'azione di disegnare se stesso su un canvas
    fornito.
    @param canvas Canvas oggetto canvas su cui si deve disegnare l'immmagine img.
     */
    public void draw(Canvas canvas)
    {
        Rect src = new Rect(0,0,img.getWidth()-1, img.getHeight()-1);
        Rect dest = new Rect(0,0,width-1, height-1);
        canvas.drawBitmap(img, src, dest, null);
    }

    /**
     * Metodo toString() che ritorna una stringa con tutte le informazioni principali e le propietà dell'oggetto.
     * @return info info Oggetto
     */
    @Override
    public String toString() {
        String info = super.toString();
        info += "\nIMG: \t"+img.toString();
        return info;
    }
}
