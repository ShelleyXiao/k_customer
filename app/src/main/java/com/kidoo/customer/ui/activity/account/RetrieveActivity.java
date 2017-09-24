package com.kidoo.customer.ui.activity.account;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.kidoo.customer.R;

/**
 * Created by Shelley on 2017/9/24.
 */

public class RetrieveActivity extends AccountBaseActivity {

    private Toolbar mToolBar;

    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    public void initWindow() {
        super.initWindow();
        initToolbar();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
    }

    private void initToolbar() {
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
            title.setText(getString(R.string.sign_in_tilte));
        }
    }

}
