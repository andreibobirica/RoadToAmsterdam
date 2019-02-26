package com.paper.bob.rta.roadtoamsterdam.engineGame.enginePlatform;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread
{
    /**Media di FPS in ogni momento*/
    public static double averageFPS;
    /**Superficie su cui disegnare il canvas, la tela*/
    private SurfaceHolder surfaceHolder;
    /**Canvas su cui sisegnare tutti gli ementi del gioco*/
    private static Canvas canvas;
    /**Varibili del motore di gioco*/
    private EngineGame engGame;
    private boolean running;


    // desired fps
    private final static int MAX_FPS = 60;
    // maximum number of frames to be skipped
    private final static int MAX_FRAME_SKIPS = 5;
    // the frame period
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;


    /**
     * Costruttore del MainThred che definisce i campo e cosa il Thred dovrà regolare in tempistiche
     * @param surfaceHolder La view su cui il Thred fa appoggio
     * @param engGame il EngineGame che il Thred dovrà regolare in tempistiche e ottimizzazione.
     */
    public GameThread(SurfaceHolder surfaceHolder, EngineGame engGame)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.engGame = engGame;
        Log.i("RTA", "Costruttore GameThread");

    }

    /**
     * Metodo run che definisce il Thred di gioco, tramite un algoritmo e un sistema di Wait & Do si riesce a definire
     * Un sistema di FPS Engine Game, per il quale ogni azione si riesce ad eseguirla ad una cerca distanza dal tempo.
     * Il metodo run() non ha valori da parametro e nessun valore di ritorno.
     * Il suo unico scopo è definire con un ciclo continuo infinito le azioni e scandirle per tempo.
     * In particola ad intervali regolari richiama i metodi:
     * EngineGame.update()  & EngineGame.draw() che successivamente faranno in modo che il gioco funzioni su un Engine Game.
     */
    @Override
    public void run()
    {
        Log.i("RTA", "Inizio RUN");
        long beginTime; // the time when the cycle begun
        long timeDiff; // the time it took for the cycle to execute
        int sleepTime; // ms to sleep (<0 if we're behind)
        int framesSkipped; // number of frames being skipped
        while(running) {
            //startTime = System.nanoTime();
            canvas = null;
            //Editing Del Canvas in modo da poter realizzare una imagine dinamica
            try {
                Log.i("RTAloop", "try lookCanvas");
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    Log.i("RTAloop", "Syncronized update and draw");

                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0; // resetting the frames skipped
                    // update game state
                    this.engGame.update();
                    // render state to the screen
                    // draws the canvas on the panel
                    this.engGame.draw(canvas);
                    // calculate how long did the cycle take
                    timeDiff = System.currentTimeMillis() - beginTime;
                    // calculate sleep time
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);
                    if (sleepTime > 0) {
                        // if sleepTime > 0 we're OK
                        try {
                            // send the thread to sleep for a short period
                            // very useful for battery saving
                            //Log.i("RTA", "-----------scleep");
                            Log.i("RTAloop", "Sleep");
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {Log.i("RTA", "Exception look canvas"+e);
                        }
                    }
                    averageFPS = MAX_FPS;
                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        // we need to catch up
                        // update without rendering
                        //Log.i("RTA", "sckippp frame ancd update");

                        this.engGame.update();
                        // add frame period to check if in next frame
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                        averageFPS = MAX_FPS -framesSkipped;
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        Log.i("RTAloop", "UnlockCanvasand post");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Metodo che fa partire il Thred.
     */
    public void setRunning(boolean b)
    {
        running=b;
    }

    public static double getAverangeFPS()
    {return averageFPS;}

    public static int getMAX_FPS(){return MAX_FPS;}
}