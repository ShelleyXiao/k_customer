package com.kidoo.customer.media;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.kidoo.customer.R;
import com.kidoo.customer.media.config.SelectOption;
import com.kidoo.customer.media.contract.SelectImageContract;
import com.kidoo.customer.ui.base.activities.BaseBackActivity;
import com.kidoo.customer.utils.DialogHelper;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * User: ShaudXiao
 * Date: 2017-09-25
 * Time: 18:48
 * Company: zx
 * Description:图片选择出来，统一图片选择方式，避免适配（来自开源组件）
 * FIXME
 */


public class SelectImageActivity extends BaseBackActivity implements EasyPermissions.PermissionCallbacks, SelectImageContract.Operator{

    private static final int RC_CAMERA_PERM = 0x03;
    private static final int RC_EXTERNAL_STORAGE = 0x04;
    public static final String KEY_CONFIG = "config";

    private static SelectOption mOption;
    private SelectImageContract.View mView;
    /*
    * 帶參數實例化
    * */
    public static void show(Context context, SelectOption options) {
        mOption = options;
        context.startActivity(new Intent(context, SelectImageActivity.class));
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_select_image;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        requestExternalStorage();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    @Override
    public void requestCamera() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            if (mView != null) {
                mView.onOpenCameraSuccess();
            }
        } else {
            EasyPermissions.requestPermissions(this, "", RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    @Override
    public void requestExternalStorage() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (mView == null) {
                handleView();
            }
        } else {
            EasyPermissions.requestPermissions(this, "", RC_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onBack() {
        onSupportNavigateUp();
    }

    @Override
    public void setDataView(SelectImageContract.View view) {
        mView = view;
    }

    @Override
    protected void onDestroy() {
        mOption = null;
        super.onDestroy();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == RC_EXTERNAL_STORAGE) {
            removeView();
            DialogHelper.getConfirmDialog(this, "", getString(R.string.media_select_image_store_perm_hint), getString(R.string.media_select_image_perm_setting_ok),
                    getString(R.string.media_select_image_perm_setting_cancel), false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                    finish();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();
        } else {
            if (mView != null)
                mView.onCameraPermissionDenied();
            DialogHelper.getConfirmDialog(this, "", getString(R.string.media_select_image_camera_perm_hint), getString(R.string.media_select_image_perm_setting_ok),
                    getString(R.string.media_select_image_perm_setting_cancel), false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }
    }

    private void removeView() {
        SelectImageContract.View view = mView;
        if (view == null)
            return;
        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove((Fragment) view)
                    .commitNowAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleView() {
        try {
            //Fragment fragment = Fragment.instantiate(this, SelectFragment.class.getName());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_content, SelectFragment.newInstance(mOption))
                    .commitNowAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
