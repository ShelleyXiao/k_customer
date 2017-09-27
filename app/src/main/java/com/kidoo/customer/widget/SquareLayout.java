package com.kidoo.customer.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.kidoo.customer.R;

/**
 * User: ShaudXiao
 * Date: 2017-09-26
 * Time: 11:09
 * Company: zx
 * Description: 一个正方形容器
 * FIXME
 */


public class SquareLayout extends FrameLayout{

    private int mBaseDirection;

    public SquareLayout(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public SquareLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public SquareLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SquareLayout);
        mBaseDirection = ta.getInteger(R.styleable.SquareLayout_accordTo, 3);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mBaseDirection == 1) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        } else if (mBaseDirection == 2) {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
        } else {
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);

            if (heightSize == 0) {
                super.onMeasure(widthMeasureSpec, widthMeasureSpec);
                return;
            }

            if (widthSize == 0) {
                super.onMeasure(heightMeasureSpec, heightMeasureSpec);
                return;
            }

            if (widthSize > heightSize)
                super.onMeasure(heightMeasureSpec, heightMeasureSpec);
            else
                super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }
    }
}
