package com.kidoo.customer.adapter.team;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.Customer;
import com.kidoo.customer.widget.glideimageview.GlideImageView;

import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-12-19
 * Time: 10:27
 * Company: zx
 * Description:
 * FIXME
 */


public class TeamMemberAdapter extends BaseQuickAdapter<Customer, BaseViewHolder> {

    private long userId = 0;
    private String baseUrl;
    private int captainID;

    public TeamMemberAdapter(Context context, String baseUrl, int captainId, List<Customer> datas) {
        super(R.layout.item_team_member_list, datas);
        this.mContext = context;
        this.baseUrl = baseUrl;
        this.captainID = captainId;
    }

    @Override
    protected void convert(BaseViewHolder helper, Customer item) {
        if (item.getId() == captainID) {
            helper.getView(R.id.tv_member_name).setVisibility(View.GONE);
            helper.getView(R.id.tv_member_captain).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_member_captain_name, item.getNickName());
        } else {
            helper.getView(R.id.tv_member_name).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_member_captain).setVisibility(View.GONE);
            helper.setText(R.id.tv_member_name, String.valueOf(item.getNickName()));
        }

        GlideImageView ivPortrait = (GlideImageView) helper.getView(R.id.iv_member_pic);
        ivPortrait.loadImage(baseUrl + item.getPortrait(),  R.drawable.def_user);
    }
}
