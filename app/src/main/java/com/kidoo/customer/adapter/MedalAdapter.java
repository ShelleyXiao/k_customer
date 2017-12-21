package com.kidoo.customer.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.MedalBean;
import com.kidoo.customer.widget.glideimageview.GlideImageView;

import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-12-19
 * Time: 10:37
 * Company: zx
 * Description:
 * FIXME
 */


public class MedalAdapter extends BaseQuickAdapter<MedalBean, BaseViewHolder> {

    private String baseUrl;

    public MedalAdapter(Context context, String baseUrl, List<MedalBean> datas) {
        super(R.layout.item_medal_layout, datas);
        this.baseUrl = baseUrl;

    }

    @Override
    protected void convert(BaseViewHolder helper, MedalBean item) {
        GlideImageView ivMedalPic = (GlideImageView) helper.getView(R.id.iv_medal_pic);
//        RequestOptions options = ivMedalPic.getImageLoader().requestOptions(R.color.placeholder)
//                .centerCrop()
//                .diskCacheStrategy(DiskCacheStrategy.ALL);
//
//        GlideImageLoader imageLoader = ivMedalPic.getImageLoader();
//        imageLoader.requestBuilder(baseUrl + item.getPicMini(), options)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(ivMedalPic);

        ivMedalPic.loadImage(baseUrl + item.getPicMini(), R.color.placeholder);
    }
}
