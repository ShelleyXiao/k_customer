package com.kidoo.customer.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.AppConfig;
import com.kidoo.customer.R;
import com.kidoo.customer.interf.OnTabReselectListener;
import com.kidoo.customer.ui.base.activities.BaseActivity;
import com.kidoo.customer.ui.fragment.NavigationFragement;
import com.kidoo.customer.utils.AppSystemUtils;
import com.kidoo.customer.utils.DialogHelper;
import com.kidoo.customer.widget.NavButtomButton;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends BaseActivity implements View.OnClickListener
,NavigationFragement.OnNavigationReselectListener, EasyPermissions.PermissionCallbacks{


    private static final int RC_EXTERNAL_STORAGE = 0x02;//存储权限
    private static final int RC_LOCATION = 0X04;

    private long mBackPressedTime;

    private NavigationFragement mNavBar;

    @BindView(R.id.activity_main_ui)
    LinearLayout mMainUi;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        FragmentManager manager = getSupportFragmentManager();
        mNavBar = ((NavigationFragement) manager.findFragmentById(R.id.fag_nav));
        mNavBar.setup(this, manager, R.id.main_container, this);
    }

    @Override
    public void initData() {
        super.initData();
        checkUpdate();
        checkLocation();


    }

    @Override
    public void onBackPressed() {
        long curTime = SystemClock.uptimeMillis();
        if ((curTime - mBackPressedTime) < (3 * 1000)) {
            finish();
        } else {
            mBackPressedTime = curTime;
            Toast.makeText(this, R.string.tip_double_click_exit, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onReselect(NavButtomButton navigationButton) {
        Fragment fragment = navigationButton.getFragment();
        if (fragment != null
                && fragment instanceof OnTabReselectListener) {
            OnTabReselectListener listener = (OnTabReselectListener) fragment;
            listener.onTabReselect();
        }
    }

    /*检查新版本*/
    private void checkUpdate() {

    }

    private void checkLocation() {

        //首先判断appCode是否存在，如果存在是否大于当前版本的appCode，或者第一次全新安装默认0)表示没有保存appCode
        int hasLocationAppCode = AppConfig.hasLocationAppCode(getApplicationContext());
        int versionCode = AppSystemUtils.getVersionCode();
        //if ((hasLocationAppCode <= 0) || (hasLocationAppCode > versionCode))
        if(true)
        {
            //如果是登陆状态，直接进行位置信息定位并上传
            if (AccountHelper.isLogin()) {
                //当app第一次被安装时，不管是覆盖安装（不管是否有定位权限）还是全新安装都必须进行定位请求
                AppConfig.updateLocationAppCode(getApplicationContext(), versionCode);
                requestLocationPermission();
            }
            return;
        }

        //如果有账户登陆，并且有主动上传过位置信息。那么准备请求定位
        if (AccountHelper.isLogin() && AppConfig.hasLocation(getApplicationContext())) {

            //1.有主动授权过，直接进行定位，否则不进行操作任何操作
            if (AppConfig.hasLocationPermission(getApplicationContext())) {
                requestLocationPermission();
            }
        }
    }

    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    public void requestExternalStorage() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        } else {
            EasyPermissions.requestPermissions(this, "", RC_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }


    @AfterPermissionGranted(RC_LOCATION)
    private void requestLocationPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE)) {
            //有权限，do something
//            startLbs();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.need_lbs_permission_hint), RC_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        for (String perm : perms) {
            if (perm.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                DialogHelper.getConfirmDialog(this, getString(R.string.permission_dlg_tilte), getString(R.string.permission_dlg_save_update), getString(R.string.permission_dlg_save_goto), getString(R.string.permission_dlg_save_no), true, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                    }
                }, null).show();

            } else if(perm.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                DialogHelper.getConfirmDialog(this, getString(R.string.permission_dlg_tilte), getString(R.string.permission_dlg_save_location), getString(R.string.permission_dlg_save_goto), getString(R.string.permission_dlg_save_no), true, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                    }
                }, null).show();
            }else {
                AppConfig.updateLocationPermission(getApplicationContext(), false);
            }
        }
    }
}
