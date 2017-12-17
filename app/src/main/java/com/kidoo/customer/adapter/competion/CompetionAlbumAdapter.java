package com.kidoo.customer.adapter.competion;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.CompetionAlbumBean;
import com.kidoo.customer.bean.MedalBean;
import com.kidoo.customer.media.ImageGalleryActivity;
import com.kidoo.customer.ui.base.adapter.BaseMultiTypeRecyclerAdapter;
import com.kidoo.customer.widget.glideimageview.GlideImageLoader;
import com.kidoo.customer.widget.glideimageview.GlideImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/16.
 */

public class CompetionAlbumAdapter extends BaseQuickAdapter<CompetionAlbumBean, BaseViewHolder> {

    private Context mContex;
    private String baseUrl;

    public CompetionAlbumAdapter(Context context, List<CompetionAlbumBean> datas) {
        super(R.layout.item_competion_album_layout, datas);
        this.baseUrl = AppContext.context().getInitData().getQnDomain();
        this.mContex = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CompetionAlbumBean item) {

        GlideImageView ivAlbumPic = (GlideImageView) helper.getView(R.id.iv_album_pic);
        RequestOptions options = ivAlbumPic.getImageLoader().requestOptions(R.color.placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        GlideImageLoader imageLoader = ivAlbumPic.getImageLoader();
        imageLoader.requestBuilder(baseUrl + item.getPicMini(), options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivAlbumPic);

        ivAlbumPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ArrayList<String> picUrls = new ArrayList<>();
                String baseUrl = AppContext.context().getInitData().getQnDomain();
                List<CompetionAlbumBean> mDatas = getData();
                for (CompetionAlbumBean dataBean : mDatas) {
                    String url = baseUrl + dataBean.getPic();
                    picUrls.add(url);
                }

                int position = mDatas.indexOf(item);
                String[] picUrlArr = new String[picUrls.size()];
                for(int i = 0;  i < picUrls.size(); i++) {
                    picUrlArr[i] = picUrls.get(i);
                }
                ImageGalleryActivity.show(mContext, picUrlArr, position);
            }


        });
    }
}
