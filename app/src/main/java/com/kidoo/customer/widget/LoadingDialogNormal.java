package com.kidoo.customer.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.kidoo.customer.R;


public class LoadingDialogNormal extends Dialog {

    Dialog mLoadingDialog;

    public LoadingDialogNormal(Context context) {
        super(context);
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_normal_loadingdialog, null);
        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.loading_dialog);
        // 设置返回键无效
        mLoadingDialog.setCancelable(false);
        if (mLoadingDialog !=null);
//        mLoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.transparent)));
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public LoadingDialogNormal(Context context, int themeResId) {
        super(context,themeResId);

// 首先得到整个View
        View layout = LayoutInflater.from(context).inflate(
                R.layout.layout_loading, null);


        mLoadingDialog = new Dialog(context, R.style.loading_dialog);
        // 设置返回键无效
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

    }


    public void show(){
        if (mLoadingDialog!=null)
        mLoadingDialog.show();
    }

    public void close(){
        if (mLoadingDialog!=null) {
            mLoadingDialog.dismiss();
            mLoadingDialog=null;
        }
    }
}
