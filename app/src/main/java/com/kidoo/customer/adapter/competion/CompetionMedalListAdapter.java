package com.kidoo.customer.adapter.competion;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.MedalBean;
import com.kidoo.customer.media.ImageGalleryActivity;
import com.kidoo.customer.ui.activity.ArenDetailActivity;
import com.kidoo.customer.ui.base.adapter.BaseMultiTypeRecyclerAdapter;
import com.kidoo.customer.widget.glideimageview.GlideImageLoader;
import com.kidoo.customer.widget.glideimageview.GlideImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/12/16.
 */

public class CompetionMedalListAdapter extends BaseQuickAdapter<MedalBean, BaseViewHolder> {

    private Context mContex;
    private String baseUrl;

    public CompetionMedalListAdapter(Context context, List<MedalBean> datas) {
        super(R.layout.item_competion_medal_list_layout, datas);
        this.baseUrl = AppContext.context().getInitData().getQnDomain();
        this.mContex = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MedalBean item) {
        helper.setText(R.id.tv_medal_name, item.getName());

        GlideImageView ivMedalPic = (GlideImageView) helper.getView(R.id.iv_medal_pic);
        RequestOptions options = ivMedalPic.getImageLoader().requestOptions(R.color.placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        GlideImageLoader imageLoader = ivMedalPic.getImageLoader();
        imageLoader.requestBuilder(baseUrl + item.getPicMini(), options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivMedalPic);

        ivMedalPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (item != null) {

                    String picUrl = AppContext.context().getInitData().getQnDomain() + item.getPic();
                    if (!TextUtils.isEmpty(picUrl)) {
                        ImageGalleryActivity.show(mContex, picUrl);
                    }
                }
            }


        });

    }
}
