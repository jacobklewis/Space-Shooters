package me.jacoblewis.spaceshooters.gengine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Apple on 9/25/15.
 */
public class ThreadController implements SurfaceHolder.Callback {

    private Callbacks cb;
    private DrawThread drawThread;
    private ComputeThread computeThread;
    private SurfaceHolder surfaceHolder;

    public ThreadController(Callbacks callbacks, SurfaceHolder surfaceHolder) {
        this.cb = callbacks;
        this.surfaceHolder = surfaceHolder;
        surfaceHolder.addCallback(this);
    }

    public void startThreads() {
        drawThread = new DrawThread(cb, surfaceHolder);
        drawThread.setRunning(true);
        drawThread.start();
        computeThread = new ComputeThread(cb, surfaceHolder);
        computeThread.setRunning(true);
        computeThread.start();
    }

    public void stopThreads() {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException ignored) {
            }
        }

        retry = true;
        computeThread.setRunning(false);
        while (retry) {
            try {
                computeThread.join();
                retry = false;
            } catch (InterruptedException ignored) {
            }
        }
    }

    public void setTargetFps(int fps) {
        drawThread.setTargetFps(fps);
        computeThread.setTargetFps(fps);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startThreads();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        cb.surfaceChanged(holder, format, width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopThreads();
    }

    public interface Callbacks {
        void drawNow(Canvas canvas, long fps);

        void computeNow(long fps);

        void surfaceChanged(SurfaceHolder holder, int format, int width, int height);
    }
}
