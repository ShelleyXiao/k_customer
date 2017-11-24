package com.kidoo.customer.media.crop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.request.RequestOptions;
import com.kidoo.customer.R;
import com.kidoo.customer.media.config.SelectOption;
import com.kidoo.customer.ui.base.activities.BaseActivity;

import net.oschina.common.utils.StreamUtil;

import java.io.FileOutputStream;

import butterknife.OnClick;


/**
 * User: haibin
 * modify: ShaudXiao
 * Date: 2017-09-27
 * Time: 18:48
 * Company: zx
 * Description:
 * FIXME
 */

public class CropActivity extends BaseActivity implements View.OnClickListener {
    private CropLayout mCropLayout;
    private static SelectOption mOption;

    public static void show(Fragment fragment, SelectOption options) {
        Intent intent = new Intent(fragment.getActivity(), CropActivity.class);
        mOption = options;
        fragment.startActivityForResult(intent, 0x04);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_crop;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("");
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mCropLayout = (CropLayout) findViewById(R.id.cropLayout);
    }

    @Override
    protected void initData() {
        super.initData();

        String url = mOption.getSelectedImages().get(0);
        getImageLoader().load(url)
                .apply(new RequestOptions()
                .fitCenter())
                .into(mCropLayout.getImageView());


        mCropLayout.setCropWidth(mOption.getCropWidth());
        mCropLayout.setCropHeight(mOption.getCropHeight());
        mCropLayout.start();
    }

    @OnClick({R.id.tv_crop, R.id.tv_cancel})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_crop:
                Bitmap bitmap = null;
                FileOutputStream os = null;
                try {
                    bitmap = mCropLayout.cropBitmap();
                    String path = getFilesDir() + "/crop.jpg";
                    os = new FileOutputStream(path);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.flush();
                    os.close();

                    Intent intent = new Intent();
                    intent.putExtra("crop_path", path);
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bitmap != null) bitmap.recycle();
                    StreamUtil.close(os);
                }
                break;
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mOption = null;
        super.onDestroy();
    }

    @Override
    public void showToast(String msg) {

    }
}
