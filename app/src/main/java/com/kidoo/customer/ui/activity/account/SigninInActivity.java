package com.kidoo.customer.ui.activity.account;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Menu;

import com.kidoo.customer.R;
import com.kidoo.customer.ui.base.BackActivity;

/**
 * User: ShaudXiao
 * Date: 2017-09-21
 * Time: 18:49
 * Company: zx
 * Description:
 * FIXME
 */


public class SigninInActivity extends BackActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_sign_ini;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.message_action_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public String getToolbarTitle() {
        return getString(R.string.sign_in_tilte);
    }

    public  Drawable setTintDrawable(Drawable drawable, @ColorInt int color) {
        drawable.clearColorFilter();
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        drawable.invalidateSelf();
        Drawable wrapDrawable = DrawableCompat.wrap(drawable).mutate();
        DrawableCompat.setTint(wrapDrawable, color);
        return wrapDrawable;
    }
}
