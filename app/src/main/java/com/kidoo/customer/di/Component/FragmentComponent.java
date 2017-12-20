package com.kidoo.customer.di.Component;

import android.app.Activity;
import android.content.Context;

import com.kidoo.customer.di.module.FragmentModule;
import com.kidoo.customer.di.scope.ContextLife;
import com.kidoo.customer.di.scope.PerFragment;
import com.kidoo.customer.ui.fragment.HomeFragment;
import com.kidoo.customer.ui.fragment.UserInfoTabFragment;
import com.kidoo.customer.ui.fragment.channelCampaign.CampaignBaseInfoFragment;

import dagger.Component;

/**
 * Created by Shelley on 2017/11/18.
 */

@PerFragment
@Component(modules = {FragmentModule.class}, dependencies = AppComponent.class)
public interface FragmentComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();


    void inject(UserInfoTabFragment fragment);

//    void inject(BroadcastTabFragment fragment);
    void inject(HomeFragment fragment);

    void inject(CampaignBaseInfoFragment fragment);

}
