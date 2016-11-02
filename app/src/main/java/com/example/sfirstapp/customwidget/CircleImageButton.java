package com.example.sfirstapp.customwidget;

/**
 * Created by Moritz on 28.10.2016.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import org.w3c.dom.Attr;

/**
 * Created by Moritz on 27.10.2016.
 */

public class CircleImageButton extends AppCompatImageButton {
    private int width, centerX, height, centerY;
    private float  startX, startY;
    private static final float CLICK_ACTION_THRESHHOLD = 5;

    private static boolean isAClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);

        if (differenceX > CircleImageButton.CLICK_ACTION_THRESHHOLD ||
                differenceY > CircleImageButton.CLICK_ACTION_THRESHHOLD) {
            return false;
        }
        return true;
    }

    public CircleImageButton(Context context) {
        super(context);
    }

    public CircleImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int radius = centerX;
        if (Math.pow(event.getX() - centerX, 2) + Math.pow(event.getY() - centerY, 2) < Math.pow(radius, 2)) {
            Log.w("bla", "check click");
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = event.getX();
                    startY = event.getY();
                    return true;

                case MotionEvent.ACTION_UP: {
                    float endX = event.getX();
                    float endY = event.getY();
                    if (isAClick(startX, endX, startY, endY)) {
                        Log.w("bla", "click in circle");
                        performClick();
                    }
                    return true;
                }
                default:
                    return super.onTouchEvent(event);
            }
        }
        return false;
    }

    public CircleImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        centerX = width / 2;
        centerY = width / 2;
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
