package com.kidoo.customer.ui.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kidoo.customer.R;

/**
 * User: ShaudXiao
 * Date: 2017-10-12
 * Time: 19:17
 * Company: zx
 * Description:
 * FIXME
 */


public abstract class BaseToolbarFragment extends BaseFragment {

    private  Toolbar mToolbar;

    @Override
    public void onCreate(Bundle saveBundle) {
        super.onCreate(saveBundle);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar = (Toolbar) view.findViewById(R.id.id_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

    }
}
