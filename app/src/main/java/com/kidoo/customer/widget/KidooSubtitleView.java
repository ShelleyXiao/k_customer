package com.kidoo.customer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kidoo.customer.R;

/**
 * description: 小标题view
 * autour: ShaudXiao
 * date: 2017/12/10
 * update: 2017/12/10
 * version:
 */
public class KidooSubtitleView extends FrameLayout {

    private TextView tvSubtitle;

    public KidooSubtitleView(@NonNull Context context) {
        super(context);
    }

    public KidooSubtitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public KidooSubtitleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.layout_subtitle, this);

        tvSubtitle = (TextView) view.findViewById(R.id.subtitle);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.KidooSubtitleView);
        if (ta != null) {
            String titel = ta.getString(R.styleable.KidooSubtitleView_subtitle);
            tvSubtitle.setText(titel);
        }

        ta.recycle();
    }

    public void setTitle(String title) {
        tvSubtitle.setText(title);
    }
}
