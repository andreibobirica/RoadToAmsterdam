package com.paper.bob.rta.roadtoamsterdam.engine;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Ostacolo extends GameObject {
    private Bitmap img;

    /**
    Costruttore della classe Ostacolo.
    L'oggetto ostacolo contiene tutte le informazioni di GameObject ed in più contiene l'immagine Bitmap che rapresenta la sua grafica.
    Inizialmente la larghezza e la lunghezza viene impostata da questo costruttore a una grandezza fissa.
    Il costruttore ha come parametri:
    @param img l'immagine che farà da grafica per l'ostacolo
    @param x coordinata x iniziale su cui starà l'ostacolo;
    @param y coordinata y iniziale su cui starà l'ostacolo
     */
    public Ostacolo(Bitmap img, int x, int y)
    {
        //Di base tutti gli ostacoli hanno la forma rettangolare con grandezza predefinita
        height = 100;
        width = 50;
        this.x = x;
        this.y = y;
        this.img = Bitmap.createBitmap(img, 0, 0, width, height);
    }
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
        this.img = Bitmap.createBitmap(img, 0, 0, width, height);
    }

    /**
    Metodo draw che richiamato da EngineGame.draw(Canvas c) disegna sul Canvas c la propietà IMG , cioè l'immggine.
    Questo metodo non ha valori di return in quando non fornisce dati, ma attua solo l'azione di disegnare se stesso su un canvas
    fornito.
    @param canvas Canvas oggetto canvas su cui si deve disegnare l'immmagine img.
     */
    public void draw(Canvas canvas)
    {
        float multiplier = (float)(EngineGame.WIDTH / EngineGame.HEIGHT);
        Matrix bgMatrix = new Matrix();
        bgMatrix.postScale(multiplier, multiplier);
        canvas.drawBitmap(img, bgMatrix, paint);
        canvas.drawBitmap(img,x,y,null);
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
