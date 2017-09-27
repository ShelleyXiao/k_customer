package com.kidoo.customer.media;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.kidoo.customer.R;
import com.kidoo.customer.media.adapter.ImageFolderAdapter;
import com.kidoo.customer.media.bean.ImageFolder;
import com.kidoo.customer.ui.base.adapter.BaseRecyleAdapter;

/**
 * User: ShaudXiao
 * Date: 2017-09-25
 * Time: 20:39
 * Company: zx
 * Description: 图片目录选择菜单
 * FIXME
 */


public class ImageFolderPopupWindow  extends PopupWindow implements View.OnAttachStateChangeListener ,
        BaseRecyleAdapter.OnItemClickListener{


    private ImageFolderAdapter mAdapter;
    private RecyclerView mFolderView;
    private Callback mCallback;

    public ImageFolderPopupWindow(Context context, Callback callback) {
        super(LayoutInflater.from(context).inflate(R.layout.popup_window_folder, null),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mCallback = callback;

        setAnimationStyle(R.style.popup_anim_style_alpha);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);
        setFocusable(true);

        View content = getContentView();
        content.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        content.addOnAttachStateChangeListener(this);

        mFolderView = (RecyclerView) content.findViewById(R.id.rv_popup_folder);
        mFolderView.setLayoutManager(new LinearLayoutManager(context ));
    }

    public void setAdapter(ImageFolderAdapter adapter) {
        this.mAdapter = adapter;
        mFolderView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT >= 24) { //6.0
            Rect visiableFrame = new Rect();
            anchor.getGlobalVisibleRect(visiableFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels
                    - visiableFrame.bottom;
            setHeight(height);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        final Callback callback =mCallback;
        if(null != callback) {
            callback.onShow();
        }
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        final Callback callback =mCallback;
        if(null != callback) {
            callback.onDismiss();
        }
    }

    @Override
    public void onItemClick(int position, long itemId) {
        final Callback callback =mCallback;
        if(null != callback) {
            callback.onSelect(this, mAdapter.getItem(position));
        }
    }

    public interface Callback {
        void onSelect(ImageFolderPopupWindow popupWindow, ImageFolder folder);

        void onDismiss();

        void onShow();
    }
}
