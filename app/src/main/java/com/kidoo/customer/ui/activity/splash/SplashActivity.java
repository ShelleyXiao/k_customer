package com.kidoo.customer.ui.activity.splash;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.R;
import com.kidoo.customer.mvp.contract.CheckAllTokenContract;
import com.kidoo.customer.mvp.presenter.account.CheckAllTokenPresenterImpl;
import com.kidoo.customer.ui.activity.MainActivity;
import com.kidoo.customer.ui.activity.user.UserCreateCompetionActivity;
import com.kidoo.customer.ui.base.activities.BaseMvpActivity;
import com.kidoo.customer.utils.SharePrefUtil;

import javax.inject.Inject;

/**
 * User: ShaudXiao
 * Date: 2017-09-12
 * Time: 20:38
 * Company: zx
 * Description: 引导页
 * FIXME
 */


public class SplashActivity extends BaseMvpActivity<CheckAllTokenPresenterImpl> implements CheckAllTokenContract.View{

    public static final String PREF_IS_USER_GUIDE_SHOWED = "is_user_guide_showed";

    @Inject
    public CheckAllTokenPresenterImpl mPresenter;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            redirectTo();
        }
    };


    @Override
    protected int getContentView() {
        return R.layout.start_activity;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        AlphaAnimation am = new AlphaAnimation(0.5f, 1.5f);
        am.setDuration(800);
        am.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                handler.sendEmptyMessage(-1);
            }
        });
        View v = findViewById(R.id.rootView);
        v.startAnimation(am);
    }


    @Override
    protected CheckAllTokenPresenterImpl initInjector() {
        mActivityComponent.inject(this);
        return mPresenter;
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void goMain() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    public void goLogin() {
        Intent intent = new Intent();
        intent.setClass(this, UserCreateCompetionActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    private void redirectTo() {
        boolean firstEnter = SharePrefUtil.getBoolean(this, PREF_IS_USER_GUIDE_SHOWED, false);
        Intent intent = new Intent();
        if(!firstEnter) {
            intent.setClass(this, GuideActivity.class);
            startActivity(intent);
            finish();
        } else {
            if(AccountHelper.isLogin()) {
                mPresenter.checkAllTokenAction();

            } else {
                intent.setClass(this, UserCreateCompetionActivity.class);
                this.startActivity(intent);
                this.finish();
            }
        }


    }

}
