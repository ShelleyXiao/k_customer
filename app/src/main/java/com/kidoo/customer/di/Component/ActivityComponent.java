package com.kidoo.customer.di.Component;

import android.app.Activity;
import android.content.Context;

import com.kidoo.customer.di.module.ActivityModule;
import com.kidoo.customer.di.scope.ContextLife;
import com.kidoo.customer.di.scope.PerActivity;
import com.kidoo.customer.ui.activity.account.LoginActivity;

import dagger.Component;

/**
 * 工程名 ： BaiLan
 * 包名   ： com.example.ggxiaozhi.store.the_basket.di.component
 * 作者名 ： 志先生_
 * 日期   ： 2017/8/26
 * 时间   ： 12:09
 * 功能   ：
 */

@PerActivity
@Component(modules = ActivityModule.class , dependencies = AppComponent.class)
public interface ActivityComponent {

    @ContextLife("Application")
    Context getApplicationContext();

    @ContextLife("Activity")
    Context getActivityContext();

    Activity getActivity();

    void inject(LoginActivity activity);

//    void inject(CategorySubscribeActivity activity);
//    void inject(CategoryNecessaryActivity activity);
//    void inject(CategoryNewActivity activity);
//    void inject(CategorySubjectActivity activity);
//    void inject(AppMoreRecommendActivity activity);
//    void inject(AppDetailActivity activity);
//    void inject(CategoryToolActivity activity);

}
