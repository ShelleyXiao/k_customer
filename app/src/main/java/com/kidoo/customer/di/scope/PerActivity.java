package com.kidoo.customer.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Shelley on 2017/11/18.
 */

@Scope
@Documented
@Retention(RUNTIME)
public @interface PerActivity {
}
