package com.kidoo.customer.adapter.myCampaignAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kidoo.customer.R;
import com.kidoo.customer.adapter.multitype.AdapterDelegate;
import com.kidoo.customer.adapter.myCampaignAdapter.model.CampaigBaseInfo;
import com.kidoo.customer.adapter.myCampaignAdapter.model.CampaignHeader;
import com.kidoo.customer.adapter.myCampaignAdapter.model.CampaignSession;
import com.kidoo.customer.adapter.myCampaignAdapter.model.CampaignTeam;
import com.kidoo.customer.adapter.myCampaignAdapter.model.ICampaignMode;
import com.kidoo.customer.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-10-19
 * Time: 10:47
 * Company: zx
 * Description:
 * FIXME
 */


public class MyCampaignAdapter extends RecyclerView.Adapter {

    private List<ICampaignMode> mItems;
    private List<AdapterDelegate> mDelegates;
    protected Context mContext;
    protected LayoutInflater mInflater;


    public final static int INFO_HEADER = 0;
    public final static int BASE_INFO = 1;
    public final static int SESSION = 2;
    public final static int TEAM = 3;
    public final static int BUTTON = 4;


    public MyCampaignAdapter(Context context) {
        this.mItems = new ArrayList<>();
        this.mDelegates = new ArrayList<>();
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getItemViewType(int position) {
        AdapterDelegate delegate = getDelegateItem(position);

        if (delegate != null) {
            if (delegate instanceof CampaignInfoHeaderDelegate) {
                LogUtils.w("CampaignInfoHeaderDelegate");
                return INFO_HEADER;
            } else if (delegate instanceof CampaignBaseInfoDelegate) {
                LogUtils.w("CampaignBaseInfoDelegate");
                return BASE_INFO;
            } else if (delegate instanceof CampaignSessionInfoDelegate) {
                LogUtils.w("CampaignBaseInfoDelegate");
                return SESSION;
            } else if (delegate instanceof CampaignTeamInfoDelegate) {
                LogUtils.w("CampaignTeamInfoDelegate");
                return TEAM;
            }
        }

        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtils.w("oncreateviewHolder: " + viewType);
        switch (viewType) {
            case INFO_HEADER:
                return new CampaignInfoHeaderDelegate.CampaignHeaderVH(mInflater.inflate(R.layout.item_campaign_header, parent, false));
            case BASE_INFO:
                return new CampaignBaseInfoDelegate.CampaignBaseInfoVH(mInflater.inflate(R.layout.item_campaign_info, parent, false));
            case SESSION:
                return new CampaignSessionInfoDelegate.CampaignSessionVH(mInflater.inflate(R.layout.item_campaign_session_info, parent, false));
            case TEAM:
                return new CampaignTeamInfoDelegate.CampaignTeamVH(mInflater.inflate(R.layout.item_campaign_team, parent, false));

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AdapterDelegate delegate = getDelegateItem(position);
        if (delegate != null) {
            delegate.onBindViewHolder(mItems, position, holder);
        }
    }

    @Override
    public int getItemCount() {
        return mDelegates.size();
    }

    protected int getIndex(int position) {
        return position;
    }

    public int getCount() {
        return mItems.size();
    }


    public final List<ICampaignMode> getItems() {
        return mItems;
    }


    public void addAll(List<ICampaignMode> items) {
        if (items != null) {
            this.mItems.addAll(items);
            addAllDelegate(items);
            notifyItemRangeInserted(this.mItems.size(), items.size());
        }

    }

    public void addItem(ICampaignMode item) {
        if (item != null) {
            this.mItems.add(item);
            notifyItemChanged(mItems.size());
        }
    }


    public void addItem(int position, ICampaignMode item) {
        if (item != null) {
            this.mItems.add(getIndex(position), item);
            notifyItemInserted(position);
        }
    }

    public void replaceItem(int position, ICampaignMode item) {
        if (item != null) {
            this.mItems.set(getIndex(position), item);
            notifyItemChanged(position);
        }
    }

    public void updateItem(int position) {
        if (getItemCount() > position) {
            notifyItemChanged(position);
        }
    }


    public final void removeItem(ICampaignMode item) {
        if (this.mItems.contains(item)) {
            int position = mItems.indexOf(item);
            this.mItems.remove(item);
            notifyItemRemoved(position);
        }
    }

    public final void removeItem(int position) {
        if (this.getItemCount() > position) {
            this.mItems.remove(getIndex(position));
            notifyItemRemoved(position);
        }
    }

    public final ICampaignMode getItem(int position) {
        int p = getIndex(position);
        if (p < 0 || p >= mItems.size())
            return null;
        return mItems.get(getIndex(position));
    }

    public void resetItem(List<ICampaignMode> items) {
        if (items != null) {
            clear();
            addAll(items);

            resetDelegate(items);
        }
    }

    public final void clear() {
        this.mItems.clear();
        notifyDataSetChanged();
    }

    private void addAllDelegate(List<ICampaignMode> items) {
        if (items != null && items.size() != 0) {

            for (int i = 0; i < items.size(); i++) {
                if (items.get(i) instanceof CampaignHeader) {
                    mDelegates.add(new CampaignInfoHeaderDelegate(mContext));
                } else if (items.get(i) instanceof CampaigBaseInfo) {
                    mDelegates.add(new CampaignBaseInfoDelegate(mContext));
                } else if (items.get(i) instanceof CampaignSession) {
                    mDelegates.add(new CampaignSessionInfoDelegate(mContext));
                } else if(items.get(i) instanceof CampaignTeam) {
                    mDelegates.add(new CampaignTeamInfoDelegate(mContext));
                }
            }

        }
    }

    public final AdapterDelegate getDelegateItem(int position) {
        if (position < 0 || position >= mDelegates.size())
            return null;
        return mDelegates.get(position);
    }

    private void resetDelegate(List<ICampaignMode> items) {
        clearDelegate();
        addAllDelegate(items);
    }

    private void clearDelegate() {
        this.mDelegates.clear();
    }

}
