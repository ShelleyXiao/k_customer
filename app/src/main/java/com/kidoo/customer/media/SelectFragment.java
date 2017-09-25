package com.kidoo.customer.media;

import com.kidoo.customer.media.config.SelectOption;
import com.kidoo.customer.ui.base.fragment.BaseFragment;

/**
 * User: ShaudXiao
 * Date: 2017-09-25
 * Time: 19:17
 * Company: zx
 * Description:
 * FIXME
 */


public class SelectFragment extends BaseFragment {

    private static SelectOption mOption;

    public static SelectFragment newInstance(SelectOption options) {
        mOption = options;
        return new SelectFragment();
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }


}
