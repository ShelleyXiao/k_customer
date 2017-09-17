package com.kidoo.customer.ui.fragment;

import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;

import com.kidoo.customer.R;
import com.kidoo.customer.ui.base.BaseFragment;
import com.kidoo.customer.widget.BorderShaper;
import com.kidoo.customer.widget.NavButtomButton;

import butterknife.Bind;

/**
 * description: 底部导航栏主类
 * autour: ShaudXiao
 * date: 2017/9/17
 * update: 2017/9/17
 * version:
 */
public class NavigationFragement extends BaseFragment {

    @Bind(R.id.nav_item_broadcast)
    NavButtomButton mBroadcast;
    @Bind(R.id.nav_item_message)
    NavButtomButton mMessage;
    @Bind(R.id.nav_item_explore)
    NavButtomButton mNavExplore;
    @Bind(R.id.nav_item_me)
    NavButtomButton mNavMe;

    public NavigationFragement() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_main_nav;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        ShapeDrawable lineDrawable = new ShapeDrawable(new BorderShaper(new RectF(0, 1, 0, 0)));
        lineDrawable.getPaint().setColor(getResources().getColor(R.color.list_divider_color));
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{
                new ColorDrawable(getResources().getColor(R.color.white)),
                lineDrawable
        });
        root.setBackgroundDrawable(layerDrawable);
    }

    public interface OnNavigationReselectListener {
        void onReselect(NavButtomButton navigationButton);
    }
}
