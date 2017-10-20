package com.kidoo.customer.adapter.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-10-19
 * Time: 10:39
 * Company: zx
 * Description:
 * FIXME
 */


public class AdapterViewholderManager {



    private List<AdapterDelegate> mDelegates = new ArrayList<>();

    public void addDelegage(AdapterDelegate delegate) {
        mDelegates.add(delegate);
    }

    public int getItemViewType(List items, int position) {
        if(mDelegates != null) {
            AdapterDelegate delegate = mDelegates.get(position);
            if(null != delegate) {
                return delegate.getItemViewType(items, position);
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    @NonNull public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        AdapterDelegate delegate = getDelegateFromViewType(viewType);
//        if(null != delegate) {
//            return delegate.onCreateViewHolder(parent);
//        }

        return  null;
    }

    public void onBindViewHolder(@NonNull List items, int position, @NonNull RecyclerView.ViewHolder viewHolder) {
        AdapterDelegate delegate = getDelegate(position);
        if (delegate != null) {
            delegate.onBindViewHolder(items, position, viewHolder);
        }
    }

    public AdapterDelegate getDelegate(int position) {
        return mDelegates.get(position);
    }

    public AdapterDelegate getDelegateFromViewType(int viewType) {
        return mDelegates.get(viewType);
    }

}
