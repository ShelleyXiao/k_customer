package com.kidoo.customer.di.Component;

import android.app.Activity;
import android.content.Context;

import com.kidoo.customer.di.module.ActivityModule;
import com.kidoo.customer.di.scope.ContextLife;
import com.kidoo.customer.di.scope.PerActivity;
import com.kidoo.customer.ui.activity.AreanMapActivity;
import com.kidoo.customer.ui.activity.WondefulEventNewsActivity;
import com.kidoo.customer.ui.activity.account.LoginActivity;
import com.kidoo.customer.ui.activity.account.SigninInOneStepActivity;
import com.kidoo.customer.ui.activity.channelCampaign.CampaignDetailActivity;
import com.kidoo.customer.ui.activity.channelCampaign.ChannelCampaignListActivtiy;
import com.kidoo.customer.ui.activity.channelCampaign.CompetionEnrollSituationActivity;
import com.kidoo.customer.ui.activity.splash.SplashActivity;
import com.kidoo.customer.ui.activity.team.QueryTeamActivity;
import com.kidoo.customer.ui.activity.team.TeamDetailActivity;
import com.kidoo.customer.ui.activity.team.TeamDetailModifyActivity;
import com.kidoo.customer.ui.activity.user.UserDetailActivity;

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
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {

    @ContextLife("Application")
    Context getApplicationContext();

    @ContextLife("Activity")
    Context getActivityContext();

    Activity getActivity();

    void inject(LoginActivity activity);

    void inject(SigninInOneStepActivity activity);

    void inject(SplashActivity activity);

    void inject(UserDetailActivity activity);

    void inject(AreanMapActivity activity);

    void inject(WondefulEventNewsActivity activity);

    void inject(ChannelCampaignListActivtiy activity);

    void inject(CampaignDetailActivity activity);

    void inject(QueryTeamActivity activity);

    void inject(TeamDetailActivity activity);

    void inject(TeamDetailModifyActivity activity);

    void inject(CompetionEnrollSituationActivity activity);


}
