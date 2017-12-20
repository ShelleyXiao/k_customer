package com.kidoo.customer.adapter.competion;

import android.content.Context;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.TeamBean;
import com.kidoo.customer.widget.glideimageview.GlideImageView;

import java.util.List;

/**
 * Created by ShaudXiao on 2017/12/17.
 */

public class CompetionEnrollSituationAdapter extends BaseQuickAdapter<TeamBean, BaseViewHolder> {

    private Context mContext;


    private String baseUrl;

    public CompetionEnrollSituationAdapter(Context context, List<TeamBean> datas) {
        super(R.layout.item_enroll_situaion_team_list_layout, datas);
        this.mContext = context;

        this.baseUrl = AppContext.context().getInitData().getQnDomain();
    }


    @Override
    protected void convert(BaseViewHolder helper, TeamBean item) {

        helper.setText(R.id.tv_team_name, item.getName());
        GlideImageView ivTeamPic = (GlideImageView) helper.getView(R.id.iv_team_pic);
        ivTeamPic.loadImage(baseUrl + item.getIcon(), R.color.placeholder);
        TextView tvEnrollSituation = (TextView) helper.getView(R.id.tv_enroll_situation);
        if (item.getState() == 0) {
            tvEnrollSituation.setText(R.string.competion_accpet);
            tvEnrollSituation.setBackground(mContext.getResources().getDrawable(R.drawable.btn_invalid_bg));
        } else {
            tvEnrollSituation.setText(R.string.competion_already);
            tvEnrollSituation.setBackground(mContext.getResources().getDrawable(R.drawable.btn_normal_bg));
        }

    }
}
