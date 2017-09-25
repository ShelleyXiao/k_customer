package com.kidoo.customer.media;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.kidoo.customer.R;
import com.kidoo.customer.media.config.SelectOption;
import com.kidoo.customer.ui.base.fragment.BaseFragment;
import com.kidoo.customer.widget.EmptyLayout;

import butterknife.Bind;

/**
 * User: ShaudXiao
 * Date: 2017-09-25
 * Time: 19:17
 * Company: zx
 * Description:
 * FIXME
 */


public class SelectFragment extends BaseFragment {

    @Bind(R.id.rv_image)
    RecyclerView mContentView;
    @Bind(R.id.btn_title_select)
    Button mSelectFolderView;
    @Bind(R.id.iv_title_select)
    ImageView mSelectFolderIcon;
    @Bind(R.id.toolbar)
    View mToolbar;
    @Bind(R.id.btn_done)
    Button mDoneView;
    @Bind(R.id.btn_preview)
    Button mPreviewView;

    @Bind(R.id.error_layout)
    EmptyLayout mErrorLayout;

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
