package com.kidoo.customer.ui.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * User: ShaudXiao
 * Date: 2017-10-19
 * Time: 10:56
 * Company: zx
 * Description:
 * FIXME
 */


public interface OnLoadingHeaderCallBack {
    RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent);

    void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position);
}
