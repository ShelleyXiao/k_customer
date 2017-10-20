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
import com.kidoo.customer.adapter.myCampaignAdapter.model.CampaigBaseInfo;
import com.kidoo.customer.utils.LogUtils;

import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-10-19
 * Time: 15:16
 * Company: zx
 * Description:
 * FIXME
 */


public class CampaignBaseInfoDelegate implements AdapterDelegate {

    private Context mContext;
    private LayoutInflater mInflater;

    public CampaignBaseInfoDelegate(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(@NonNull List items, int position) {
        if(items.get(position) instanceof CampaigBaseInfo) {
            return MyCampaignAdapter.BASE_INFO;
        }

        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        LogUtils.w("base info onBindViewHolder");
        return new CampaignBaseInfoVH(mInflater.inflate(R.layout.item_campaign_info, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List items, int position, @NonNull RecyclerView.ViewHolder holder) {
        LogUtils.w("onBindViewHolder");
        if(items.get(position) instanceof CampaigBaseInfo) {
            CampaigBaseInfo info = (CampaigBaseInfo) items.get(position);
            CampaignBaseInfoVH vh = (CampaignBaseInfoVH) holder;
            if(TextUtils.isEmpty(info.getCampaignItemName())) {
                vh.campaignName.setVisibility(View.GONE);
            } else {
                vh.campaignName.setText(info.getCampaignItemName());
            }
            vh.campaignTitle.setText(info.getCampaignItemtitle());
            vh.campaignInfoContent.setText(info.getCamapignItemContent());
        }

    }

    public static class CampaignBaseInfoVH extends RecyclerView.ViewHolder {

        TextView campaignName;

        TextView campaignTitle;

        TextView campaignInfoContent;

        public  CampaignBaseInfoVH(View itemView) {
            super(itemView);
            campaignName = (TextView) itemView.findViewById(R.id.campaign_name);
            campaignTitle = (TextView) itemView.findViewById(R.id.campaign_title);
            campaignInfoContent = (TextView) itemView.findViewById(R.id.campaign_info);
        }
    }
}
