package com.kidoo.customer.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** 
 * description:
 * autour: ShaudXiao
 * date: 2017/11/19  
 * update: 2017/11/19
 * version: 
*/

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ContextLife {
    /** The name. */
    String value() default "";
}
