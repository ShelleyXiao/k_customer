package com.kidoo.customer.utils;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.kidoo.customer.interf.LocationFace;

/**
 * User: ShaudXiao
 * Date: 2017-09-19
 * Time: 19:45
 * Company: zx
 * Description: 定位帮助类
 * FIXME
 */


public class LocationUtils extends BDAbstractLocationListener {

    public LocationClient mLocationClient = null;
    private Context context;
    private LocationFace locationFace;

    public LocationUtils(Context context, LocationFace locationFace) {
        this.locationFace = locationFace;
        this. context = context;
        mLocationClient = new LocationClient(context);
        mLocationClient.registerLocationListener(LocationUtils.this);
        startLocation();
    }

    private void startLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode. Hight_Accuracy); // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType( "bd09ll"); // 可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(0); // 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress( true); // 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps( true); // 可选，默认false,设置是否使用 gps
        option.setLocationNotify( true); // 可选，默认false，设置是否当 gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe( true); // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList( true); // 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess( false); // 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException( false); // 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps( false); // 可选，默认false，设置是否需要过滤 gps仿真结果，默认需要
        mLocationClient.setLocOption(option);

        mLocationClient.start();
    }

    @Override
    public void onReceiveLocation(BDLocation arg0) {
        LogUtils.d( "Location return : ************ " + arg0.getLocType());
        //注意这里，一定要判断BdLocation的返回值，只有在getLocType（）==61或者161的情况下才表示定位成功，具体返回的错误码可参考http://lbsyun.baidu.com/index.php?title=android-locsdk/guide/ermsg
        if (arg0.getLocType() == 61 || arg0.getLocType() == 161 && arg0.getLatitude() != 0.0) {

            //将定位结果回调给locationFace的locationResult（）方法
            if(null != locationFace) {

                locationFace.locationResult(arg0);
            }

        }
    }

    /**
     * 回调定位诊断信息，开发者可以根据相关信息解决定位遇到的一些问题
     * 自动回调，相同的diagnosticType只会回调一次
     *
     * @param locType           当前定位类型
     * @param diagnosticType    诊断类型（1~9）
     * @param diagnosticMessage 具体的诊断信息释义
     */
    public void onLocDiagnosticMessage(int locType, int diagnosticType, String diagnosticMessage) {

        if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_BETTER_OPEN_GPS) {

            //建议打开GPS

        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_BETTER_OPEN_WIFI) {

            //建议打开wifi，不必连接，这样有助于提高网络定位精度！

        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_CHECK_LOC_PERMISSION) {

            //定位权限受限，建议提示用户授予APP定位权限！

        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_CHECK_NET) {

            //网络异常造成定位失败，建议用户确认网络状态是否异常！

        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_CLOSE_FLYMODE) {

            //手机飞行模式造成定位失败，建议用户关闭飞行模式后再重试定位！

        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_INSERT_SIMCARD_OR_OPEN_WIFI) {

            //无法获取任何定位依据，建议用户打开wifi或者插入sim卡重试！

        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_NEED_OPEN_PHONE_LOC_SWITCH) {

            //无法获取有效定位依据，建议用户打开手机设置里的定位开关后重试！

        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_SERVER_FAIL) {

            //百度定位服务端定位失败
            //建议反馈location.getLocationID()和大体定位时间到loc-bugs@baidu.com

        } else if (diagnosticType == LocationClient.LOC_DIAGNOSTIC_TYPE_FAIL_UNKNOWN) {

            //无法获取有效定位依据，但无法确定具体原因
            //建议检查是否有安全软件屏蔽相关定位权限
            //或调用LocationClient.restart()重新启动后重试！

        }
    }

}
