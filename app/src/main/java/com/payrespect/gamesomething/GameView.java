package com.payrespect.gamesomething;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;
    private Paint paint;
    private int x,y,box_size,VIEW_WIDTH,VIEW_HEIGHT,dash;
    private float vel_x,vel_y;
    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);
        setFocusable(true);
        x=y= 0;
        vel_x=vel_y=10;
        box_size= 100;
        paint = new Paint();
        paint.setColor(Color.rgb(200,0,0));
        dash=0;
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread.setRunning(true);
        thread.start();
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
    public void check_boundary(){
        if(x>=VIEW_WIDTH-box_size){
            x=VIEW_WIDTH-box_size;
            vel_x=-vel_x;
        }
        if(y>=VIEW_HEIGHT-box_size){
            y=VIEW_HEIGHT-box_size;
            vel_y=-vel_y;
        }
        if(x<=0){
            x=0;
            vel_x=-vel_x;
        }
        if(y<=0){
            y=0;
            vel_y=-vel_y;
        }
    }
    public void update(int height, int width){
        VIEW_HEIGHT=height;
        VIEW_WIDTH=width;
        if(dash>0){
            x+=vel_x*3;
            y+=vel_y*3;
            dash--;
        }else {
            x += vel_x;
            y += vel_y;
        }
        check_boundary();
    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas!=null){
            canvas.drawColor(Color.WHITE);

            canvas.drawRect(x,y,box_size+x,box_size+y,paint);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action =event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            dash=100;
        }
        if(action==MotionEvent.ACTION_UP){
            dash=0;
        }
        return true;
    }
}
