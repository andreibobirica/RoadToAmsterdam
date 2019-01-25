package com.paper.bob.rta.roadtoamsterdam.engine.utils;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import com.paper.bob.rta.roadtoamsterdam.engine.EngineGame;

public class MainThread extends Thread
{
    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private EngineGame engGame;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, EngineGame engGame)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.engGame = engGame;
    }
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
                System.out.println(averageFPS);
            }
        }
    }
    public void setRunning(boolean b)
    {
        running=b;
    }
}