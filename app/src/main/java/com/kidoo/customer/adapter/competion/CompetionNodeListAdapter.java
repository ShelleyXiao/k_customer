package com.kidoo.customer.adapter.competion;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.CompetionNodeBean;
import com.kidoo.customer.utils.DateTimeUtils;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.glideimageview.GlideImageView;
import com.kidoo.customer.widget.glideimageview.progress.CircleProgressView;
import com.kidoo.customer.widget.glideimageview.progress.OnGlideImageViewListener;

import java.util.List;

/**
 * Created by Administrator on 2017/12/16.
 */

public class CompetionNodeListAdapter extends BaseQuickAdapter<CompetionNodeBean, BaseViewHolder> {

    private Context mContex;
    private String baseUrl;

    public CompetionNodeListAdapter(Context context, List<CompetionNodeBean> datas) {
        super(R.layout.item_competion_node_list_layout, datas);
        mContex = context;
        this.baseUrl = AppContext.context().getInitData().getQnDomain();
    }

    @Override
    protected void convert(BaseViewHolder helper, CompetionNodeBean item) {


        helper.setText(R.id.tv_node_time, DateTimeUtils.formatDateTime(DateTimeUtils.format(item.getTimePoint(),
                "yyyy-MM-dd HH:mm")));

        helper.setText(R.id.tv_node_msg, item.getMsg());
        GlideImageView ivNodePic = (GlideImageView) helper.getView(R.id.iv_node_pic);
        if(!TextUtils.isEmpty(item.getPic())) {

            final CircleProgressView progressView = (CircleProgressView) helper.getView(R.id.pv_node_pic_progress);

            RequestOptions requestOptions = ivNodePic.requestOptions(R.color.placeholder).centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);

            ivNodePic.load(baseUrl + item.getPic(), requestOptions).listener(new OnGlideImageViewListener() {
                @Override
                public void onProgress(int percent, boolean isDone, GlideException exception) {
                    if (exception != null && !TextUtils.isEmpty(exception.getMessage())) {
                        LogUtils.e(exception.getMessage());
                    }
                    progressView.setProgress(percent);
                    progressView.setVisibility(isDone ? View.GONE : View.VISIBLE);
                }
            });
        } else {
            ivNodePic.setImageDrawable(mContex.getResources().getDrawable(R.color.placeholder));
        }

    }
}
