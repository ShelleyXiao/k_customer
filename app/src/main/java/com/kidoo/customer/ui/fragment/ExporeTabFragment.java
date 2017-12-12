package com.kidoo.customer.ui.fragment;

import com.kidoo.customer.R;
import com.kidoo.customer.ui.base.fragment.BaseTitleFragment;
import com.kidoo.customer.widget.MyEnterLayout;

import butterknife.BindView;
import butterknife.OnClick;

/** 
 * description:
 * autour: ShaudXiao
 * date: 2017/12/2  
 * update: 2017/12/2
 * version: 
*/
public class ExporeTabFragment extends BaseTitleFragment {

    @BindView(R.id.item_ranking)
    MyEnterLayout mlRankingView;

    @Override
    protected int getContentLayoutId() {
        return  R.layout.layout_main_tab_explore;
    }

    @Override
    protected int getTitleRes() {
        return R.string.main_tab_name_explore;
    }

    @OnClick(R.id.item_ranking)
    public void rankingViewClick() {

    }

}
