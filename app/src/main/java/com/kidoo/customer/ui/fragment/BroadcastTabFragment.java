package com.kidoo.customer.ui.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.baidu.mapapi.map.Marker;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.AreanaBean;
import com.kidoo.customer.bean.ChannelA;
import com.kidoo.customer.bean.ChannelC;
import com.kidoo.customer.interf.OnTabReselectListener;
import com.kidoo.customer.mvp.contract.MapContract;
import com.kidoo.customer.mvp.presenter.MapPresenterImpl;
import com.kidoo.customer.ui.base.fragment.BaseMvpFragment;
import com.kidoo.customer.utils.DialogHelper;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.expandMenu.SelectMenuView;
import com.kidoo.customer.widget.mapView.BaidumapView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * User: ShaudXiao
 * Date: 2017/9/17.
 * Time: 19:45
 * Company: zx
 * Description: 附件广播展示
 * FIXME
 */

public class BroadcastTabFragment extends BaseMvpFragment<MapPresenterImpl> implements OnTabReselectListener, EasyPermissions.PermissionCallbacks
        , MapContract.View, SelectMenuView.OnMenuSelectDataChangedListener, BaidumapView.OnMyMarkerClickListener {

    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;

    private static final int RC_LOC_PERM = 122;
    public static final int LOCATION_PERMISSION = 0x0100;//定位权限

    private AlertDialog.Builder confirmDialog;
    private AlertDialog alertDialog;

    @BindView(R.id.bmapView)
    BaidumapView mMapView;

    @BindView(R.id.search_menu)
    SelectMenuView selectMenuView;

    @Inject
    public MapPresenterImpl mPresenter;

    private List<ChannelA> mChannelList ;
    private List<AreanaBean> mAreanBeans ;
    private int mSelectChannelID = -1;
    private int mSelectChannelAIndex = 0;
    private int mSelectChannelBIndex = 0;
    private int mSelectChannelCIndex = 0;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
//        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
//        mMapView.onResume();
        super.onResume();

        mPresenter.doQueryAllChannels();

    }

    @Override
    protected MapPresenterImpl initInjector() {
        mFragmentComponent.inject(this);

        return mPresenter;
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();

    }

    @Override
    public void initWidget(View root) {

        mMapView.setMyMarkerClickListener(this);
        selectMenuView.setOnMenuSelectDataChangedListener(this);
    }

    @Override
    public void initData() {
        super.initData();

        requsetLocation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_main_tab_broadcast;
    }


    @Override
    public void onTabReselect() {
        LogUtils.w("tab relect");

        requsetLocation();
        mPresenter.doQueryAreans(mSelectChannelID);
    }


    @AfterPermissionGranted(RC_LOC_PERM)
    private void requsetLocation() {
        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE)) {
            // Have permission, do the thing!
//            Toast.makeText(getActivity(), "TODO: SMS things", Toast.LENGTH_LONG).show();

        } else {
            // Request one permission
            EasyPermissions.requestPermissions(getActivity(), getString(R.string.need_lbs_permission_hint), LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        showSettingDialog();
    }


    private void showSettingDialog() {

        if (confirmDialog == null) {
            confirmDialog = DialogHelper.getConfirmDialog(getActivity(), getString(R.string.no_location_hint),
                    getString(R.string.no_permission_hint),
                    getString(R.string.permission_dlg_save_no), getString(R.string.permission_dlg_save_goto), false, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivityForResult(intent, LOCATION_PERMISSION);
                        }
                    });
        }

        if (alertDialog == null) {
            alertDialog = confirmDialog.create();
        }

        if (alertDialog != null) {
            if (!alertDialog.isShowing()) {
                alertDialog.show();
            }
        }
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void updateUserInfo(AllChannelResultBean channelResultBean) {
        if(mChannelList == null) {
            mChannelList = new ArrayList<>();
        }
        if (channelResultBean != null) {
            LogUtils.i(channelResultBean.getChannelAList()[0].getDescription());
            if (channelResultBean.getChannelAList() != null) {
                mChannelList.clear();
                List<ChannelA> dataList = new ArrayList<>();
                dataList.add(channelResultBean.getChannelAList()[0]);
                selectMenuView.setDataList(dataList);
                mChannelList.addAll(dataList);
            }
        }

    }

    @Override
    public void updateAreans(List<AreanaBean> datas) {
        if(mAreanBeans == null) {
            mAreanBeans = new ArrayList<>();
        }
        mAreanBeans.clear();
        mAreanBeans.addAll(datas);
    }

    @Override
    public void onSubjectABChanged(int indexChannalA, int indexChannalB) {
        mSelectChannelAIndex = indexChannalA;
        mSelectChannelBIndex = indexChannalB;
    }

    @Override
    public void onSubjectCChanged(int indexChannalC) {
        mSelectChannelCIndex = indexChannalC;
        ChannelC selectChannelC = mChannelList.get(mSelectChannelAIndex)
                .getChannelBList().get(mSelectChannelBIndex)
                .getChannelCList().get(indexChannalC);
        mSelectChannelID = selectChannelC.getId();

        mPresenter.doQueryAreans(mSelectChannelID);
    }

    @Override
    public void onViewClicked(View view) {

    }

    @Override
    public void onSelectedDismissed(int indexChannalA, int indexChannalB) {

    }

    @Override
    public void onMarkerClick(Marker marker) {
        AreanaBean bean = (AreanaBean) marker.getExtraInfo ().get ("marker");
        if(bean != null) {
            //todo
        }
    }
}
