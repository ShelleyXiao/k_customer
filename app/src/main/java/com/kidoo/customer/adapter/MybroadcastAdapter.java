package com.kidoo.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kidoo.customer.GlideApp;
import com.kidoo.customer.GlideRequests;
import com.kidoo.customer.R;
import com.kidoo.customer.mvp.model.Broadcast;
import com.kidoo.customer.ui.base.adapter.BaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/** 
 * description: 我的广播adapter
 * autour: ShaudXiao
 * date: 2017/10/15  
 * update: 2017/10/15
 * version: 
*/


public class MybroadcastAdapter extends BaseRecyclerAdapter<Broadcast> {


    private GlideRequests mGlideRequests;
    private String[] typeStrArr;
    private String[] statusStrArr;

    public MybroadcastAdapter(Context context, int mode) {
        super(context, mode);
        mGlideRequests = GlideApp.with(context);

        setState(BaseRecyclerAdapter.STATE_LOADING, false);
        typeStrArr = context.getResources().getStringArray(R.array.broadcast_type_name);
        statusStrArr = context.getResources().getStringArray(R.array.broadcast_status_str);

    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new MyBroadcastViewholder(mInflater.inflate(R.layout.layout_my_broadcast_item, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, Broadcast item, int position) {
        MyBroadcastViewholder viewHolder = (MyBroadcastViewholder) holder;

        viewHolder.broadcast_type.setText("(" + typeStrArr[item.getType()] + ")");
        viewHolder.broadcastName.setText(item.getName());
        viewHolder.broadcastStatus.setText(statusStrArr[item.getStatus()]);

    }

    public class MyBroadcastViewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.broadcast_feature_pic)
        ImageView broadcastFeature;

        @BindView(R.id.broadcast_type)
        TextView broadcast_type;

        @BindView(R.id.broadcast_name)
        TextView broadcastName;

        @BindView(R.id.broadcast_status)
        TextView broadcastStatus;

        public MyBroadcastViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
