package com.kidoo.customer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.kidoo.customer.R;
import com.kidoo.customer.ui.activity.account.LoginActivity;
import com.kidoo.customer.utils.SharePrefUtil;

/**
 * User: ShaudXiao
 * Date: 2017-09-12
 * Time: 20:38
 * Company: zx
 * Description: 引导页
 * FIXME
 */


public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            redirectTo();
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = LayoutInflater.from(this).inflate(R.layout.start_activity, null);
        setContentView(v);

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
        v.startAnimation(am);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void redirectTo() {
        boolean firstEnter = SharePrefUtil.getBoolean(this, "first_enter", false);
        Intent intent = new Intent();
        if(!firstEnter) {
            SharePrefUtil.saveBoolean(this, "first_enter", true);
//            intent.setClass(this, GuideActivity.class);
        } else {

        }
//        if(AccountHelper.isLogin()) {
//            intent.setClass(this, MainActivity.class);
//        } else {
//            intent.setClass(this, LoginActivity.class);
//        }

        intent.setClass(this, LoginActivity.class);
        this.startActivity(intent);
        this.finish();
    }

}
