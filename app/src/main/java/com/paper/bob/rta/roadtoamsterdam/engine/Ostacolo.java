package com.paper.bob.rta.roadtoamsterdam.engine;
import android.graphics.Bitmap;

public class Ostacolo extends GameObject {
    private Bitmap img;

    /*
    Costruttore della classe Ostacolo.
    L'oggetto ostacolo contiene tutte le informazioni di GameObject ed in più contiene l'immagine Bitmap che rapresenta la sua grafica.
    Inizialmente la larghezza e la lunghezza viene impostata da questo costruttore a una grandezza fissa.
    Il costruttore ha come parametri:
    @param img l'immagine che farà da grafica per l'ostacolo
    @x coordinata x iniziale su cui starà l'ostacolo;
    @y coordinata y iniziale su cui starà l'ostacolo
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

    @Override
    public String toString() {
        String info = super.toString();
        info += "\nIMG: \n"+img.toString();
        return info;
    }
}
