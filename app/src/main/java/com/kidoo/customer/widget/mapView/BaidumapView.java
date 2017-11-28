package com.kidoo.customer.widget.mapView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.kidoo.customer.R;
import com.kidoo.customer.interf.LocationFace;
import com.kidoo.customer.utils.LogUtils;

import java.util.HashMap;

/**
 * User: ShaudXiao
 * Date: 2017-11-14
 * Time: 16:12
 * Company: zx
 * Description:
 * FIXME
 */


public class BaidumapView extends LinearLayout implements BaiduMap.OnMapClickListener {

    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;

    private Context mContext;

    private MapView mMapView;
    private BaiduMap mBaidumap;

    private BitmapDescriptor mCurrentMarker;

    private HashMap<String, Marker> mMarkerHashMap = new HashMap<>();

    private OnMyMarkerClickListener MyMarkerClickListener;

    public BaidumapView(Context context) {
        super(context);
    }

    public BaidumapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        initBaiduMap();
    }

    public BaidumapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBaiduMap();
    }


    public BaidumapView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mBaidumap.hideInfoWindow();
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    public  void setMyMarkerClickListener(OnMyMarkerClickListener myMarkerClickListener) {
        MyMarkerClickListener = myMarkerClickListener;
    }

    public void addOverlyLatLng(View view, LatLng latLng, String title) {
        BitmapDescriptor bdC = BitmapDescriptorFactory.fromView(view);
        MarkerOptions ooC = new MarkerOptions().title(title)
                .position(latLng)
                .icon(bdC)
                .perspective(false)
                .anchor(0.5f, 1f);
        ooC.animateType(MarkerOptions.MarkerAnimateType.grow);
        mMarkerHashMap.put(title, (Marker)(mBaidumap.addOverlay(ooC)));

        mBaidumap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(null != MyMarkerClickListener) {
                    MyMarkerClickListener.onMarkerClick(marker);
                }
                return true;
            }
        });
    }

//    public void onResume() {
//        mMapView.onResume();
//    }
//
//    public void onPause() {
//        mMapView.onPause();
//    }

    public void onDestroy() {
        mBaidumap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
    }

    public void setOverlyLatLng(String title, LatLng ll) {
        if(mMarkerHashMap != null) {
            Marker marker = mMarkerHashMap.get(title);
            if(null != marker) {
                marker.setPosition(ll);
            }
        }
    }

    private void initBaiduMap() {
        //初始化地图
        mMapView = new MapView(mContext);
        mMapView.showZoomControls(false);//缩放按钮

        mBaidumap = mMapView.getMap();
        mBaidumap.setMyLocationEnabled(true);

        mBaidumap.setMyLocationConfiguration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleStrokeColor));

        //地图点击事件处理
        mBaidumap.setOnMapClickListener(this);

//        mBaidumap.setOnMapLoadedCallback(new OnMapLoadedCallback() {
//
//            @Override
//            public void onMapLoaded() {
//                // TODO Auto-generated method stub
//                ToastUtils.showTextToast(SelectStationActivity.this, "地图加载完成");
//
//                //SearchButtonProcess();
//            }
//        });

//        //定位
        LocationUtils.getLocation(mContext);
        LocationUtils.setLocationFace(new LocationFace() {
            @Override
            public void locationResult(BDLocation location) {
                LogUtils.w(location.toString());
                setCenter(new LatLng(location.getLatitude(), location.getLongitude()));
            }
        });

        addView(mMapView);
    }

    private void setCenter(LatLng latLng) {
//        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
//
//        mBaidumap.animateMapStatus(u);
//
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.gb_choice_icon);
        //创建一个图层选项
        OverlayOptions options = new MarkerOptions().position(latLng).icon(bitmapDescriptor);
        mBaidumap.addOverlay(options);
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(14)
                .build();

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaidumap.setMapStatus(mMapStatusUpdate);


    }



    public interface OnMyMarkerClickListener {
        void onMarkerClick(Marker arg0);
    }
}
