package com.kidoo.customer.ui;

import android.Manifest;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kidoo.customer.R;
import com.kidoo.customer.ui.base.BaseActivity;
import com.kidoo.customer.ui.fragment.NavigationFragement;
import com.kidoo.customer.widget.NavButtomButton;

import java.util.List;

import butterknife.Bind;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends BaseActivity implements View.OnClickListener
,NavigationFragement.OnNavigationReselectListener, EasyPermissions.PermissionCallbacks{

    private long mBackPressedTime;

    private NavigationFragement mNavBar;

    @Bind(R.id.activity_main_ui)
    LinearLayout mMainUi;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        FragmentManager manager = getSupportFragmentManager();
        mNavBar = ((NavigationFragement) manager.findFragmentById(R.id.fag_nav));
        mNavBar.setup(this, manager, R.id.main_container, this);
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

    }

    /**
     * proxy request permission
     */
    @AfterPermissionGranted(NearbyActivity.LOCATION_PERMISSION)
    private void requestLocationPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE)) {
//            startLbs();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.need_lbs_permission_hint), LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
