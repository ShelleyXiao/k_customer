package com.kidoo.customer.ui.fragment;

import com.kidoo.customer.R;
import com.kidoo.customer.ui.base.fragment.BaseTitleFragment;

/**
 * Created by Shelley on 2017/9/17.
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
