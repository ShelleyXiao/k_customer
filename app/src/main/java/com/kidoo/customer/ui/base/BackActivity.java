package com.kidoo.customer.ui.base;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.kidoo.customer.R;

/**
 * User: ShaudXiao
 * Date: 2017-09-21
 * Time: 18:38
 * Company: zx
 * Description: 带返回 button 的Toolbar activity 基础类
 * FIXME
 */


public abstract class BackActivity extends BaseActivity {

    protected Toolbar mToolBar;


    @Override
    protected void initWindow() {
        super.initWindow();
        mToolBar = (Toolbar) findViewById(R.id.id_toolbar);
        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(false);
            }
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    protected void initToolbar() {
        mToolBar = (Toolbar) findViewById(R.id.id_toolbar);
        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(false);
            }
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            TextView title = (TextView) mToolBar.findViewById(R.id.toolbar_title);
            title.setText(getToolbarTitle());
        }
    }

    public abstract String getToolbarTitle();



}
