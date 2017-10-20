package com.kidoo.customer.adapter.myCampaignAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kidoo.customer.GlideApp;
import com.kidoo.customer.R;
import com.kidoo.customer.adapter.multitype.AdapterDelegate;
import com.kidoo.customer.adapter.myCampaignAdapter.model.CampaignTeam;
import com.kidoo.customer.api.ComParamContact;
import com.kidoo.customer.widget.CircleImageView;

import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-10-19
 * Time: 15:16
 * Company: zx
 * Description:
 * FIXME
 */


public class CampaignTeamInfoDelegate implements AdapterDelegate {

    private Context mContext;
    private LayoutInflater mInflater;

    public CampaignTeamInfoDelegate(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

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
        if (items.get(position) instanceof CampaignTeam) {
            CampaignTeam team = (CampaignTeam) items.get(position);
            CampaignTeamVH vh = (CampaignTeamVH) holder;
            if (TextUtils.isEmpty(team.getTitle())) {
                vh.mTeamTitle.setVisibility(View.GONE);
            } else {
                vh.mTeamTitle.setText(team.getTitle());
            }
            if (team.getTeambase() != null) {
                vh.mTeamName.setText(team.getTeambase().getName());
                GlideApp.with(mContext).load(ComParamContact.QnToken.TEST_BASE + team.getTeambase().getIcon())
                        .error(R.drawable.def_user)
                        .into(vh.mTeamLogo);
            }

            if (team.getTeamScroe() != null) {
                vh.mTeamScore.setText(team.getTeamScroe().getScore() + "");
            }

        }
    }

    public static class CampaignTeamVH extends RecyclerView.ViewHolder {

        private TextView mTeamTitle;
        private CircleImageView mTeamLogo;
        private TextView mTeamName;
        private TextView mTeamScore;

        public CampaignTeamVH(View itemView) {
            super(itemView);

            mTeamTitle = (TextView) itemView.findViewById(R.id.campaign_title);
            mTeamName = (TextView) itemView.findViewById(R.id.team_name);
            mTeamScore = (TextView) itemView.findViewById(R.id.team_scroe);
            mTeamLogo = (CircleImageView) itemView.findViewById(R.id.team_logo);
        }
    }
}
