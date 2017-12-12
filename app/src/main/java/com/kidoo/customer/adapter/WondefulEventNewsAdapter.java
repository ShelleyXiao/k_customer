package com.kidoo.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestOptions;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.NewsBean;
import com.kidoo.customer.utils.DateTimeUtils;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.KidooSubtitleView;
import com.kidoo.customer.widget.glideimageview.GlideImageView;
import com.kidoo.customer.widget.glideimageview.progress.CircleProgressView;
import com.kidoo.customer.widget.glideimageview.progress.OnGlideImageViewListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shelley on 2017/12/12.
 */

public class WondefulEventNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater inflater;
    private Context mContext;
    private List<NewsBean> mNewsBeans;
    private WondefulEventNewsAdapter.ContentViewHolder contentViewHolder;

    private String baseUrl;


    public WondefulEventNewsAdapter(Context mContext, List<NewsBean> newsBeans) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mNewsBeans = newsBeans;

        this.baseUrl = AppContext.context().getInitData().getQnDomain();
//        this.baseUrl = "http://ouhstqlop.bkt.clouddn.com/";

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WondefulEventNewsAdapter.ContentViewHolder(inflater.inflate(R.layout.item_home_list,
                parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int contentPosition;
        contentPosition = position;
        final NewsBean bean = mNewsBeans.get(contentPosition);
        contentViewHolder = (WondefulEventNewsAdapter.ContentViewHolder) holder;
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


        contentViewHolder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {

                    onClickListener.onPostItemClick(contentPosition, v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsBeans != null ? mNewsBeans.size() : 0;
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


    private WondefulEventNewsAdapter.OnClickListener onClickListener;

    /**
     * 设置文章列表条目点击
     */

    public void setOnClickListener(WondefulEventNewsAdapter.OnClickListener onItemClickListener) {
        this.onClickListener = onItemClickListener;
    }

    public interface OnClickListener {
        void onPostItemClick(int position, View view);

    }
}
