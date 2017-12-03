package com.kidoo.customer.ui.fragment;

import com.kidoo.customer.R;
import com.kidoo.customer.ui.base.fragment.BaseTitleFragment;

/** 
 * description:
 * autour: ShaudXiao
 * date: 2017/12/2  
 * update: 2017/12/2
 * version: 
*/
public class ExporeTabFragment extends BaseTitleFragment {

    @Override
    protected int getContentLayoutId() {
        return  R.layout.layout_main_tab_explore;
    }

    @Override
    protected int getTitleRes() {
        return R.string.main_tab_name_explore;
    }

}
