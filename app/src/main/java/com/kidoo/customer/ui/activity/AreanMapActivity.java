package com.kidoo.customer.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.AreanaBean;
import com.kidoo.customer.bean.ChannelA;
import com.kidoo.customer.bean.ChannelC;
import com.kidoo.customer.bean.InitData;
import com.kidoo.customer.mvp.contract.MapContract;
import com.kidoo.customer.mvp.presenter.MapPresenterImpl;
import com.kidoo.customer.ui.base.activities.BaseBackMvpActivity;
import com.kidoo.customer.utils.DialogHelper;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.expandMenu.SelectMenuView;
import com.kidoo.customer.widget.glideimageview.GlideImageView;
import com.kidoo.customer.widget.mapView.BaidumapView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * User: ShaudXiao
 * Date: 2017-12-11
 * Time: 15:13
 * Company: zx
 * Description:
 * FIXME
 */

public class AreanMapActivity extends BaseBackMvpActivity<MapPresenterImpl> implements EasyPermissions.PermissionCallbacks
        , MapContract.View, SelectMenuView.OnMenuSelectDataChangedListener, BaidumapView.OnMyMarkerClickListener {

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

    private List<ChannelA> mChannelList;
    private List<AreanaBean> mAreanBeans;
    private int mSelectChannelID = -1;
    private int mSelectChannelAIndex = 0;
    private int mSelectChannelBIndex = 0;
    private int mSelectChannelCIndex = 0;

    private String baseUrl;

    @Override
    public void onPause() {
//        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
//        mMapView.onResume();
        super.onResume();

        InitData initData = AppContext.context().getInitData();
        baseUrl = initData.getQnDomain();

        mPresenter.doQueryAllChannels();

    }

    @Override
    protected MapPresenterImpl initInjector() {
        mActivityComponent.inject(this);

        return mPresenter;
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();

    }

    @Override
    public void initWidget() {
        super.initWidget();

        mMapView.setMyMarkerClickListener(this);
        selectMenuView.setOnMenuSelectDataChangedListener(this);
    }

    @Override
    public void initData() {
        super.initData();

        List<ChannelA> channelAList = AppContext.context().getgChannelAList();
        if (channelAList != null && channelAList.size() > 0) {
            selectMenuView.setDataList(channelAList);
        }

        requsetLocation();
    }


    @Override
    protected int getContentView() {
        return R.layout.layout_main_tab_broadcast;
    }


    @AfterPermissionGranted(RC_LOC_PERM)
    private void requsetLocation() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE)) {
            // Have permission, do the thing!
//            Toast.makeText(this, "TODO: SMS things", Toast.LENGTH_LONG).show();
            mPresenter.doQueryAreans(mSelectChannelID);

        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, getString(R.string.need_lbs_permission_hint), LOCATION_PERMISSION,
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
            confirmDialog = DialogHelper.getConfirmDialog(this, getString(R.string.no_location_hint),
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
        if (mChannelList == null) {
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

                AppContext.context().setgChannelAList(dataList);

                mSelectChannelID = dataList.get(mSelectChannelAIndex).getChannelBList()
                        .get(mSelectChannelBIndex)
                        .getChannelCList().get(mSelectChannelCIndex)
                        .getId();

                mPresenter.doQueryAreans(mSelectChannelID);
            }
        }


    }

    @Override
    public void updateAreans(List<AreanaBean> datas) {
        if (mAreanBeans == null) {
            mAreanBeans = new ArrayList<>();
        }
        mAreanBeans.clear();
        mAreanBeans.addAll(datas);

        mMapView.clearAllOverlys();

        for (AreanaBean bean : mAreanBeans) {
            addMapOverlyItem(bean);
        }

        LatLng latLng = null;
        if (!findCloseArean(mAreanBeans, latLng)) {
            LogUtils.i("setCenter");
            mMapView.setCenter(latLng);
        }

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
        AreanaBean bean = (AreanaBean) marker.getExtraInfo().get("marker");
        if (bean != null) {
            LogUtils.i(bean.toString());

            ArenDetailActivity.showArenDetail(this, mSelectChannelAIndex,
                    mSelectChannelBIndex, mSelectChannelCIndex, bean);
        }
    }

    private void addMapOverlyItem(AreanaBean bean) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_map_view_marker, null, false);
        GlideImageView glideImageView = (GlideImageView) view.findViewById(R.id.img_pic);

//        RequestOptions requestOptions = glideImageView.requestOptions(R.color.placeholder).centerCrop()
//                .diskCacheStrategy(DiskCacheStrategy.DATA)
//                .dontAnimate();
//
//        glideImageView.load(baseUrl + bean.getPicMini(), requestOptions);

        glideImageView.loadImage(baseUrl + bean.getPicMini(), R.color.placeholder);

//        ImageView glideImageView = (ImageView) view.findViewById(R.id.img_pic);
//        getImageLoader().load(baseUrl + bean.getPicMini()).into(glideImageView);

        mMapView.addOverlyLatLng(view, bean);
    }

    private boolean findCloseArean(List<AreanaBean> areanaBeans, LatLng cloesAreanLatLng) {
        LatLng myLatLng = mMapView.getMyLatLng();
        double minDistance = 0;
        for (AreanaBean bean : areanaBeans) {
            double distance = DistanceUtil.getDistance(myLatLng, new LatLng(bean.getLatitude(), bean.getLongitude()));
            if (distance < Constants.DEFAAULT_AREN_CLOSE) {
                LogUtils.i(cloesAreanLatLng);
                return true;
            }
            if (minDistance < distance) {
                minDistance = distance;
                cloesAreanLatLng = new LatLng(bean.getLatitude(), bean.getLongitude());
            }
        }

        return false;
    }

}