package com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Person;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Animation;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Background;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.EngineGame;
import com.paper.bob.rta.roadtoamsterdam.gameUtils.DataXMLGraberPlatform;

public class Notify{

    private final int height;
    private final int width;
    private int y;
    private int x;
    private final Bitmap img;
    private Bitmap imgNot;
    private Animation anim = new Animation();

    private static DataXMLGraberPlatform dg;
    private static Background bgCoord;
    private boolean prima =true;
    private static final int nframe = 1;

    public Notify(Bitmap img,int x, int y, int width, int height)
    {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Canvas c)
    {
        if(prima)
        {
            prima = false;
            imgNot = dg.getNotifyImage();
            if(nframe>1) {
                Bitmap[] gif = new Bitmap[nframe];
                for (int i = 0; i < gif.length; i++) {
                    gif[i] = Bitmap.createBitmap(img, i * (img.getWidth()/nframe), 0, img.getWidth()/nframe, img.getHeight());
                }
                anim.setFrames(gif);
                //Se 2 frame = 100 delay - Se 6 frame = 200 delay
                //Per ogni frame 25 di delay in più
                int delay = 50+nframe*25;
                anim.setDelay(delay);
            }
        }
        //if(nframe>1) imgNot = anim.getImage();
        if(x<EngineGame.WIDTH && y <EngineGame.HEIGHT) {
            Rect srcNot = new Rect(0, 0, imgNot.getWidth() - 1, imgNot.getHeight() - 1);
            // Rect destNot = new Rect(((width / 3) + x), y - 100, x + width - (width / 3), y);
            Rect destNot = new Rect(x, y - 150, x + width - (width / 3), y);
            c.drawBitmap(imgNot, srcNot, destNot, null);
        }
    }
    public void update()
    {
        this.x += bgCoord.getDX();
        this.y += bgCoord.getDY();
        //if(nframe>1)anim.update();
    }

    public static void setDG(DataXMLGraberPlatform datag)
    {dg = datag;}

    public static void setBgCoord(Background bg)
    {bgCoord = bg;}


}
