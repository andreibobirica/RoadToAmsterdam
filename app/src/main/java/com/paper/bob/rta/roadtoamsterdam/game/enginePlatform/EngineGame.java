/*
 * Copyright (c)
 * Road To Amsterdam, RTA
 * Andrei Cristian Bobirica - Matteo Pedron
 * Classe 5IA 2019
 */

package com.paper.bob.rta.roadtoamsterdam.game.enginePlatform;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import com.paper.bob.rta.roadtoamsterdam.activity.SoundBackgroundActivity;
import com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Objects.Background;
import com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Objects.Base;
import com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Objects.GameObject;
import com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Objects.Ostacolo;
import com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Objects.Person.Notify;
import com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Objects.Person.Personaggio;
import com.paper.bob.rta.roadtoamsterdam.game.enginePlatform.Objects.Person.Player;
import com.paper.bob.rta.roadtoamsterdam.gameUtils.Sound;

import java.util.ArrayList;

public class EngineGame extends SurfaceView implements SurfaceHolder.Callback {

    /**Campi con tutti gli elemtni che compongono il EngineGame*/
    private GameThread gameLoop;
    private ArrayList<Ostacolo> ostacoli;
    private ArrayList<Personaggio> personaggi;
    private Background bg;
    private Base base;
    private Player pl;
    private ArrayList<GameObject> objColl;
    private Controller control;
    private ArrayList<Sound> sounds;
    public static int WIDTH;
    public static int HEIGHT;
    /**Prppietà che indica la effettiva visualizzazione del EngineGame*/
    private boolean viewIsRunning = false;

    /**Nome del levelName, corrispondente al nome del livello nel file XML contenente le info per tutti i campi del EngineGame*/
    private String levelName;

    /**Campi adetti al movimento della SurfaceView utilizzando lo scorrimento con le dita*/
    private float mScaleFactor = 1.f;
    private ScaleGestureDetector mScaleDetector;
    private float mLastTouchX;
    private float mLastTouchY;
    private int mActivePointerId;
    private Context context;
    private int dx = 0;
    private int dy = 0;


