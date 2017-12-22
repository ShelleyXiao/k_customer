package com.kidoo.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.MatchBean;
import com.kidoo.customer.widget.glideimageview.GlideImageView;

import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-12-14
 * Time: 15:05
 * Company: zx
 * Description:
 * FIXME
 */


public class UserMatchsManagerAdapter extends BaseQuickAdapter<MatchBean, BaseViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;


    private String baseUrl;

    public UserMatchsManagerAdapter(Context context, List<MatchBean> datas) {
        super(R.layout.item_matchs_list_item, datas);
        this.mContext = context;

        this.baseUrl = AppContext.context().getInitData().getQnDomain();
    }


    @Override
    protected void convert(BaseViewHolder helper, MatchBean item) {

        helper.setText(R.id.tv_match_name, item.getName())
                .setText(R.id.tv_match_status,
                        mContext.getResources()
                                .getStringArray(R.array.match_status)[item.getState()]);
        GlideImageView ivMathPic = (GlideImageView) helper.getView(R.id.iv_match_pic);
        ivMathPic.loadImage(baseUrl + item.getPicMini(), R.color.placeholder);


    }


}
