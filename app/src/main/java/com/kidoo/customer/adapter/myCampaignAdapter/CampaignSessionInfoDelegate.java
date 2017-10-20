package com.kidoo.customer.adapter.myCampaignAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kidoo.customer.R;
import com.kidoo.customer.adapter.multitype.AdapterDelegate;
import com.kidoo.customer.adapter.myCampaignAdapter.model.CampaignSession;
import com.kidoo.customer.mvp.model.Episode;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.utils.TimeUtils;

import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-10-19
 * Time: 15:16
 * Company: zx
 * Description:
 * FIXME
 */


public class CampaignSessionInfoDelegate implements AdapterDelegate {

    private Context mContext;
    private LayoutInflater mInflater;

    public CampaignSessionInfoDelegate(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(@NonNull List items, int position) {
        if (items.get(position) instanceof CampaignSession) {
            LogUtils.w("session");
            return MyCampaignAdapter.SESSION;
        }

        return -3;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        LogUtils.w("Sessoion onBindViewHolder");
        return new CampaignSessionVH(mInflater.inflate(R.layout.item_campaign_session_info, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List items, int position, @NonNull RecyclerView.ViewHolder holder) {
        LogUtils.w("onBindViewHolder");
        if (items.get(position) instanceof CampaignSession) {
            CampaignSession session = (CampaignSession) items.get(position);
            Episode episode = session.getEpisode();
            LogUtils.w(episode);
            if (null != session && null != episode) {
                CampaignSessionVH vh = (CampaignSessionVH) holder;
                if (TextUtils.isEmpty(session.getTitle())) {
                    vh.sessionTitle.setVisibility(View.GONE);
                } else {
                    vh.sessionTitle.setText(session.getTitle());
                }
                String infoFormat = mContext.getResources().getString(R.string.campaign_session_num);
                String sessionOngoing = String.format(infoFormat, episode.getRoundNo(), episode.getNo());
                vh.sessionInfo.setText(sessionOngoing);

                String startTimeFormat = mContext.getResources().getString(R.string.campaign_session_startTime);
                vh.sessionStartTime.setText(String.format(startTimeFormat, TimeUtils.millis2String(episode.getStartTime())));

                String endTimeFormat = mContext.getResources().getString(R.string.campaign_session_endTime);
                vh.sessionEndTime.setText(String.format(endTimeFormat, TimeUtils.millis2String(episode.getStopTime())));

            }
        }

    }

    public static class CampaignSessionVH extends RecyclerView.ViewHolder {

        TextView sessionTitle;
        TextView sessionInfo;
        TextView sessionStartTime;
        TextView sessionEndTime;


        public CampaignSessionVH(View itemView) {
            super(itemView);
            sessionTitle = (TextView) itemView.findViewById(R.id.campaign_session_title);
            sessionInfo = (TextView) itemView.findViewById(R.id.campaign_session_info);
            sessionStartTime = (TextView) itemView.findViewById(R.id.campaign_session_starttime);
            sessionEndTime = (TextView) itemView.findViewById(R.id.campaign_session_endtime);
        }
    }
}
