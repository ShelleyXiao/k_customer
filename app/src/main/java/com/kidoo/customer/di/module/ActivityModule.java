package com.kidoo.customer.di.module;

import android.app.Activity;
import android.content.Context;

import com.kidoo.customer.di.scope.ContextLife;
import com.kidoo.customer.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/** 
 * description:
 * autour: ShaudXiao
 * date: 2017/11/18  
 * update: 2017/11/18
 * version: 
*/

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity aty) {
        this.mActivity = aty;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context provideContext() {
        return mActivity;
    }
}
