package com.kidoo.customer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kidoo.customer.R;
import com.kidoo.customer.utils.TDevice;



public class MyEnterLayout extends LinearLayout {
    private TextView setItemTitle;
    private ImageView devider_line;
    private RelativeLayout item_layout;
    private ImageView ivIcon;

    public MyEnterLayout(Context paramContext) {
        super(paramContext);
    }

    public MyEnterLayout(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        a(paramContext, paramAttributeSet);
    }

    private void a(Context context, AttributeSet attrs) {


        setOrientation(LinearLayout.VERTICAL);
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.explore__normal_item, this);
        item_layout = ((RelativeLayout) view.findViewById(R.id.item_layout));
        //设置左右padding16像素
//        setPadding(DensityUtil.getDensity(context, 16), DensityUtil.getDensity(context, 0));
        setPadding((int)TDevice.dipToPx(context.getResources(), 16f), (int)TDevice.dipToPx(context.getResources(),16f));
        setItemTitle = ((TextView) item_layout.findViewById(R.id.itemTitle));
        devider_line = ((ImageView) view.findViewById(R.id.devider_line));
        ivIcon = (ImageView) view.findViewById(R.id.itemIcon);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyEnterLayout);
        if(ta != null) {
            String titel = ta.getString(R.styleable.MyEnterLayout_itemTitle);
            Drawable iconRes = ta.getDrawable(R.styleable.MyEnterLayout_itemIcon);

            setItemTitle.setText(titel);
            ivIcon.setImageDrawable(iconRes);
        }

        ta.recycle();
    }

    public void a() {
        devider_line.setVisibility(View.VISIBLE);
    }

    public void setPadding(int paramInt1, int paramInt2) {
        item_layout.setPadding(paramInt1, 0, paramInt2, 0);
    }



    public void setTitle(String title) {
        if(!TextUtils.isEmpty(title))
            setItemTitle.setText(title);
    }
}