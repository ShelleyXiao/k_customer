package com.kidoo.customer.ui.fragment.channelCampaign;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.MatchBean;
import com.kidoo.customer.ui.base.fragment.BaseFragment;
import com.kidoo.customer.utils.LogUtils;

/**
 * User: ShaudXiao
 * Date: 2017-12-14
 * Time: 19:32
 * Company: zx
 * Description:
 * FIXME
 */


public class CampaignBaseInfoFragment extends BaseFragment {

    private MatchBean mMatchBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        mMatchBean = (MatchBean) bundle.getSerializable(Constants.MATCH_BEAN_DATA_KEY);
        LogUtils.i(mMatchBean.toString());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_campaign_base_info_layout;
    }
}
