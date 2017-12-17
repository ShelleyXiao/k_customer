package com.kidoo.customer.adapter.team;

import android.content.Context;

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

public class QueryTeamAdapter extends BaseQuickAdapter<TeamBean, BaseViewHolder> {

    private Context mContext;


    private String baseUrl;

    public QueryTeamAdapter(Context context, List<TeamBean> datas) {
        super(R.layout.item_team_list_layout, datas);
        this.mContext = context;

        this.baseUrl = AppContext.context().getInitData().getQnDomain();
    }


    @Override
    protected void convert(BaseViewHolder helper, TeamBean item) {

        helper.setText(R.id.tv_team_name, item.getName());
        GlideImageView ivMathPic = (GlideImageView) helper.getView(R.id.iv_match_pic);
        ivMathPic.loadImage(baseUrl + item.getIcon(), R.color.placeholder);


    }
}
