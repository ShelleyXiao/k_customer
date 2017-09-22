package com.kidoo.customer.ui.activity.account;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import com.kidoo.customer.R;

import butterknife.Bind;

/**
 * User: ShaudXiao
 * Date: 2017-09-21
 * Time: 18:30
 * Company: zx
 * Description:
 * FIXME
 */


public class LoginActivity extends AccountBaseActivity {

    @Bind(R.id.test)
    ImageView test;

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initWidget() {
        AnimationDrawable am = (AnimationDrawable) test.getBackground();
        am.start();

    }


}
