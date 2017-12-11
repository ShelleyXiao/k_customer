package com.kidoo.customer.ui.base.activities;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;

import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.utils.DialogHelper;

/**
 * User: ShaudXiao
 * Date: 2017-09-25
 * Time: 18:55
 * Company: zx
 * Description:
 * FIXME
 */


public abstract class BaseBackMvpActivity<T extends BasePresenter> extends BaseMvpActivity<T> {

    private ProgressDialog mWaitDialog;

    @Override
    protected void initWindow() {
        super.initWindow();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        finish();
    }

    protected void showLoadingDialog(String message) {
        if (mWaitDialog == null) {
            mWaitDialog = DialogHelper.getProgressDialog(this, true);
        }
        mWaitDialog.setMessage(message);
        mWaitDialog.show();
    }

    protected void dismissLoadingDialog() {
        if (mWaitDialog == null) return;
        mWaitDialog.dismiss();
    }
}
