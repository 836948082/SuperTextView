package com.runtai.supertextview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @作者：高炎鹏
 * @日期：2017/5/8时间09:18
 * @描述：
 */

public class MyViewGroup extends ViewGroup {

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 获取子View中宽度最大的值
     */
    private int getMaxChildWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            MyViewGroup.LayoutParams lp = (MyViewGroup.LayoutParams) childView.getLayoutParams();
            int left = lp.leftMargin;
            int right = lp.rightMargin;
            if (childView.getMeasuredWidth() + left + right > maxWidth)
                maxWidth = childView.getMeasuredWidth() + left + right;
        }
        return maxWidth;
    }

    /**
     * 将所有子View的高度相加
     */
    private int getTotleHeight() {
        int childCount = getChildCount();
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            MyViewGroup.LayoutParams lp = (MyViewGroup.LayoutParams) childView.getLayoutParams();
            int top = lp.topMargin;
            int bottom = lp.bottomMargin;
            height += childView.getMeasuredHeight() + top + bottom;
        }
        return height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //将所有的子View进行测量，这会触发每个子View的onMeasure函数
        //注意要与measureChild区分，measureChild是对单个view进行测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();

        if (childCount == 0) {//如果没有子View,当前ViewGroup没有存在的意义，不用占用空间
            setMeasuredDimension(0, 0);
        } else {
            //如果宽高都是包裹内容
            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
                //我们将高度设置为所有子View的高度相加，宽度设为子View中最大的宽度
                int height = getTotleHeight();
                int width = getMaxChildWidth();
                setMeasuredDimension(width, height);
            } else if (heightMode == MeasureSpec.AT_MOST) {//如果只有高度是包裹内容
                //宽度设置为ViewGroup自己的测量宽度，高度设置为所有子View的高度总和
                setMeasuredDimension(widthSize, getTotleHeight());
            } else if (widthMode == MeasureSpec.AT_MOST) {//如果只有宽度是包裹内容
                //宽度设置为子View中宽度最大的值，高度设置为ViewGroup自己的测量值
                setMeasuredDimension(getMaxChildWidth(), heightSize);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int curHeight = 0;
        int bottom;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            MyViewGroup.LayoutParams lp = (MyViewGroup.LayoutParams) child.getLayoutParams();
            int top = lp.topMargin;
            Log.e("top", "top" + top);
            bottom = lp.bottomMargin;
            Log.e("bottom", "bottom" + bottom);

            curHeight += top;

            int left = lp.leftMargin;
            Log.e("left", "left" + left);
            /*child.layout()位置信息是相对于group的值*/
            child.layout(l + left, curHeight, l + left + width, curHeight + height);
            curHeight += height + bottom;
        }
    }


    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyViewGroup.LayoutParams(getContext(), attrs);
    }

}
