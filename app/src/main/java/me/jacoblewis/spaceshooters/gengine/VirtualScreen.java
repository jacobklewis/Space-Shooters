package me.jacoblewis.spaceshooters.gengine;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Apple on 8/15/15.
 */
public class VirtualScreen implements ThreadController.Callbacks {
    public static int FILL_ASPECT = 0, FILL_SCREEN = 1;

    private int width, height, displayMode;
    private float ratio = 1, defaultRatio = 0, zoomMin = 0, zoomMax = 100;
    private boolean initialized = false;
    private PointF offset = new PointF(0, 0);
    private SurfaceView surfaceView;
    public List<Renderable> renderables = new ArrayList<>();
    private OnFrameListener onFrameListener = null;


    public VirtualScreen(int width, int height, int displayMode, SurfaceView surfaceView) {
        this.width = width;
        this.height = height;
        this.displayMode = displayMode;
        this.surfaceView = surfaceView;
        new ThreadController(this, surfaceView.getHolder());
    }

    public void addRenderable(Renderable renderable) {
        renderables.add(renderable);
        if (initialized) {
            renderable.setVirtualScreen(this);
            Log.i("FIRE", "On Screen");
        }
    }

    public void removeRenderable(Renderable renderable) {
        renderables.remove(renderable);
    }

    public void setOnFrameListener(OnFrameListener onFrameListener) {
        this.onFrameListener = onFrameListener;
    }

    public void initScreen(int realWidth, int realHeight) {
        float aspectR = ((float) realWidth) / realHeight;
        float aspectV = ((float) width) / height;

        if (displayMode == FILL_ASPECT) {
            if (aspectR / aspectV > 1) {
                defaultRatio = ((float) realHeight) / height;
            } else {
                defaultRatio = ((float) realWidth) / width;
            }

        } else if (displayMode == FILL_SCREEN) {
            if (aspectR / aspectV < 1) {
                defaultRatio = ((float) realHeight) / height;
            } else {
                defaultRatio = ((float) realWidth) / width;
            }
        }
        // Apply the virtual screen to each renderable before rendering
        for (Renderable renderable : renderables) {
            renderable.setVirtualScreen(this);
        }
        initialized = true;
    }

    public void setZoomRange(float min, float max) {
        zoomMin = min;
        zoomMax = max;
    }

    public void setDefaultRatio() {
        ratio = 1;
    }

    public void offsetRatio(float dr) {
        ratio *= dr;
        ratio = ratio < zoomMin ? zoomMin : Math.min(ratio, zoomMax);
    }

    public void offsetPositionTo(float x, float y) {
        offset.x = x;
        offset.y = y;
    }

    public PointF offset() {
        return new PointF(offset.x, offset.y);
    }

    public float realX(float val) {
        if (defaultRatio != 0) {
            return offset.x + ratio * defaultRatio * val - (ratio - 1) * defaultRatio * centerX();
        } else {
            return val;
        }
    }

    public float realY(float val) {
        if (defaultRatio != 0) {
            return offset.y + ratio * defaultRatio * val - (ratio - 1) * defaultRatio * centerY();
        } else {
            return val;
        }
    }

    public float realScale(float val) {
        if (defaultRatio != 0) {
            return ratio * defaultRatio * val;
        } else {
            return val;
        }
    }

    public float width() {
        return width;
    }

    public float height() {
        return height;
    }

    public float centerX() {
        return width / 2f;
    }

    public float centerY() {
        return height / 2f;
    }

    @Override
    public void drawNow(Canvas canvas, long fps) {
        try {
            List<Renderable> rs = new ArrayList<>(renderables);
            if (!initialized && surfaceView.getWidth() > 0) {
                initScreen(surfaceView.getWidth(), surfaceView.getHeight());
            }
            for (Renderable renderable : rs) {
                renderable.drawNow(canvas, fps);
            }
        } catch (ConcurrentModificationException ex) {
            // ignore
        }
        if (onFrameListener != null) {
            onFrameListener.onFrame(fps);
        }
    }

    @Override
    public void computeNow(long fps) {
        for (Renderable renderable : renderables) {
            renderable.computeNow(fps);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        initScreen(width, height);
    }
}