    /**Costruttori*/
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
        Display display = wm != null ? wm.getDefaultDisplay() : null;
        // display size in pixels
        Point size = new Point();
        display.getSize(size);
        WIDTH = size.x;
        HEIGHT = size.y;
        if(HEIGHT>WIDTH)
        {
            int app = WIDTH;
            WIDTH = HEIGHT;
            HEIGHT = app;
        }

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);
        //make EngineGame focusable so it can handle events
        setFocusable(true);
    }

    /**Metodo che appena si crea la superficie viene richiamato e fa partire startView, cioè crea gli elemtni da far apparire e avvia il thread di gioco*/
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        startView();
    }
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {}
    /**Metodo che viene richiamto ogni volta che la superficie viene distrutta, richiamando stopView il quale stoppa il Thread di gioco*/
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        stopView();
    }
    /**
    Metodo Update senza parametri e senza valori di return
    Questo Metodo è il metodo che viene richiamato dal GameThread cioè dal gameLoop ogni Frame.
    A cadenza di FPS questo metodo viene richiamato e deve aggiornare il Canvas su cui sono gli Object.
    Per farlo richiama i relativi metodi update() di tutti gli Object , estesi o no, istanziati nell'engine.
     Inoltre muove tutti gli GameObject in base al trascinamento sullo schermo
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
        //Controlle
        control.update();
        //Movimento trascinamento touchscreen
        moveObjectTouch(dx,dy);
    }

    /**
     * Metodo che viene richiamato ogni volta a cadenza di FPS, questo significa che viene richiamto dal GameThread di gioco.
     * Questo metodo richiama per ogni elemento il proprio metodo canvas e gli passa il parametro canvas su cui disegnarsi.
     * @param canvas canvas su cui disegnare tutti gli elemti di gioco
     */
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
        //DEBUGMODE
        if(Controller.debugMode)
        {
            String info = "DebugMode-> FPS: "+ GameThread.getMAX_FPS()+" - AverageFPS: "+gameLoop.getAverangeFPS()+" - dx:"+control.getDX()+" - dy: "+control.getDY()+" - dDown: "+control.getDDown();
            info += "\n\t - x: "+pl.getX()+" -  y:"+pl.getY();
            info += "\n\t - dTime: "+control.getDTime();
            TextPaint textPaint = new TextPaint();
            textPaint.setAntiAlias(true);
            textPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
            textPaint.setColor(0xFF000000);
            int width = (int) textPaint.measureText(info);
            StaticLayout staticLayout = new StaticLayout(info, textPaint, (int) width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);
            staticLayout.draw(canvas);

        }
    }
    /**
     * Metodo startView() che inizializza la vista con le cose da vedere e lo start del Thred dedicato alla gestione degli FPS.
     * Questo metodo potrebbe essere richiamato sia dentro questa classe nel SurfaceCreated() che nell'activity PlatformActivity
     * dentro il metodo resume();
     * Viene richiamato in entrambi i casi per gestire al meglio l'android LyFECICLE.
     * Questo metodo fa una distinzione se gli elementi esistono già oppure no.
     * Nel caso gli elemtni non esistino già, cioè che l'ultimo creato cioè il Player, sia uguale NULL, allora vengono ricreati e fatto partire
     * il Thread per gli FPS.
     * Nel caso estistano già gli elementi quindi il Player sia già inizializzato, allora viene fatto partire solamente il Thread per la gestione
     * degli FPS.
     */
    public void startView()
    {
        Log.i("RTA", "Start View");

        //Scelta se si è già settata la View, quindi inizializzato i campi, e se esista l'ultimo campo importante, cioè IL PLAYER
        if (!viewIsRunning && pl == null) {
            viewIsRunning=true;//Setto la View ora visibile
            /*
            Le istruzioni successive servono per inizializzare tutti gli elementi del engineGame
            Le informazioni necessarie le trovano dal lvComposer , incaricato a comporre il livello
             */
            objColl = new ArrayList<>();
            sounds = new ArrayList<>();
            //OPERAZIONI ch edefiniscono un LIVELLO, Creazione LevelComposer
            LevelComposer lvComposer = new LevelComposer(levelName, getContext());
            //Background
            bg = lvComposer.getBackGround();
            //Base
            base = lvComposer.getBase();
            //Ostacoli
            ostacoli = lvComposer.getOstacoli();
            //Personaggi
            personaggi = lvComposer.getPersonaggi();
            //Player
            pl = lvComposer.getPlayer();
            //Suoni
            sounds = lvComposer.getSounds();
            //Sound background
            SoundBackgroundActivity.setSoundBG(lvComposer.getSoundBG());
            SoundBackgroundActivity.play();
            /*
            Le istruzzioni successive indicano parametri di confiruazione dei Elementi
            Inoltre inizia un processo di SET automati reciproco degli oggetti che comunicano tra di loro
             */
            //Movimento col touch
            mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
            /*
            Il player viene mosso dal giocatore tramite i pulsanti, il background segue il player nelle sue coordinate.
            Tutti gli altri elementi seguono il background.
             */
            bg.setPlayer(pl);
            Ostacolo.setBgCoord(bg);
            Notify.setBgCoord(bg);
            Base.setBgCoord(bg);
            //Gestione Collisioni creazione objColl, arraylist contenente tutti gli objectGame fisici con cui si può collidere
            for (Ostacolo o : ostacoli) {if (o.getFisico()) {objColl.add(o);}}
            for (Personaggio p : personaggi) {if (p.getFisico()) {objColl.add(p);}}
            objColl.add(base);
            //Controller//Collision per il Player//Movimento per altri
            pl.setController(control);
            bg.setController(control);
            control.setPlayer(pl);
            control.setObjColl(objColl);
            control.setPers(personaggi);
            //Setto al controller i suoni
            //Log.i("RTA","caricamento e play bg sound");
            control.setSounds(sounds);

            //INIZIO GAME//THREAD Game
            gameLoop = new GameThread(getHolder(), this);
            gameLoop.setRunning(true);
            gameLoop.start();
        }else if(!viewIsRunning)
        {//Se si è già effettuata la configurazione dei campi e dei elementi di gioco , si procede solo nel avviare il thread di gioco
            viewIsRunning=true;//Setto la View ora visibile
            //SUONI
            //sbg.play();
            //THREAD Game
            SoundBackgroundActivity.play();
            gameLoop = new GameThread(getHolder(), this);
            gameLoop.setRunning(true);
            gameLoop.start();
        }
        Log.i("RTA", "End View");

    }
    /**
     * Metodo stopView() che stoppa la visualizzazione della SurfaceView sullo schermo. in poche parole non lo fa più funzionare.
     * Non elimina nessuna istanza della classe, e neppure nessun campo, quelli vengono salvati , stoppa solamente il Thread di gioco che gestisce gli FPS.
     * In base al fermo del Thread di gioco che gestisce gli FPS non appparirà nulla sulla schermo, e i rispettivi metodi update() e draw()
     * NON verranno più richiamati, rendendo il gioco bloccato (IN PAUSA).
     */
    public void stopView()
    {
        Log.i("RTA", "Stop View");

        if (viewIsRunning) {
            viewIsRunning=false;//Setto la View ora NON visibile
            /*
              Fine operazioni della VIEW
             */
            boolean retry = true;
            int counter = 0;
            while (retry && counter < 100) {
                counter++;
                try {
                    gameLoop.setRunning(false);
                    gameLoop.join();
                    retry = false;
                    gameLoop = null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            SoundBackgroundActivity.stop();
            Log.i("RTA", "End Stop View");
        }
    }


    /**
     * Metodo setController() che serve a settare il controller, il campo
     * @param c istanza dell'oggetto Controller
     */
    public void setController(Controller c) {
        control = c;
    }

    /**
     * Metodo che ritorno un arraylist di Suoni, quelli che vengono uttilizzati nel game
     * @return ArrayList di Sound
     */
    public ArrayList<Sound> getSounds() {
        return sounds;
    }


    /**
     * Metodo set che imposta il nome del livello
     * @param levelName String contenente il nome del livello
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    /**
     * Metodo set che imposta il Context, il contesto, passato dal Activity
     * @param c Context contesto
     */
    public void setContext(Context c){this.context = c;}

    /**
     * Metodo sincronizzato listener che si avvia quando riceve un segnale di tocco sul SurfaceView
     * In base al tipo di movimento, e al movimento, setta delle coordinate temporanee dx e dy con cui tutti gli elemtni a schermo si muoveranno
     * @param ev MotionEvent tipo di evento di movimento
     * @return valore boleano contenente l'esito
     */
    @Override
    public synchronized boolean onTouchEvent(MotionEvent ev) {
        //*Imposto il listener sul detector*/
        mScaleDetector.onTouchEvent(ev);
        final int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            final float x = ev.getX();
            final float y = ev.getY();

            mLastTouchX = x;
            mLastTouchY = y;
            mActivePointerId = ev.getPointerId(0);
        }
        if (action == MotionEvent.ACTION_MOVE) {
                final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                final float x = ev.getX(pointerIndex);
                final float y = ev.getY(pointerIndex);
                if (!mScaleDetector.isInProgress()) {
                    final int dx = (int) (x - mLastTouchX);
                    final int dy = (int) (y - mLastTouchY);
                    this.dx = dx;
                    this.dy = dy;
                    invalidate();
                }
                mLastTouchX = x;
                mLastTouchY = y;
        }
        if (action == MotionEvent.ACTION_UP) {
            System.out.println("UP");
            this.dx = 0;
            this.dy = 0;
        }
        if (action == MotionEvent.ACTION_CANCEL) {
            System.out.println("CANCEL");
            this.dx = 0;
            this.dy = 0;
        }

        if (action == MotionEvent.ACTION_POINTER_UP) {
            System.out.println("ACTN_POINTER_UP");
            final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
            final int pointerId = ev.getPointerId(pointerIndex);
            if (pointerId == mActivePointerId) {
                final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                mLastTouchX = ev.getX(newPointerIndex);
                mLastTouchY = ev.getY(newPointerIndex);
                mActivePointerId = ev.getPointerId(newPointerIndex);
            }
        }
        return true;
    }

    /**
     * Metodo recycle che serve per riciclare gli elementi utillizzati e pulire le celle di memoria, in maniera da liberare spazio
     * e non generare leak di memoria che potrebbero rallentare l'applicazione ma anche il dispositivo stesso
     */
    public void recycle() {
        //Recycle all the Bitmap
        for(int i = 0; i < personaggi.size(); i++)
        {personaggi.get(i).getImage().recycle();}
        for(int i = 0; i < ostacoli.size(); i++)
        {ostacoli.get(i).getImage().recycle();}
        {bg.getImage().recycle();}
        {base.getImage().recycle();}
        //Setto a null così il Garbage Collector farà il suo lavoro
        getHolder().getSurface().release();
    }

    public void print(String s) {
        Log.i("RTA",s);
    }


    /**
     * Metodo listener che appena ricere che il trascinamento è stato terminato  scala le coordinate
     */
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            invalidate();
            return true;
        }
    }

    /**
     * Metodo che ricevendo due ipotetiche coordinate di movimento date dal trascinamento sullo schermo
     * le controllo e verifica se effettivamente si può ancora spostare con il dito la scena di gioco.
     * Prima però verifica dal bg se il player si può muovere e se è fuori dal suo quadro di movimento
     * @param dx vettore x
     * @param dy vettore y
     */
    private void moveObjectTouch(int dx, int dy) {
        if (bg.verifyMovementPlayer()) {
            if (pl.getY() + pl.getHeight() + dy > EngineGame.HEIGHT) {
                dy = 0;
            }
            if (base.getY() + dy < (EngineGame.HEIGHT)) {
                dy = 0;
            }
            bg.setX(bg.getX() + dx);
            bg.setY(bg.getY() + dy);
            for (Ostacolo o : ostacoli) {
                o.setX(o.getX() + dx);
                o.setY(o.getY() + dy);
            }
            for (Personaggio o : personaggi) {
                o.setX(o.getX() + dx);
                if (o.getNotify()) {
                    Notify not = o.getNot();
                    not.setX(not.getX() + dx);
                    not.setY(not.getY() + dy);
                }
                o.setY(o.getY() + dy);
            }
            base.setX(base.getX() + dx);
            base.setY(base.getY() + dy);
            pl.setX(pl.getX() + dx);
            pl.setY(pl.getY() + dy);
        }
    }
}

