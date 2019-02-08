package com.paper.bob.rta.roadtoamsterdam.engine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.paper.bob.rta.roadtoamsterdam.R;
import com.paper.bob.rta.roadtoamsterdam.activity.PlatformMainActivity;
import com.paper.bob.rta.roadtoamsterdam.engine.Person.Notify;
import com.paper.bob.rta.roadtoamsterdam.engine.Person.Personaggio;
import com.paper.bob.rta.roadtoamsterdam.engine.Person.Player;

import java.util.ArrayList;

public class EngineGame extends SurfaceView implements SurfaceHolder.Callback {

    //Proprità
    private MainThread gameLoop;
    private ArrayList<Ostacolo> ostacoli;
    private ArrayList<Personaggio> personaggi;
    private Background bg;
    private Base base;
    private Player pl;
    private ArrayList<GameObject> objColl = new ArrayList<>();
    private Controller control;
    public static int WIDTH;
    public static int HEIGHT;

    //Costruttori
    //Implementazione di Costruttori per essere leggibile anche da XML Layout
    public EngineGame(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public EngineGame(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    //Costruttore
    public EngineGame(Context context) {
        super(context);
        init(context);
    }
    //Istruzioni da eseguire su tutti i Costruttori
    @SuppressLint("ClickableViewAccessibility")
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

        //OPERAZIONI ch edefiniscono un LIVELLO, Creazione LevelComposer
        LevelComposer lvComposer = new LevelComposer("benzinaio", getContext());
        //Background
        bg = lvComposer.getBackGround();
        //Base
        base = lvComposer.getBase();
        //Ostacoli
        ostacoli = lvComposer.getOstacoli();
        //Personaggi
        personaggi = lvComposer.getPersonaggi();
        //Player & Movement Player
        pl = lvComposer.getPlayer();
        bg.setPlayer(pl);
        //Coordinate Background
        Ostacolo.setBgCoord(bg);
        Notify.setBgCoord(bg);
        Base.setBgCoord(bg);
        //Gestione Collisioni
        for(Ostacolo o : ostacoli) {if(o.getFisico()) {objColl.add(o);}}
        for(Personaggio p : personaggi) {if(p.getFisico()) {objColl.add(p);}}
        Log.i("RTA", String.valueOf(objColl.size()));
        //Controller//Collision per il Player
        control = PlatformMainActivity.getController();
        pl.setController(control);
        control.setPlayer(pl);
        control.setObjColl(objColl);
        control.setBase(base);
        //THREAD Game
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
    /**
    Metodo Update senza parametri e senza valori di return
    Questo Metodo è il metodo che viene richiamato dal MainThread cioè dal gameLoop ogni Frame.
    A cadenza di FPS questo metodo viene richiamato e deve aggiornare il Canvas su cui sono gli Object.
    Per farlo richiama i relativi metodi update() di tutti gli Object , estesi o no, istanziati nell'engine.
    */
    public void update()
    {
        //Background
        bg.update();
        //Base
        base.update();
        //Ostacoli
        for(Ostacolo o : ostacoli) {o.update();}
        //Personaggi
        for(Personaggio p : personaggi) {p.update();}
        //Player
        pl.update();
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas)
    {
        //BACKGROUND
        bg.draw(canvas);
        //Base
        base.draw(canvas);
        //OSTACOLI
        for(Ostacolo o : ostacoli)
        {o.draw(canvas);}
        //PERSONAGGI
        for(Personaggio p : personaggi)
        {p.draw(canvas);}
        //PLAYER
        pl.draw(canvas);
    }

}
