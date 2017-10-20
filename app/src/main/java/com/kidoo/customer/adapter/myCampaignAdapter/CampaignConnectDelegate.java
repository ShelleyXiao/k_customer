package com.kidoo.customer.adapter.myCampaignAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.kidoo.customer.adapter.multitype.AdapterDelegate;

import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-10-19
 * Time: 15:16
 * Company: zx
 * Description:
 * FIXME
 */


public class CampaignConnectDelegate implements AdapterDelegate {
    @Override
    public int getItemViewType(@NonNull List items, int position) {
        return items.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull List items, int position, @NonNull RecyclerView.ViewHolder holder) {

    }
}
