package com.kidoo.customer.adapter.myCampaignAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kidoo.customer.GlideApp;
import com.kidoo.customer.R;
import com.kidoo.customer.adapter.multitype.AdapterDelegate;
import com.kidoo.customer.adapter.myCampaignAdapter.model.CampaignHeader;
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


public class CampaignInfoHeaderDelegate implements AdapterDelegate {

    private Context mContext;
    private LayoutInflater mInflater;

    public CampaignInfoHeaderDelegate(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(@NonNull List items, int position) {
        if(items.get(position) instanceof CampaignHeader) {
            return MyCampaignAdapter.INFO_HEADER;
        }

        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        LogUtils.w("header onCreateViewHolder");
        return new CampaignHeaderVH(mInflater.inflate(R.layout.item_campaign_header, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List items, int position, @NonNull RecyclerView.ViewHolder holder) {
        if(items.get(position) instanceof CampaignHeader) {
            CampaignHeader header = (CampaignHeader) items.get(position);
            if(null != header) {
                CampaignHeaderVH vh = (CampaignHeaderVH) holder;
                vh.channelA.setText(header.getChannelA());
                vh.channelB.setText(header.getChannelB());
                vh.channelC.setText(header.getChannelC());

                if(!TextUtils.isEmpty(header.getCampaignPic())) {
                    GlideApp.with(mContext)
                            .load(header.getCampaignPic())
                            .error(R.drawable.ic_default_image)
                            .into(vh.mCampaignPic);
                }
            }
        }
    }

    public static class CampaignHeaderVH extends RecyclerView.ViewHolder {

        TextView channelA;
        TextView channelB;
        TextView channelC;
        ImageView mCampaignPic;


        public CampaignHeaderVH(View itemView) {
            super(itemView);
            channelA = (TextView) itemView.findViewById(R.id.channelA);
            channelB = (TextView) itemView.findViewById(R.id.channelB);
            channelC = (TextView) itemView.findViewById(R.id.channelC);
            mCampaignPic = (ImageView) itemView.findViewById(R.id.campaignPic);
        }
    }
}
