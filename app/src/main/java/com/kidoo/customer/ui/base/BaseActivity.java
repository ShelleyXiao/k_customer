package com.kidoo.customer.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * User: ShaudXiao
 * Date: 2017-09-12
 * Time: 19:11
 * Company: zx
 * Description:
 * FIXME
 */


public abstract class BaseActivity extends AppCompatActivity {

    private boolean mIsDestroy;
    private Fragment mFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(initBundle(getIntent().getExtras())) {
            setContentView(getContentView());

            initWindow();

            ButterKnife.bind(this);

            initWidget();
            initData();

        } else {
            finish();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mIsDestroy = true;
        super.onDestroy();
    }

    protected void addFragment(int frameLayoutId, Fragment fragment) {
        if (null != fragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(fragment.isAdded()) {
                if(null != mFragment) {
                    ft.hide(mFragment);
                } else {
                    ft.show(fragment);
                }
            } else {
                if(null != mFragment) {
                    ft.hide(mFragment).add(frameLayoutId, fragment);
                } else {
                    ft.add(frameLayoutId, fragment);
                }
            }
            mFragment = fragment;
            ft.commit();
        }
    }

    protected void replaceFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayoutId, fragment);
            transaction.commit();
        }
    }

    public boolean isDestroy() {
        return mIsDestroy;
    }

    protected abstract int getContentView();

    protected boolean initBundle(Bundle bundle) {
        return true;
    }

    protected void initWindow() {
    }

    protected void initWidget() {
    }

    protected void initData() {
    }


}
