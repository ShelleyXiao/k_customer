package com.kidoo.customer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kidoo.customer.R;

import java.math.BigDecimal;

/**
 * User: ShaudXiao
 * Date: 2017-09-22
 * Time: 10:01
 * Company: zx
 * Description: 星星評價控件
 * FIXME
 */


public class StarRatingView extends LinearLayout{

    private boolean mClickable;
    private boolean halfstart;
    private int starCount;
    private int starNum;
    private OnRatingChangeListener onRatingChangeListener;
    private float starImageSize;
    private float starImageWidth;
    private float starImageHeight;
    private float starImagePadding;
    private Drawable starEmptyDrawable;
    private Drawable starFillDrawable;
    private Drawable starHalfDrawable;

    private int y = 1;
    private boolean isEmpty=true;

    public StarRatingView(Context context) {
        super(context);
    }

    public StarRatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StarRatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StarRatingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    public void setClickable(boolean clickable) {
        mClickable = clickable;
    }

    public void setHalfstart(boolean halfstart) {
        this.halfstart = halfstart;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    public void setOnRatingChangeListener(OnRatingChangeListener onRatingChangeListener) {
        this.onRatingChangeListener = onRatingChangeListener;
    }

    public void setStarImageSize(float starImageSize) {
        this.starImageSize = starImageSize;
    }

    public void setStarImageWidth(float starImageWidth) {
        this.starImageWidth = starImageWidth;
    }

    public void setStarImageHeight(float starImageHeight) {
        this.starImageHeight = starImageHeight;
    }

    public void setStarImagePadding(float starImagePadding) {
        this.starImagePadding = starImagePadding;
    }

    public void setStarEmptyDrawable(Drawable starEmptyDrawable) {
        this.starEmptyDrawable = starEmptyDrawable;
    }

    public void setStarFillDrawable(Drawable starFillDrawable) {
        this.starFillDrawable = starFillDrawable;
    }

    public void setStarHalfDrawable(Drawable starHalfDrawable) {
        this.starHalfDrawable = starHalfDrawable;
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.HORIZONTAL);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StarRatingView);

        starHalfDrawable = ta.getDrawable(R.styleable.StarRatingView_starHalf);
        starEmptyDrawable = ta.getDrawable(R.styleable.StarRatingView_starEmpty);
        starFillDrawable = ta.getDrawable(R.styleable.StarRatingView_starFill);
        starImageSize = ta.getDimension(R.styleable.StarRatingView_starImageSize, 120);
        starImageWidth = ta.getDimension(R.styleable.StarRatingView_starImageWidth, 0);
        starImageHeight = ta.getDimension(R.styleable.StarRatingView_starImageHeight, 0);
        starImagePadding = ta.getDimension(R.styleable.StarRatingView_starImagePading, 15);
        starCount = ta.getInteger(R.styleable.StarRatingView_starCount, 5);
        starNum = ta.getInteger(R.styleable.StarRatingView_starNum, 0);
        mClickable = ta.getBoolean(R.styleable.StarRatingView_clickable, true);
        halfstart = ta.getBoolean(R.styleable.StarRatingView_harlfstart, false);

        ta.recycle();

        for(int i = 0; i < starNum; i++) {
            ImageView view = getImageView(context, false);
            addView(view);
        }

        for(int i = 0 ; i < starCount; i++) {
            ImageView imageView = getImageView(context,isEmpty);
            imageView.setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mClickable) {
                                if (halfstart) {
                                    //TODO:This is not the best way to solve half a star,
                                    //TODO:but That's what I can do,Please let me know if you have a better solution
                                    if (y % 2 == 0) {
                                        setStar(indexOfChild(v) + 1f);
                                    } else {
                                        setStar(indexOfChild(v) + 0.5f);
                                    }
                                    if (onRatingChangeListener != null) {
                                        if (y % 2 == 0) {
                                            onRatingChangeListener.onRatingChange(indexOfChild(v) + 1f);
                                            y++;
                                        } else {
                                            onRatingChangeListener.onRatingChange(indexOfChild(v) + 0.5f);
                                            y++;
                                        }
                                    }
                                } else {
                                    setStar(indexOfChild(v) + 1f);
                                    if (onRatingChangeListener != null) {
                                        onRatingChangeListener.onRatingChange(indexOfChild(v) + 1f);
                                    }
                                }

                            }

                        }
                    }
            );
            addView(imageView);
        }

    }

    private ImageView getImageView(Context context, boolean isEmpty) {
        ImageView imageView = new ImageView(context);

//        ViewGroup.LayoutParams para = new ViewGroup.LayoutParams(
//                Math.round(starImageWidth),
//                Math.round(starImageHeight)
//        );

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                starImageWidth == 0 ? LayoutParams.WRAP_CONTENT :  Math.round(starImageWidth),
                starImageHeight == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : Math.round(starImageHeight)
        );

        imageView.setLayoutParams(lp);
        imageView.setPadding(0, 0, Math.round(starImagePadding), 0);
        if(isEmpty) {
            imageView.setImageDrawable(starEmptyDrawable);
        } else {
            imageView.setImageDrawable(starFillDrawable);
        }
        return imageView;
    }

    public void setStar(float starCount) {
        int fint = (int) starCount;
        BigDecimal b1 = new BigDecimal(Float.toString(starCount));
        BigDecimal b2 = new BigDecimal(Integer.toString(fint));
        float fPoint = b1.subtract(b2).floatValue();


        starCount = fint > this.starCount ? this.starCount : fint;
        starCount = starCount < 0 ? 0 : starCount;

        //drawfullstar
        for (int i = 0; i < starCount; ++i) {
            ((ImageView) getChildAt(i)).setImageDrawable(starFillDrawable);
        }

        //drawhalfstar
        if (fPoint > 0) {
            ((ImageView) getChildAt(fint)).setImageDrawable(starHalfDrawable);

            //drawemptystar
            for (int i = this.starCount - 1; i >= starCount + 1; --i) {
                ((ImageView) getChildAt(i)).setImageDrawable(starEmptyDrawable);
            }

        } else {
            //drawemptystar
            for (int i = this.starCount - 1; i >= starCount; --i) {
                ((ImageView) getChildAt(i)).setImageDrawable(starEmptyDrawable);
            }

        }
    }
    /**
     * change listener
     */
    public interface OnRatingChangeListener {

        void onRatingChange(float RatingCount);

    }
}
