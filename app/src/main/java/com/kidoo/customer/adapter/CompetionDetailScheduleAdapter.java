package com.kidoo.customer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kidoo.customer.R;
import com.kidoo.customer.bean.CompetionEpisodeBean;
import com.kidoo.customer.utils.DateTimeUtils;
import com.kidoo.customer.utils.VectorDrawableUtils;
import com.kidoo.customer.widget.timeline.TimelineView;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: ShaudXiao
 * Date: 2017-12-15
 * Time: 16:06
 * Company: zx
 * Description:
 * FIXME
 */


public class CompetionDetailScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CompetionEpisodeBean> mDatas;
    private Context mContext;

    public CompetionDetailScheduleAdapter(Context context, List<CompetionEpisodeBean> datas) {

        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_competion_schedule_layout, parent, false);
        return new CompetionScheduleViewHolder(view, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CompetionScheduleViewHolder viewHolder = (CompetionScheduleViewHolder) holder;
        CompetionEpisodeBean item = mDatas.get(position);
        if (item != null) {
            viewHolder.tvScheduleName.setText(item.getName()
                    + "("
                    + mContext.getResources().getStringArray(R.array.match_status)[item.getState()]
                    + ")");
            String startTime = DateTimeUtils.format(item.getStartTime(), DateTimeUtils.FORMAT_LONG);
            String stopTime = DateTimeUtils.format(item.getStopTime(), DateTimeUtils.FORMAT_LONG);
            String timeFormat = mContext.getResources()
                    .getString(R.string.competion_time);

            String timeStr = String.format(timeFormat, startTime, stopTime);

            viewHolder.tvScheduleTime.setText(timeStr);
            TimelineView timeLineView = viewHolder.mTimelineView;
            if (item.getState() == 0) {
                timeLineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
            } else if (item.getState() == 1) {
                timeLineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
            } else if (item.getState() == 2) {
                timeLineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_active, R.color.colorPrimary));
            } else {
                timeLineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.marker), ContextCompat.getColor(mContext, R.color.colorPrimary));
            }
        }

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    public void replaceData(@NonNull Collection<CompetionEpisodeBean> data) {
        // 不是同一个引用才清空列表
        if (data != mDatas) {
            mDatas.clear();
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }


    public class CompetionScheduleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvScheduleName;
        @BindView(R.id.tv_time)
        TextView tvScheduleTime;
        @BindView(R.id.time_marker)
        TimelineView mTimelineView;

        public CompetionScheduleViewHolder(View itemView, int viewType) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            mTimelineView.initLine(viewType);
        }
    }
}
