package com.paper.bob.rta.roadtoamsterdam.engine;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import com.paper.bob.rta.roadtoamsterdam.engine.utils.MainThread;

public class EngineGame extends SurfaceView implements SurfaceHolder.Callback {

    //Proprità
    private LevelCreator lvCreator;
    private MainThread gameLoop;

    //Costruttori
    //Implementazione di Costruttori per essere leggibile anche da XML
    public EngineGame(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public EngineGame(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public EngineGame(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    //Costruttore
    public EngineGame(Context context)
    {
        super(context);
        init();
    }
    //Istruzioni da eseguire su tutti i Costruttori
    private void init()
    {
        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);
        //make EngineGame focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        lvCreator = new LevelCreator("");
    }
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }
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
    public void update() {

    }
}
