package me.jacoblewis.spaceshooters.gengine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Apple on 8/15/15.
 */
public class Surfaces {
    private int mWidth, mHeight;
    private float posX = 0,posY = 0;
    private ArrayList<int[]> mPaths = new ArrayList<>();

    public Surfaces(int width, int height, int[] ...paths) {
        mWidth = width;
        mHeight = height;
        Collections.addAll(mPaths, paths);
    }

    public boolean hitTestSurface(int testX, int testY, float bias) {
        //TODO: setup bias
        int[] mPath = mPaths.get(0);
        testX += posX;
        testY += posY;

        int count = 0;
        for(int i=0;i<mPath.length-2;i+=2) {
            int ax = mPath[i], ay = mPath[i + 1], bx = mPath[i + 2], by = mPath[i + 3];
            if ((ax < testX && bx > testX || bx < testX && ax > testX) && (ay < testY || by < testY)) {
                if (ay<testY && by<testY)
                    count++;
                else if (((float)(by-ay))/(bx-ax) * (testX-ax)+ay <= testY)
                    count++;
            }else if(ax==testX && ay < testY) {
                if(bx!=testX || by >= testY)
                    count++;
            }

        }
        return count % 2 == 1; //returns true if inside shape
    }

    public void setPosition(float x, float y) {
        posX = x;
        posY = y;
    }

    public int getWidth() { return mWidth; }
    public int getHeight() { return mHeight; }

    public void drawSurfaces(Canvas canvas, VirtualScreen virtualScreen, Paint paint) {
        int[] mPath = mPaths.get(0);
        Path path = new Path();
        path.moveTo(virtualScreen.realX(mPath[0]-posX), virtualScreen.realY(mPath[1]-posY));
        for(int i = 2; i < mPath.length; i+=2) {
            path.lineTo(virtualScreen.realX(mPath[i]-posX), virtualScreen.realY(mPath[i+1]-posY));
        }
        path.toggleInverseFillType();


        canvas.drawPath(path,paint);
    }


}
