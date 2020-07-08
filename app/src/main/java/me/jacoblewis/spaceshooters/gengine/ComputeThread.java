package me.jacoblewis.spaceshooters.gengine;

import android.view.SurfaceHolder;

/**
 * Created by Apple on 8/15/15.
 */
public class ComputeThread extends Thread {
    private long targetFPS = 60;
    private long currentFPS = 0;
    private ThreadController.Callbacks view;
    private boolean running = false;
    private final SurfaceHolder surfaceHolder;

    public ComputeThread(ThreadController.Callbacks view, SurfaceHolder surfaceHolder) {
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
            startTime = System.currentTimeMillis();
            try {
                if (currentFPS == 0)
                    currentFPS = targetFPS;

                synchronized (surfaceHolder) {
                    view.computeNow(currentFPS);
                }
            } catch (Exception ignored) {
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
