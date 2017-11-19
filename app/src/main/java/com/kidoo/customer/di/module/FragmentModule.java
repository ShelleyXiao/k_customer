package com.kidoo.customer.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.kidoo.customer.di.scope.ContextLife;
import com.kidoo.customer.di.scope.PerFragment;

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
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    @Provides
    @PerFragment
    public Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    @PerFragment
    public Activity provideFragmentActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    @ContextLife("Activity")
    public Context provideFragmentContext() {
        return mFragment.getActivity();
    }
}
