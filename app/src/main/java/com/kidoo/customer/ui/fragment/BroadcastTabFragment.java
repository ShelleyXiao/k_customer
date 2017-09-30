package com.kidoo.customer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.R;
import com.kidoo.customer.interf.LocationFace;
import com.kidoo.customer.interf.OnTabReselectListener;
import com.kidoo.customer.ui.base.fragment.BaseFragment;
import com.kidoo.customer.utils.LocationUtils;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.utils.Logger;
import com.kidoo.customer.widget.boardcastFloatBtn.contorl.FloatActionController;
import com.kidoo.customer.widget.boardcastFloatBtn.permission.FloatPermissionManager;

import butterknife.BindView;


/**
 * User: ShaudXiao
 * Date: 2017/9/17.
 * Time: 19:45
 * Company: zx
 * Description: 附件广播展示
 * FIXME
 */

public class BroadcastTabFragment extends BaseFragment implements OnTabReselectListener, BaiduMap.OnMarkerClickListener {

    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;

    @BindView(R.id.bmapView)
    MapView mMapView;
    private BaiduMap mBaiduMap;
    private Marker mMarkerSelf;

    private InfoWindow mInfoWindow;

    BitmapDescriptor mCurrentMarker;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private MyLocationData locData;
    private int mCurrentDirection = 0;

    boolean isFirstLoc = true; // 是否首次定位

    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdSSelf = BitmapDescriptorFactory
            .fromResource(R.drawable.gb_gr_icon);
    BitmapDescriptor bdGround = BitmapDescriptorFactory
            .fromResource(R.drawable.ground_overlay);


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.getLogger().d("****************** onActivityCreated");
    }



    @Override
    public void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mMapView.onPause();
        super.onPause();

        FloatActionController.getInstance().hide();
    }

    @Override
    public void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mMapView.onResume();
        super.onResume();

        new LocationUtils(AppContext.context(), new LocationFace() {
            @Override
            public void locationResult(BDLocation location) {
//                Logger.getLogger().e("****location = " + location.getCity() + " " + location.getAddrStr());
                LogUtils.e("****location = " + location.getCity() + " " + location.getAddrStr());

                mCurrentLat = location.getLatitude();
                mCurrentLon = location.getLongitude();
                mCurrentAccracy = location.getRadius();
                locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(mCurrentDirection).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                if (isFirstLoc) {
                    isFirstLoc = false;
                    LatLng ll = new LatLng(location.getLatitude(),
                            location.getLongitude());
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }
                initOverlay(location.getLongitude(), location.getLatitude(), bdSSelf);
            }
        });
        FloatActionController.getInstance().show();
    }

    @Override
    public void onDestroy() {
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();

        bdSSelf.recycle();
        bdGround.recycle();
        FloatActionController.getInstance().stopMonkServer(getActivity());
    }

    @Override
    public  void initWidget(View root) {
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(msu);
        // 开启定位图层
  //      mBaiduMap.setMyLocationEnabled(true);

        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleStrokeColor));

        boolean isPermission = FloatPermissionManager.getInstance().applyFloatWindow(getActivity());
        if(isPermission) {
            FloatActionController.getInstance().startFloatWMServer(this.getActivity());
        } else {
            LogUtils.i("Float have no peimisson!!!");
        }

    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    protected int getLayoutId() {

        return R.layout.layout_main_tab_broadcast;
    }


    @Override
    public void onTabReselect() {
        LogUtils.d("tab relect");
    }

    public void initOverlay(double longitude, double latitude, BitmapDescriptor bd ) {
        // add marker overlay
        LatLng ll = new LatLng(longitude, latitude);
        MarkerOptions ooA = new MarkerOptions().position(ll).icon(bd)
                .zIndex(9).draggable(true);
        // 掉下动画
        ooA.animateType(MarkerOptions.MarkerAnimateType.drop);

        Marker marker = (Marker) (mBaiduMap.addOverlay(ooA));
/*
        // add ground overlay
        LatLng southwest = new LatLng(longitude + 20 , latitude + 20);
        LatLng northeast = new LatLng(longitude - 20 , latitude - 20);
        LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
                .include(southwest).build();

        OverlayOptions ooGround = new GroundOverlayOptions()
                .positionFromBounds(bounds).image(bdGround).transparency(0.8f);
        mBaiduMap.addOverlay(ooGround);

        MapStatusUpdate u = MapStatusUpdateFactory
                .newLatLng(bounds.getCenter());
        mBaiduMap.setMapStatus(u);
*/
        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
            }

            public void onMarkerDragEnd(Marker marker) {
                Toast.makeText(
                        BroadcastTabFragment.this.getActivity(),
                        "拖拽结束，新位置：" + marker.getPosition().latitude + ", "
                                + marker.getPosition().longitude,
                        Toast.LENGTH_LONG).show();
            }

            public void onMarkerDragStart(Marker marker) {
            }
        });
    }

    /*
    * 标记点击监听
    *
    * */
    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
