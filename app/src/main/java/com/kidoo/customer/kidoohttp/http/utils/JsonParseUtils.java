package com.kidoo.customer.kidoohttp.http.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.ChannelA;
import com.kidoo.customer.bean.ChannelCMap;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-12-21
 * Time: 10:01
 * Company: zx
 * Description:
 * FIXME
 */

public class JsonParseUtils {

    public static KidooApiResult<AllChannelResultBean> parseAllChannel(String json) {
        if (TextUtils.isEmpty(json)) {
            LogUtils.e("json empty");
            return null;
        }
        KidooApiResult<AllChannelResultBean> result = new KidooApiResult<>();
        try {
//            LogUtils.i(json);
            AllChannelResultBean channelResultBean = new AllChannelResultBean();
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            if (jsonObject != null) {
                boolean success = jsonObject.get("success").getAsBoolean();
                result.setSuccess(success);
                JsonElement jsonErrElement = jsonObject.get("errorCode");
                if (!jsonErrElement.isJsonNull()) {

                    String errorCode = jsonErrElement.getAsJsonPrimitive().getAsString();
                    result.setErrorCode(errorCode);
                } else {
                    result.setErrorCode(null);
                }

                JsonElement jsonerrorMsgElement = jsonObject.get("errorMsg");
                if (!jsonerrorMsgElement.isJsonNull()) {
                    String errorMsg = jsonerrorMsgElement.getAsJsonPrimitive().getAsString();
                    result.setErrorMsg(errorMsg);
                } else {
                    result.setErrorMsg(null);
                }

                JsonElement jsonErrMsgDevElement = jsonObject.get("errorMsgDev");
                if (!jsonErrMsgDevElement.isJsonNull()) {
                    String errorMsgDev = jsonErrMsgDevElement.getAsJsonPrimitive().getAsString();
                    result.setErrorCode(errorMsgDev);
                } else {
                    result.setErrorMsgDev(null);
                }


                JsonObject data = jsonObject.get("data").getAsJsonObject();
                if (!data.isJsonNull()) {
                    JsonArray channelaArr = data.get("channelAList").getAsJsonArray();
                    Gson gson = new Gson();
                    List<ChannelA> channelAList = new ArrayList<>();
                    for (JsonElement element : channelaArr) {
                        ChannelA channelABean = gson.fromJson(element, new TypeToken<ChannelA>() {
                        }.getType());
                        channelAList.add(channelABean);
//                        LogUtils.i(channelABean.toString());
                    }
                    channelResultBean.setChannelAList(channelAList);
                    JsonObject channelCMap = data.get("channelCMap").getAsJsonObject();
//                    LogUtils.i(channelCMap.size());
                    List<ChannelCMap> channelCList = new ArrayList<>();
                    for (int i = 1; i <= channelCMap.size(); i++) {
                        JsonObject object = channelCMap.get(i + "").getAsJsonObject();
                        if (!object.isJsonNull()) {
                            ChannelCMap channelCBean = gson.fromJson(object, new TypeToken<ChannelCMap>() {
                            }.getType());
//                            LogUtils.i(channelCBean.toString());
                            channelCList.add(channelCBean);
                        }
                    }
                    channelResultBean.setChannelCmaps(channelCList);
                    result.setData(channelResultBean);
                } else {
                    result.setData(null);
                }

            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        }


        return result;
    }
}
