package com.runtai.zdytextlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * @作者：高炎鹏
 * @日期：2017/5/4时间11:08
 * @描述：
 */
public class ZDYTextView extends View {

    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;

    /**
     * 文本
     */
    private String mTitleText;
    /**
     * 文本的颜色
     */
    private int mTitleTextColor;
    /**
     * 文本的大小
     */
    private int mTitleTextSize;
    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;

    public ZDYTextView(Context context) {
        this(context, null);
    }

    public ZDYTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        paint = new Paint();
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ZDYTextView);
            mTitleText = a.getString(R.styleable.ZDYTextView_titleText);
            mTitleTextColor = a.getColor(R.styleable.ZDYTextView_title_textColor, DEFAULT_TEXT_COLOR);
            mTitleTextSize = a.getDimensionPixelSize(R.styleable.ZDYTextView_titleTextSize, (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
            a.recycle();
        }

        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mBound = new Rect();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mTitleTextColor);
        mPaint.setTextSize(mTitleTextSize);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = onMeasureR(0, widthMeasureSpec);
        int height = onMeasureR(1, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTitleTextColor);
//        float startX = getWidth() / 2 - mBound.width() / 2;//文字右侧没有预留位置(和原生TextView效果一样)
        float startX = getWidth() / 2 - mBound.width() / 2 - mBound.left;//文字稍微向左偏移一些(显示居中效果)
        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();
        int startY = getHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2;
        canvas.drawText(mTitleText, startX, startY, mPaint);

        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(4);
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mPaint);

        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setTextSize(35);
        canvas.drawText(content, rx, 60, paint);
        if (myThread == null) {
            myThread = new MyThread();
            myThread.start();
        }
    }

    /**
     * 计算控件宽高
     *
     * @param attr       属性[0宽,1高]
     * @param oldMeasure
     */
    public int onMeasureR(int attr, int oldMeasure) {

        int newSize = 0;
        int mode = MeasureSpec.getMode(oldMeasure);
        int oldSize = MeasureSpec.getSize(oldMeasure);

        switch (mode) {
            case MeasureSpec.EXACTLY:
                newSize = oldSize;
                break;
            case MeasureSpec.AT_MOST:
                float value;
                if (attr == 0) {
                    // value = mBound.width();
                    value = mPaint.measureText(mTitleText);
                    // 控件的宽度 + getPaddingLeft() + getPaddingRight()
                    newSize = (int) (getPaddingLeft() + value + getPaddingRight());
                } else if (attr == 1) {
                    // value = mBound.height();
                    Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
                    value = Math.abs((fontMetrics.bottom - fontMetrics.top));
                    // 控件的高度 + getPaddingTop() + getPaddingBottom()
                    newSize = (int) (getPaddingTop() + value + getPaddingBottom() + 1.5);
                }
                break;
        }
        return newSize;
    }


    private Paint paint;
    private float rx = 0;
    private String content = "hellowold";
    private MyThread myThread;

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                rx += 1;
                if (rx >= getWidth()) {
                    rx = 0 - paint.measureText(content);
                }
                postInvalidate();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
