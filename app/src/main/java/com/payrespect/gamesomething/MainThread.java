package com.payrespect.gamesomething;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread{
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;
    public int VIEW_WIDTH;
    public int VIEW_HEIGHT;
    public MainThread(SurfaceHolder surfaceHolder, GameView gameView){
        super();
        this.surfaceHolder=surfaceHolder;
        this.gameView=gameView;
        this.VIEW_HEIGHT=0;
        this.VIEW_WIDTH=0;
    }

    @Override
    public void run(){
        long now=0,dt;
        long last = System.currentTimeMillis();
        while(running){
            canvas=null;
            try {
                //TODO deltatime check
                canvas = this.surfaceHolder.lockCanvas();
                this.VIEW_WIDTH = canvas.getWidth();
                this.VIEW_HEIGHT = canvas.getHeight();
                synchronized (surfaceHolder) {
                    this.gameView.update(this.VIEW_HEIGHT, this.VIEW_WIDTH);
                    this.gameView.draw(canvas);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            finally{
                if(canvas!=null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setRunning(boolean d){
        running=d;
    }
}
