package com.runtai.supertextview.SuperTextView;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.runtai.supertextview.R;
import com.runtai.supertextviewlibrary.SuperTextView;

/**
 * Project Name:SuperTextView
 * Author:CoorChice
 * Date:2017/4/17
 * Notes:
 */

public class OpportunityDemoAdjuster extends SuperTextView.Adjuster {

    private float density;
    private Paint paint;

    public OpportunityDemoAdjuster() {
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void adjust(SuperTextView v, Canvas canvas) {
        int width = v.getWidth();
        int height = v.getHeight();
        if (density == 0) {
            density = v.getResources().getDisplayMetrics().density;
        }
        paint.setColor(v.getResources().getColor(R.color.colorPrimary));
        canvas.drawCircle(width / 2, height / 2, 30 * density, paint);
    }
}
