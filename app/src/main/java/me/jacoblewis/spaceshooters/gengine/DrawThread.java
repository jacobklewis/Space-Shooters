package me.jacoblewis.spaceshooters.gengine;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by Apple on 8/15/15.
 */
public class DrawThread extends Thread {
    private long targetFPS = 60;
    private long currentFPS = 0;
    private ThreadController.Callbacks view;
    private boolean running = false;
    private final SurfaceHolder surfaceHolder;

    public DrawThread(ThreadController.Callbacks view, SurfaceHolder surfaceHolder) {
        this.view = view;
        this.surfaceHolder = surfaceHolder;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    public void setTargetFps(long fps) {
        this.targetFPS = fps;
    }

    @Override
    public void run() {
        long ticksPS;
        long startTime;
        long sleepTime;
        while (running) {
            ticksPS = 1000 / targetFPS;

            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                if (currentFPS == 0)
                    currentFPS = targetFPS;
                c = surfaceHolder.lockCanvas();
                if (c == null) {
                    Log.e("TAG", "canvas is null");
                    continue;
                }
                synchronized (surfaceHolder) {
                    view.drawNow(c, currentFPS);
                }
            } finally {
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);

            try {
                if (sleepTime > 0) {
                    currentFPS = targetFPS;
                    //Log.i("SLEEP","sleep for:"+sleepTime);
                    sleep(sleepTime);
                } else {
                    currentFPS = 1000 / Math.abs(sleepTime - ticksPS - 10);
                    //Log.i("SLEEP","sleep unknown: "+sleepTime);
                    sleep(10);
                }
            } catch (Exception ignored) {
            }
        }
    }
}
