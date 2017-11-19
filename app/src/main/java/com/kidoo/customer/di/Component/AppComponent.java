package com.kidoo.customer.di.Component;

import android.content.Context;

import com.kidoo.customer.di.module.AppModule;
import com.kidoo.customer.di.scope.ContextLife;
import com.kidoo.customer.di.scope.PerApp;

import dagger.Component;

/**
 * Created by Shelley on 2017/11/18.
 */

@PerApp
@Component(modules = {AppModule.class})
public interface AppComponent {

    @ContextLife("Application")
    Context getApplicationContext();

}
