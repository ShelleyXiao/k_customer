package com.kidoo.customer;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.kidoo.customer.widget.glideimageview.progress.ProgressManager;

import java.io.InputStream;

/**
 * User: ShaudXiao
 * Date: 2017-09-27
 * Time: 14:05
 * Company: zx
 * Description:
 * FIXME
 */

@GlideModule
public class KidooAppGlideModule extends AppGlideModule {
    /**
     *  通过GlideBuilder设置默认的结构(Engine,BitmapPool ,ArrayPool,MemoryCache等等).
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        builder.setMemoryCache(new LruResourceCache(20*1024*1024));

    }

//    /**
//     *
//     *
//     * @param context
//     * @param registry
//     */
//    @Override
//    public void registerComponents(Context context, Registry registry) {
//
//
//    }

    /**
     * 清单解析的开启
     *
     * 这里不开启，避免添加相同的modules两次
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
    }
}
