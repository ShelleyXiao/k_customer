package com.kidoo.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestOptions;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.BannerBean;
import com.kidoo.customer.bean.NewsBean;
import com.kidoo.customer.utils.DateTimeUtils;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.KidooSubtitleView;
import com.kidoo.customer.widget.glideimageview.GlideImageView;
import com.kidoo.customer.widget.glideimageview.progress.CircleProgressView;
import com.kidoo.customer.widget.glideimageview.progress.OnGlideImageViewListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;


public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater inflater;
    private Context mContext;
    private List<BannerBean> mBannerList;
    private List<NewsBean> mNewsBeans;
    private ContentViewHolder contentViewHolder;

    private String baseUrl;


    public enum ITEM_TYPE {
        ITEM_TOP,       //广告
        ITEM_TAGS,        //标签
        ITEM_CONTENT    //list
    }


    private String[] colors = new String[]{"#ddcfa6", "#c0dfd3", "#8c9bb9", "#caa196", "#bbcf97"};


    public HomeAdapter(Context mContext, List<BannerBean> bannerBeans, List<NewsBean> newsBeans) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mBannerList = bannerBeans;
        this.mNewsBeans = newsBeans;

        this.baseUrl = AppContext.context().getInitData().getQnDomain();
//        this.baseUrl = "http://ouhstqlop.bkt.clouddn.com/";

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE.ITEM_TOP.ordinal();
        } else if (position == 1) {
            return ITEM_TYPE.ITEM_TAGS.ordinal();
        } else {
            return ITEM_TYPE.ITEM_CONTENT.ordinal();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE.ITEM_TOP.ordinal()) {
            return new BannerAdsViewHolder(inflater.inflate(R.layout.item_banner_ads_home, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_TAGS.ordinal()) {
            final TagsViewHolder tagsViewHolder = new TagsViewHolder(inflater.inflate(R.layout.item_home_ranking_list, parent, false));
            return tagsViewHolder;
        } else {
            return new ContentViewHolder(inflater.inflate(R.layout.item_home_list, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BannerAdsViewHolder) {
            //广告条
            if (mBannerList.size() != 0) {
                final List<String> mImageList = new ArrayList<>();
                List<String> mTypeList = new ArrayList<>();
                for (BannerBean item : mBannerList) {
                    mImageList.add(item.getPic());
                    mTypeList.add(String.valueOf(item.getType()));
                }
                BannerAdsViewHolder adsViewHolder = (BannerAdsViewHolder) holder;

                adsViewHolder.vpTop.setData(getPageView(mBannerList));
                adsViewHolder.vpTop.setAdapter(new BGABanner.Adapter<ImageView, String>() {
                    @Override
                    public void fillBannerItem(BGABanner banner, ImageView itemView, String type, int position) {
                        ((GlideImageView) itemView).loadImage(baseUrl + mImageList.get(position), R.color.placeholder);
                    }
                });
                adsViewHolder.vpTop.setAutoPlayAble(true);

                adsViewHolder.vpTop.setDelegate(new BGABanner.Delegate<ImageView, String>() {
                    @Override
                    public void onBannerItemClick(BGABanner banner, ImageView itemView, String type, int position) {
                        if (onClickListener != null) {
                            onClickListener.onBannerItemClick(position, itemView);
                        }
                    }
                });
            }

        } else if (holder instanceof TagsViewHolder) {

            TagsViewHolder rankHolder = (TagsViewHolder) holder;
            rankHolder.campaignInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onStarRankingListClick(v);
                    }
                }
            });

            rankHolder.findTeam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onStarRankingListClick(v);
                    }


                }
            });
            rankHolder.arenMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onStarRankingListClick(v);
                    }


                }
            });

            rankHolder.review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onStarRankingListClick(v);
                    }


                }
            });


        } else {
            //对内容位置进行修正
            final int contentPosition;
            contentPosition = position - 2;
            final NewsBean bean = mNewsBeans.get(contentPosition);
            contentViewHolder = (ContentViewHolder) holder;
//            contentViewHolder.kvTypeTitle.setTitle(bean.getTitle());
            contentViewHolder.tvInfoTitle.setText(bean.getTitle());
            contentViewHolder.tvTime.setText(DateTimeUtils.formatDateTime(DateTimeUtils.format(bean.getUpdateTime(),
                    "yyyy-MM-dd HH:mm")));


            RequestOptions requestOptions = contentViewHolder.ivInfoPic.requestOptions(R.color.placeholder).centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);

            contentViewHolder.ivInfoPic.load(baseUrl + bean.getTitlePic(), requestOptions).listener(new OnGlideImageViewListener() {
                @Override
                public void onProgress(int percent, boolean isDone, GlideException exception) {
                    if (exception != null && !TextUtils.isEmpty(exception.getMessage())) {
                        LogUtils.e(exception.getMessage());
                    }
                    contentViewHolder.pvInfoProgress.setProgress(percent);
                    contentViewHolder.pvInfoProgress.setVisibility(isDone ? View.GONE : View.VISIBLE);
                }
            });


