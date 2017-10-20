package com.kidoo.customer.adapter.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-10-19
 * Time: 10:40
 * Company: zx
 * Description:
 * FIXME
 */


public interface AdapterDelegate {
    int getItemViewType(@NonNull List items, int position);
    @NonNull RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);
    void onBindViewHolder(@NonNull List items, int position, @NonNull RecyclerView.ViewHolder holder);
}
