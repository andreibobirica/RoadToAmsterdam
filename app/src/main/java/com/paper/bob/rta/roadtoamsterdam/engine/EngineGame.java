package com.paper.bob.rta.roadtoamsterdam.engine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import java.util.ArrayList;

public class EngineGame extends SurfaceView implements SurfaceHolder.Callback {

    //Proprità
    private MainThread gameLoop;
    private ArrayList<Ostacolo> ostacoli;
    private Background bg;
    public static int WIDTH;
    public static int HEIGHT;

    //Costruttori
    //Implementazione di Costruttori per essere leggibile anche da XML
    public EngineGame(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public EngineGame(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    //Costruttore
    public EngineGame(Context context)
    {
        super(context);
        init(context);
    }
    //Istruzioni da eseguire su tutti i Costruttori
    private void init(Context c)
    {
        //GET Display Size Info
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        WIDTH = display.getWidth();  // deprecated
        HEIGHT = display.getHeight();  // deprecated

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);
        //make EngineGame focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        //OPERAZIONI ch edefiniscono un LIVELLO
        LevelComposer lvComposer = new LevelComposer("benzinaio", getContext());
        ostacoli = lvComposer.getOstacoli();
        bg = lvComposer.getBackGround();;

        gameLoop = new MainThread(getHolder(), this);
        gameLoop.setRunning(true);
        gameLoop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        int counter = 0;
        while(retry && counter<1000)
        {
            counter++;
            try{
                gameLoop.setRunning(false);
                gameLoop.join();
                retry = false;
                gameLoop = null;
            }catch(InterruptedException e){e.printStackTrace();}
        }
    }
    /*
    Metodo Update senza parametri e senza valori di return
    Questo Metodo è il metodo che viene richiamato dal MainThread cioè dal gameLoop ogni Frame.
    A cadenza di FPS questo metodo viene richiamato e deve aggiornare il Canvas su cui sono gli Object.
    Per farlo richiama i relativi metodi update() di tutti gli Object , estesi o no, istanziati nell'engine.
    */
    public void update()
    {
        bg.update(-4);
        /*
            bg.update();
            player.update();
            //update top border
            this.updateTopBorder();

            //CHECK COLLISION
            for(int i = 0; i<botborder.size(); i++)
            {
                if(collision(botborder.get(i), player))
                    player.setPlaying(false);
            }
         */
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas)
    {
        //BACKGROUND
        bg.draw(canvas);
        //OSTACOLI
        for(int i = 0; i < ostacoli.size(); i++)
        {ostacoli.get(i).draw(canvas);}

    }
}
