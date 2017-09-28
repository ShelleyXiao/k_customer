package com.kidoo.customer.media;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kidoo.customer.AppOperator;
import com.kidoo.customer.GlideRequest;
import com.kidoo.customer.R;
import com.kidoo.customer.ui.base.activities.BaseActivity;
import com.kidoo.customer.utils.rom.BitmapUtil;
import com.kidoo.customer.widget.loading.Loading;

import net.oschina.common.utils.StreamUtil;

import java.io.File;
import java.util.List;
import java.util.concurrent.Future;

import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * User: ShaudXiao
 * Date: 2017-09-28
 * Time: 17:11
 * Company: zx
 * Description: 图片预览
 * FIXME
 */


public class ImageGalleryActivity extends BaseActivity implements ViewPager.OnPageChangeListener
        , EasyPermissions.PermissionCallbacks {

    private static final int PERMISSION_ID = 0x0001;

    public static final String KEY_IMAGE = "images";
    public static final String KEY_COOKIE = "cookie_need";
    public static final String KEY_POSITION = "position";
    public static final String KEY_NEED_SAVE = "save";

    private PreviewViewpaper mImagePreviewViewpaper;
    private TextView mIndexText;
    private String[] mImageSource;
    private int mCurPosition;
    private boolean mNeedSaveLocal;
    private boolean[] mImageDownloadStatus;
    private boolean mNeedCookie;

    public static void show(Context context, String images) {
        show(context, images, true);
    }

    public static void show(Context context, String images, boolean needSaveLocal) {
        if (images == null)
            return;
        show(context, new String[]{images}, 0, needSaveLocal);
    }

    public static void show(Context context, String images, boolean needSaveLocal, boolean needCookie) {
        if (images == null)
            return;
        show(context, new String[]{images}, 0, needSaveLocal, needCookie);
    }

    public static void show(Context context, String[] images, int position) {
        show(context, images, position, true);
    }

    public static void show(Context context, String[] images, int position, boolean needSaveLocal) {
        show(context, images, position, needSaveLocal, false);
    }

    public static void show(Context context, String[] images, int position, boolean needSaveLocal, boolean needCookie) {
        if (images == null || images.length == 0)
            return;
        Intent intent = new Intent(context, ImageGalleryActivity.class);
        intent.putExtra(KEY_IMAGE, images);
        intent.putExtra(KEY_POSITION, position);
        intent.putExtra(KEY_NEED_SAVE, needSaveLocal);
        intent.putExtra(KEY_COOKIE, needCookie);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_image_gallery;
    }

    @Override
    protected boolean initBundle(Bundle bundle) {
        mImageSource = bundle.getStringArray(KEY_IMAGE);
        mCurPosition = bundle.getInt(KEY_POSITION, 0);
        mNeedSaveLocal = bundle.getBoolean(KEY_NEED_SAVE, true);
        mNeedCookie = bundle.getBoolean(KEY_COOKIE, false);

        if (mImageSource != null) {
            // 初始化下载状态
            mImageDownloadStatus = new boolean[mImageSource.length];
            return true;
        }

        return false;

    }

    @Override
    protected void initWindow() {
        super.initWindow();
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("");

        mImagePreviewViewpaper = (PreviewViewpaper) findViewById(R.id.vp_image);
        mIndexText = (TextView) findViewById(R.id.tv_index);

        mImagePreviewViewpaper.addOnPageChangeListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        int len = mImageSource.length;
        if (mCurPosition < 0 || mCurPosition >= len) {
            mCurPosition = 0;
        }

        if (len == 1) {
            mIndexText.setVisibility(View.GONE);
        }
        mImagePreviewViewpaper.setAdapter(new ViewpaperAdapter());
        mImagePreviewViewpaper.setCurrentItem(mCurPosition);
        onPageSelected(mCurPosition);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurPosition = position;
        mIndexText.setText(String.format("%s/%s", (position + 1), mImageSource.length));
        // 滑动时自动切换当前的下载状态
        changeSaveButtonStatus(mImageDownloadStatus[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @SuppressWarnings("unused")
    @AfterPermissionGranted(PERMISSION_ID)
    @OnClick(R.id.iv_save)
    public void saveToFileByPermission() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, permissions)) {
            saveToFile();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.gallery_save_file_permmison_grant), PERMISSION_ID, permissions);
        }
    }

    private void changeSaveButtonStatus(boolean isShow) {
        if (mNeedSaveLocal) {
            findViewById(R.id.iv_save).setVisibility(isShow ? View.VISIBLE : View.GONE);
        } else
            findViewById(R.id.iv_save).setVisibility(View.GONE);
    }

    private void updateDownloadStatus(int pos, boolean isOk) {
        mImageDownloadStatus[pos] = isOk;
        if (mCurPosition == pos) {
            changeSaveButtonStatus(isOk);
        }
    }

    private Point mDisplayDimens;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @SuppressWarnings("deprecation")
    private synchronized Point getDisplayDimens() {
        if (mDisplayDimens != null) {
            return mDisplayDimens;
        }
        Point displayDimens;
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            displayDimens = new Point();
            display.getSize(displayDimens);
        } else {
            displayDimens = new Point(display.getWidth(), display.getHeight());
        }

        // In this we can only get 85% width and 60% height
        //displayDimens.y = (int) (displayDimens.y * 0.60f);
        //displayDimens.x = (int) (displayDimens.x * 0.85f);

        mDisplayDimens = displayDimens;
        return mDisplayDimens;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, R.string.gallery_save_file_not_have_external_storage_permission, Toast.LENGTH_SHORT).show();
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    private void saveToFile() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, R.string.gallery_save_file_not_have_external_storage, Toast.LENGTH_SHORT).show();
            return;
        }

        String path = mImageSource[mCurPosition];

        Object urlOrPath;
        // Do load
        if (mNeedCookie) {
            //
//            urlOrPath = AppOperator.getGlideUrlByUser(path);
            urlOrPath = null;
        } else {
            urlOrPath = path;
        }

        // In this save max image size is source
        final Future<File> future = getImageLoader()
                .load(urlOrPath)
                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

        AppOperator.runOnBackgrounThread(new Runnable() {
            @Override
            public void run() {
                try {
                    File sourceFile = future.get();
                    if (sourceFile == null || !sourceFile.exists())
                        return;
                    String extension = BitmapUtil.getExtension(sourceFile.getAbsolutePath());
                    String extDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            .getAbsolutePath() + File.separator + "开源中国";
                    File extDirFile = new File(extDir);
                    if (!extDirFile.exists()) {
                        if (!extDirFile.mkdirs()) {
                            // If mk dir error
                            callSaveStatus(false, null);
                            return;
                        }
                    }
                    final File saveFile = new File(extDirFile, String.format("IMG_%s.%s", System.currentTimeMillis(), extension));
                    final boolean isSuccess = StreamUtil.copyFile(sourceFile, saveFile);
                    callSaveStatus(isSuccess, saveFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    callSaveStatus(false, null);
                }
            }
        });
    }

    private void callSaveStatus(final boolean success, final File savePath) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (success) {
                    // notify
                    Uri uri = Uri.fromFile(savePath);
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                    Toast.makeText(ImageGalleryActivity.this, R.string.gallery_save_file_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ImageGalleryActivity.this, R.string.gallery_save_file_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class ViewpaperAdapter extends PagerAdapter implements ImagePreviewView.OnReachBorderListener {

        private View.OnClickListener mFinishClickListener;

        @Override
        public int getCount() {
            return mImageSource.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.lay_gallery_page_item_contener, container, false);
            ImagePreviewView previewView = (ImagePreviewView) view.findViewById(R.id.iv_preview);
            previewView.setOnReachBorderListener(this);
            Loading loading = (Loading) view.findViewById(R.id.loading);
            ImageView defaultView = (ImageView) view.findViewById(R.id.iv_default);

            if (mNeedCookie) {

            } else {
                loadImage(position, mImageSource[position], previewView, defaultView, loading);
            }

            previewView.setOnClickListener(getListener());
            container.addView(view);
            return view;
        }

        private View.OnClickListener getListener() {
            if (mFinishClickListener == null) {
                mFinishClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                };
            }
            return mFinishClickListener;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


        @Override
        public void onReachBorder(boolean isReached) {
            mImagePreviewViewpaper.isInterceptable(isReached);
        }

        private <T> void loadImage(final int pos, final T urlOrPath,
                                   final ImageView previewView,
                                   final ImageView defaultView,
                                   final Loading loading) {
            loadImageDoDownAndGetOverrideSize(urlOrPath, new DoOverrideSizeCallback() {

                @Override
                public void onDone(int overrideW, int overrideH, boolean isTrue) {
                    GlideRequest<Drawable> request = getImageLoader().load(urlOrPath)
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    loading.stop();
                                    loading.setVisibility(View.GONE);
                                    defaultView.setVisibility(View.VISIBLE);
                                    updateDownloadStatus(pos, false);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    loading.stop();
                                    loading.setVisibility(View.GONE);
                                    updateDownloadStatus(pos, true);
                                    return false;
                                }
                            }).diskCacheStrategy(DiskCacheStrategy.DATA);


                    if (isTrue && overrideW > 0 && overrideH > 0) {
                        request = request.override(overrideW, overrideH).fitCenter();

                    }

                    request.into(previewView);
                }
            });
        }

        private <T> void loadImageDoDownAndGetOverrideSize(final T urlOrPath, final DoOverrideSizeCallback callback) {
            final FutureTarget<File> futureTarget = getImageLoader().load(urlOrPath)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
            AppOperator.runOnBackgrounThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        File sourceFile = futureTarget.get();
                        BitmapFactory.Options options = BitmapUtil.createOptions();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(sourceFile.getAbsolutePath(), options);

                        int width = options.outWidth;
                        int height = options.outHeight;
                        BitmapUtil.resetOptions(options);
                        if (width > 0 && height > 0) {
                            final Point screenPoint = getDisplayDimens();
                            final int maxLen = Math.min(Math.min(screenPoint.y, screenPoint.x) * 5, 1366 * 3);

                            final int overrideW, overrideH;
                            if ((width / (float) height) > (screenPoint.x / (float) screenPoint.y)) {
                                overrideH = Math.min(height, screenPoint.y);
                                overrideW = Math.min(width, maxLen);
                            } else {
                                overrideW = Math.min(width, screenPoint.x);
                                overrideH = Math.min(height, maxLen);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onDone(overrideW, overrideH, true);
                                    }
                                });

                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onDone(0, 0, false);
                                }
                            });
                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onDone(0, 0, false);
                            }
                        });
                    }
                }
            });
        }

    }

    interface DoOverrideSizeCallback {
        void onDone(int overrideW, int overrideH, boolean isTrue);
    }
}
