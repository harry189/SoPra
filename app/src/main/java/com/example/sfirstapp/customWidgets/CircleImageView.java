package com.example.sfirstapp.customWidgets;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Moritz on 23.10.2016.
 */

public class CircleImageView extends ImageView {
    public static final String LOG_TAG = "CircleImageView";
    private float centerX, centerY, touchX, touchY, radius;
    public CircleImageView(Context context) {
        super(context);
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isMotionEventInCircle(event)) {
                Log.d(LOG_TAG, "Inside Circle");
                return false;
            } else {
                Log.d(LOG_TAG, "Outside Circle");
                return true;
            }
            }
        });
    }
    public boolean isMotionEventInCircle (MotionEvent event) {
        centerX = this.getWidth() / 2;
        centerY = this.getHeight() / 2;
        touchX = event.getX();
        touchY = event.getY();
        if (Math.pow(touchX - centerX, 2)
                + Math.pow(touchY - centerY, 2) < Math.pow(centerX, 2)) {
            return true;
        } else {
            return false;
        }
    }

    public void onCircleTouch() {

    }


}


