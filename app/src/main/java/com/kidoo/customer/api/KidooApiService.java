package com.kidoo.customer.api;

import com.kidoo.customer.api.token.QNToken;
import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.ArenaListResult;
import com.kidoo.customer.bean.CheckAllTokenBean;
import com.kidoo.customer.bean.InitData;
import com.kidoo.customer.bean.KeypairResult;
import com.kidoo.customer.bean.LoginResult;
import com.kidoo.customer.bean.NewBananersResult;
import com.kidoo.customer.bean.NewsListResult;
import com.kidoo.customer.bean.RegisterResultBean;
import com.kidoo.customer.bean.ReturnNullBean;
import com.kidoo.customer.bean.SMSCodeBean;
import com.kidoo.customer.bean.UserDetailBean;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * User: ShaudXiao
 * Date: 2017-10-09
 * Time: 10:12
 * Company: zx
 * Description: api 接口
 * FIXME
 */


public interface KidooApiService {

//    public static final String BASE_API_URL = "http://www.kidoo.site";
    public static final String BASE_API_URL = "http://120.79.34.62:8061";

    @POST("customer/common/getKeyPair")
    @FormUrlEncoded
    Observable<KidooApiResult<KeypairResult>> getRSAKeyPair(@FieldMap Map<String, String> map);


    @POST("customer/common/login")
    @FormUrlEncoded
    Observable<KidooApiResult<LoginResult>> login(@FieldMap Map<String, String> map);

    @POST("customer/common/getSMS")
    @FormUrlEncoded
    Observable<KidooApiResult<SMSCodeBean>> getSMS(@FieldMap Map<String, String> map);

    @POST("customer/common/register")
    @FormUrlEncoded
    Observable<KidooApiResult<RegisterResultBean>> register(@FieldMap Map<String, String> map);

    @POST("customer/common/checkAllToken")
    @FormUrlEncoded
    Observable<KidooApiResult<CheckAllTokenBean>> checkAllToken(@FieldMap Map<String , String> map);

    @GET("customer/common/getInitData")
    Observable<KidooApiResult<InitData>> getInitData( @QueryMap Map<String, String> maps);

    @GET("customer/channel/queryAllChannels")
    Observable<KidooApiResult<AllChannelResultBean>> queryAllChannels();


    @POST("customer/arena/queryArenaMap")
    @FormUrlEncoded
    Observable<KidooApiResult<ArenaListResult>> queryArenaMap(@FieldMap Map<String, String> maps);

    @POST("customer/customer/queryDetail")
    @FormUrlEncoded
    Observable<KidooApiResult<UserDetailBean>> queryDetail(@FieldMap Map<String, String> maps);

    @POST("customer/account/update")
    @FormUrlEncoded
    Observable<KidooApiResult<ReturnNullBean>> updateUserInfo(@FieldMap Map<String,String> maps);

    @GET("customer/store/queryPicInfo")
    Observable<KidooApiResult<QNToken>> queryPicInfo();

    @GET("customer/banner/queryBannerList")
    Observable<KidooApiResult<NewBananersResult>> queryBannerList();

    @GET("customer/news/queryNewsList")
    Observable<KidooApiResult<NewsListResult>> queryNewsList(@QueryMap Map<String, String> maps);




}
