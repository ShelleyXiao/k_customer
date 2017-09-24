package com.kidoo.customer.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

import com.kidoo.customer.AppContext;




/** 
 * description: 界面帮助类
 * autour: ShaudXiao
 * date: 2017/9/24  
 * update: 2017/9/24
 * version: 
*/
public class UIHelper {


    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    public static void initWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setDefaultFontSize(14);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        int sysVersion = Build.VERSION.SDK_INT;
        if (sysVersion >= 11) {
            settings.setDisplayZoomControls(false);
        } else {
            ZoomButtonsController zbc = new ZoomButtonsController(webView);
            zbc.getZoomControls().setVisibility(View.GONE);
        }
        //webView.setWebViewClient(UiUtil.getWebViewClient());
    }




    /**
     * 组合动态的回复文本
     *
     * @param name
     * @param body
     * @return
     */
    public static SpannableStringBuilder parseActiveReply(String name,
                                                          String body) {
        Spanned span = Html.fromHtml(body.trim());
        SpannableStringBuilder sp = new SpannableStringBuilder(name + "：");
        sp.append(span);
        // 设置用户名字体加粗、高亮
        // sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
        // name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#008000")), 0,
                name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sp;
    }

    /**
     * 显示用户中心页面
     *
     * @param context
     * @param hisuid
     * @param hisuid
     * @param hisname
     */
    public static void showUserCenter(Context context, long hisuid,
                                      String hisname) {
        if (hisuid == 0 && hisname.equalsIgnoreCase("匿名")) {
            AppContext.showToast("提醒你，该用户为非会员");
            return;
        }
//        OtherUserHomeActivity.show(context, hisuid);
    }



    /**
     * 显示用户头像大图
     *
     * @param context
     * @param avatarUrl
     */
//    public static void showUserAvatar(Context context, String avatarUrl) {
//        if (TextUtils.isEmpty(avatarUrl)) {
//            return;
//        }
//        String url = AvatarView.getLargeAvatar(avatarUrl);
//        ImageGalleryActivity.show(context, url);
//    }

    /**
     * 显示扫一扫界面
     *
     * @param context
     */
//    public static void showScanActivity(Context context) {
//        Intent intent = new Intent(context, CaptureActivity.class);
//        context.startActivity(intent);
//    }


    /**
     * 清除app缓存
     */
    public static void clearAppCache(boolean showToast) {
        final Handler handler = showToast ? new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    AppContext.showToastShort("缓存清除成功");
                } else {
                    AppContext.showToastShort("缓存清除失败");
                }
            }
        } : null;
//        AppOperator.runOnThread(new Runnable() {
//            @Override
//            public void run() {
//                Message msg = new Message();
//                try {
//                    AppContext.context().clearAppCache();
//                    msg.what = 1;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    msg.what = -1;
//                }
//                if (handler != null)
//                    handler.sendMessage(msg);
//            }
//        });
    }







}
