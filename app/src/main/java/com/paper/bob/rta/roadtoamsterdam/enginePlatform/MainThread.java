package com.paper.bob.rta.roadtoamsterdam.enginePlatform;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread
{
    //Campo che definisce il numero di FPS
    public static final int FPS = 60;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private EngineGame engGame;
    private boolean running;
    private static Canvas canvas;

    /**
     * Costruttore del MainThred che definisce i campo e cosa il Thred dovrà regolare in tempistiche
     * @param surfaceHolder La view su cui il Thred fa appoggio
     * @param engGame il EngineGame che il Thred dovrà regolare in tempistiche e ottimizzazione.
     */
    public MainThread(SurfaceHolder surfaceHolder, EngineGame engGame)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.engGame = engGame;
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
        //Variabili Neccessarie per formare un loopGame e un andamento FRS statico
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;
            //Editing Del Canvas in modo da poter realizzare una imagine dinamica
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    //Update del Engine Game con il metodo update(); il quale viene richiamato ogni Frame
                    this.engGame.update();
                    this.engGame.draw(canvas);
                }
            }
            catch (Exception e) {System.err.println(e.getMessage());}
            finally{
                if(canvas!=null)
                {
                    try{surfaceHolder.unlockCanvasAndPost(canvas);}
                    catch(Exception e){e.printStackTrace();}
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;

            try{
                this.sleep(waitTime);
            }catch(Exception e){}

            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == FPS)
            {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount =0;
                totalTime = 0;
                //Log.i("FPS", String.valueOf(averageFPS));
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
    
}