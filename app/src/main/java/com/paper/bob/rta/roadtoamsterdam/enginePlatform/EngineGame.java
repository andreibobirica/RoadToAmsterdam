package com.paper.bob.rta.roadtoamsterdam.enginePlatform;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Background;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Base;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.GameObject;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Ostacolo;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Person.Notify;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Person.Personaggio;
import com.paper.bob.rta.roadtoamsterdam.enginePlatform.Objects.Person.Player;
import com.paper.bob.rta.roadtoamsterdam.gameUtils.Sound;
import com.paper.bob.rta.roadtoamsterdam.gameUtils.SoundBG;

import java.io.Serializable;
import java.util.ArrayList;

public class EngineGame extends SurfaceView implements SurfaceHolder.Callback,Serializable {

    //Proprità
    private MainThread gameLoop;
    private ArrayList<Ostacolo> ostacoli;
    private ArrayList<Personaggio> personaggi;
    private Background bg;
    private Base base;
    private Player pl;
    private ArrayList<GameObject> objColl;
    private Controller control;
    private ArrayList<Sound> sounds;
    private SoundBG sbg;
    public static int WIDTH;
    public static int HEIGHT;
    //Propietà che indica la visualizzazione
    private boolean viewIsRunning = false;
    private String levelName;

    private static final int INVALID_POINTER_ID = -1;
    private float mScaleFactor = 1.f;
    private ScaleGestureDetector mScaleDetector;
    private float mLastTouchX;
    private float mLastTouchY;
    private int mActivePointerId;
    private Context context;
    private int dx = 0;
    private int dy = 0;


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

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        startView();
    }
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {}
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        stopView();
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
        //Controlle
        control.update();
        //moveElemntByTouch
        moveObjectsWithTouch(dx,dy);
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
        //DEBUGMODE
        if(Controller.debugMode)
        {
            String info = "DebugMode-> FPS: "+MainThread.FPS+" - AverageFPS: "+MainThread.averageFPS+" - dx:"+control.getDX()+" - dy: "+control.getDY()+" - dDown: "+control.getDDown();
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
     * Questo metodo potrebbe essere richiamato sia dentro questa classe nel SurfaceCreated() che nell'activity PlatformMainActivity
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
        if (!viewIsRunning && pl == null) {
            viewIsRunning=true;//Setto la View ora visibile
            /**
             * Inizio operazioni della VIEW
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
            //Suoni
            sounds = lvComposer.getSounds();
            sbg = lvComposer.getSoundBG();
            control.setSoundBG(sbg);
            //Player
            pl = lvComposer.getPlayer();
            //MOTION WITH TOUCH
            mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
            //Configurazione del Livello
            //Player & Movement Player
            bg.setPlayer(pl);
            //Coordinate a tutti dal Background
            Ostacolo.setBgCoord(bg);
            Notify.setBgCoord(bg);
            Base.setBgCoord(bg);
            //Gestione Collisioni
            for (Ostacolo o : ostacoli) {if (o.getFisico()) {objColl.add(o);}}
            for (Personaggio p : personaggi) {if (p.getFisico()) {objColl.add(p);}}
            objColl.add(base);
            Log.i("RTA", String.valueOf("\tTot:"+objColl.size()));
            //Controller//Collision per il Player//Movimento per altri
            pl.setController(control);
            bg.setController(control);
            control.setPlayer(pl);
            control.setObjColl(objColl);
            //Setto al controller i suoni
            control.setSounds(sounds);

            //INIZIO GAME
            //THREAD Game
            gameLoop = new MainThread(getHolder(), this);
            gameLoop.setRunning(true);
            gameLoop.start();
        }else if(!viewIsRunning && pl!=null)
        {
            viewIsRunning=true;//Setto la View ora visibile
            //SUONI
            sbg.play();
            //THREAD Game
            gameLoop = new MainThread(getHolder(), this);
            gameLoop.setRunning(true);
            gameLoop.start();
        }
    }
    /**
     * Metodo stopView() che stoppa la visualizzazione della SurfaceView sullo schermo. in poche parole non lo fa più funzionare.
     * Non elimina nessuna istanza della classe, e neppure nessun campo, quelli vengono salvati , stoppa solamente il Thread di gioco che gestisce gli FPS.
     * In base al fermo del Thread di gioco che gestisce gli FPS non appparirà nulla sulla schermo, e i rispettivi metodi update() e draw()
     * NON verranno più richiamati, rendendo il gioco bloccato (IN PAUSA).
     */
    public void stopView()
    {
        if (viewIsRunning) {
            viewIsRunning=false;//Setto la View ora NON visibile
            /**
             * Fine operazioni della VIEW
             */
            boolean retry = true;
            int counter = 0;
            while (retry && counter < 1000) {
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
        }
    }


    /**
     * Metodo setController() che serve a settare il controller, il campo
     * @param c istanza dell'oggetto Controller
     */
    public void setController(Controller c) {
        control = c;
    }

    public ArrayList<Sound> getSounds() {
        return sounds;
    }

    public SoundBG getSoundBG() {
        return sbg;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public void setContext(Context c){this.context = c;}

    //***************************************
//*************  TOUCH  *****************
//***************************************
    @Override
    public synchronized boolean onTouchEvent(MotionEvent ev) {

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

            System.out.println("MOVE");
            final int pointerIndex = ev.findPointerIndex(mActivePointerId);
            final float x = ev.getX(pointerIndex);
            final float y = ev.getY(pointerIndex);

            // Only move if the ScaleGestureDetector isn't processing a gesture.
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
            //mActivePointerId = INVALID_POINTER_ID;
        }

        if (action == MotionEvent.ACTION_CANCEL) {
            System.out.println("CANCEL");
            this.dx = 0;
            this.dy = 0;
           // mActivePointerId = INVALID_POINTER_ID;
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

    private void moveObjectsWithTouch(int dx, int dy) {
        if(bg.verifyMovementPlayer()) {
            if(pl.getY()+pl.getHeight()+dy>EngineGame.HEIGHT){
                dy = 0;Log.i("RTA","CHIAMATO");
            }
            if(base.getY()+dy<(EngineGame.HEIGHT)){
                dy = 0;Log.i("RTA","CHIAMATO2");
            }
            bg.setX(bg.getX() + dx);
            bg.setY(bg.getY() + dy);
            for (Ostacolo o : ostacoli) {
                o.setX(o.getX() + dx);
                o.setY(o.getY() + dy);
            }
            for (Personaggio o : personaggi) {
                o.setX(o.getX() + dx);
                o.setY(o.getY() + dy);
            }
            base.setX(base.getX() + dx);
            base.setY(base.getY() + dy);
            pl.setX(pl.getX() + dx);
            pl.setY(pl.getY() + dy);
        }
    }


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
}

