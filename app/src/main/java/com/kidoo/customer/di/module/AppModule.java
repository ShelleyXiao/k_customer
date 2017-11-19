package com.kidoo.customer.di.module;

import android.content.Context;

import com.kidoo.customer.AppContext;
import com.kidoo.customer.di.scope.ContextLife;
import com.kidoo.customer.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;

/** 
 * description:全局的Module
 * autour: ShaudXiao
 * date: 2017/11/18  
 * update: 2017/11/18
 * version: 
*/


@Module
public class AppModule {

    private AppContext mApplication;

    public AppModule(AppContext app) {
        this.mApplication = app;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideContext() {
        return mApplication.getApplicationContext();
    }

}
