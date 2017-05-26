package com.runtai.supertextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @作者：高炎鹏
 * @日期：2017/5/4时间11:08
 * @描述：
 */
public class ZDYView extends View {

    private int defalutSize;
    private Paint paint;

    public ZDYView(Context context) {
        super(context);
        paint = new Paint();
    }

    public ZDYView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            //第二个参数就是我们在styles.xml文件中的<declare-styleable>标签
            //即属性集合的标签，在R文件中名称为R.styleable+name
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ZDYView);

            //第一个参数为属性集合里面的属性，R文件名称：R.styleable+属性集合名称+下划线+属性名称
            //第二个参数为，如果没有设置这个属性，则设置的默认的值
            defalutSize = a.getDimensionPixelSize(R.styleable.ZDYView_default_size, 300);

            //最后记得将TypedArray对象回收
            a.recycle();
        }

        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMySize(defalutSize, widthMeasureSpec);
        int height = getMySize(defalutSize, heightMeasureSpec);
        if (width < height) {
            height = width;
        } else {
            width = height;
        }

        Log.e("width", "width" + width);
        Log.e("height", "height" + height);
        setMeasuredDimension(width, height);
    }

    private int getMySize(int defaultSize, int measureSpec) {

        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 调用父View的onDraw函数，因为View这个类帮我们实现了一些基本的绘制功能，比如绘制背景颜色、背景图片等
        super.onDraw(canvas);
        int r = getMeasuredHeight() / 2;//也可以是getMeasuredHeight()/2,本例中我们已经将宽高设置相等了
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        //开始绘制
        canvas.drawCircle(r, r, r, paint);
    }

}
