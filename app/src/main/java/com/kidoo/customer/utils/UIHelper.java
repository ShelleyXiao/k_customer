package com.kidoo.customer.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
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
import android.view.ViewGroup;
import android.view.WindowManager;
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

    private final static String TAG = "UiUtil";
    private final static String STATUS_BAR_DEF_PACKAGE = "android";
    private final static String STATUS_BAR_DEF_TYPE = "dimen";
    private final static String STATUS_BAR_NAME = "status_bar_height";
    private final static String STATUS_CLASS_NAME = "com.android.internal.R$dimen";
    private final static String STATUS_CLASS_FIELD = "status_bar_height";
    private static int STATUS_BAR_HEIGHT = 0;


    public static synchronized int getStatusBarHeight(final Context context) {
        if (STATUS_BAR_HEIGHT > 0)
            return STATUS_BAR_HEIGHT;

        Resources resources = context.getResources();
        int resourceId = context.getResources().
                getIdentifier(STATUS_BAR_NAME, STATUS_BAR_DEF_TYPE, STATUS_BAR_DEF_PACKAGE);
        if (resourceId > 0) {
            STATUS_BAR_HEIGHT = context.getResources().getDimensionPixelSize(resourceId);
            LogUtils.d( String.format("Get status bar height %s", STATUS_BAR_HEIGHT));
        } else {
            try {
                Class<?> clazz = Class.forName(STATUS_CLASS_NAME);
                Object object = clazz.newInstance();
                int height = Integer.parseInt(clazz.getField(STATUS_CLASS_FIELD)
                        .get(object).toString());
                STATUS_BAR_HEIGHT = resources.getDimensionPixelSize(height);
            } catch (Exception e) {
                e.printStackTrace();
                return (int) TDevice.dp2px(25);
            }
        }
        return STATUS_BAR_HEIGHT;
    }


    public static boolean changeViewHeight(final View view, final int aimHeight) {
        if (view.isInEditMode()) {
            return false;
        }

        if (view.getHeight() == aimHeight) {
            return false;
        }

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    aimHeight);
            view.setLayoutParams(layoutParams);
        } else {
            layoutParams.height = aimHeight;
            view.requestLayout();
        }

        return true;
    }

    public static boolean isFullScreen(final Activity activity) {
        return (activity.getWindow().getAttributes().flags &
                WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0;
    }

    public static boolean isTranslucentStatus(final Activity activity) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                (activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) != 0;
    }

    static boolean isFitsSystemWindows(final Activity activity) {
        return ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0).
                getFitsSystemWindows();
    }






}