//            contentViewHolder.ivInfoPic.loadImage(baseUrl + bean.getTitlePic(), R.color.placeholder);

            contentViewHolder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {

                        onClickListener.onPostItemClick(contentPosition, v);
                    }
                }
            });
//


        }
    }

    private List<View> getPageView(List<BannerBean> bannerList) {
        List<View> views = new ArrayList<>();
        for (BannerBean bean : bannerList) {
            GlideImageView imageView = new GlideImageView(mContext);
            views.add(imageView);
        }
        return views;
    }

    @Override
    public int getItemCount() {
        if (mNewsBeans != null) {
            return mNewsBeans.size() + 2;
        }

        return 0;
    }

    public void addBannerData(List<BannerBean> bannerList) {
        if (bannerList == null) {
            return;
        }
        mBannerList.addAll(bannerList);
        notifyDataSetChanged();
    }

    public void addData(List<NewsBean> newsBeans) {

        if (newsBeans == null) {
            return;
        }
        mNewsBeans.addAll(newsBeans);
        notifyDataSetChanged();
    }

    public void addData(NewsBean dataBean) {
        if (mNewsBeans != null) {
            mNewsBeans.add(dataBean);
        }
        notifyDataSetChanged();
    }

    public void clearAllNews() {
        if (mNewsBeans != null) {
            mNewsBeans.clear();
        }
    }

    /**
     * @param position
     */
    public void deleteItem(int position) {
        if (mNewsBeans == null) {
            return;
        }
        mNewsBeans.remove(position);
        notifyDataSetChanged();
    }


    class BannerAdsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vp_top)
        BGABanner vpTop;

        public BannerAdsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    class TagsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.campaign_info)
        RelativeLayout campaignInfo;

        @BindView(R.id.find_team)
        RelativeLayout findTeam;

        @BindView(R.id.aren_map)
        RelativeLayout arenMap;

        @BindView(R.id.review)
        RelativeLayout review;

        public TagsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class ContentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.content)
        View content;
        @BindView(R.id.info_type_title)
        KidooSubtitleView kvTypeTitle;

        @BindView(R.id.info_title)
        TextView tvInfoTitle;

        @BindView(R.id.update_time)
        TextView tvTime;

        @BindView(R.id.info_pic)
        GlideImageView ivInfoPic;

        @BindView(R.id.info_pic_progress)
        CircleProgressView pvInfoProgress;


        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private OnClickListener onClickListener;

    /**
     * 设置文章列表条目点击
     */

    public void setOnClickListener(OnClickListener onItemClickListener) {
        this.onClickListener = onItemClickListener;
    }

    public interface OnClickListener {
        void onPostItemClick(int position, View view);

        void onStarRankingListClick(View view);


        void onBannerItemClick(int position, View view);


        void onBtnHeadAndNameClick(int position);


    }


}
