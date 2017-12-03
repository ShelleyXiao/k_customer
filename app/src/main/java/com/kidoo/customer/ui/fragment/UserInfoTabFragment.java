package com.kidoo.customer.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.Customer;
import com.kidoo.customer.bean.MedalBean;
import com.kidoo.customer.bean.UserDetailBean;
import com.kidoo.customer.interf.OnTabReselectListener;
import com.kidoo.customer.mvp.contract.UseInfoContract;
import com.kidoo.customer.mvp.presenter.UserInfoPresenterImpl;
import com.kidoo.customer.ui.base.fragment.BaseMvpFragment;
import com.kidoo.customer.utils.TDevice;
import com.kidoo.customer.widget.MyEnterLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * description:
 * autour: ShaudXiao
 * date: 2017/12/2
 * update: 2017/12/2
 * version:
 */

public class UserInfoTabFragment extends BaseMvpFragment<UserInfoPresenterImpl> implements UseInfoContract.View,
        OnTabReselectListener,
        View.OnClickListener {

    @BindView(R.id.userinfo_header)
    View userInfoHeaderView;

    @BindView(R.id.userinfo_bg)
    ImageView ivUsefinfoBg;

    @BindView(R.id.portrait)
    ImageView ivPortrait;

    @BindView(R.id.garden)
    ImageView ivGerden;

    @BindView(R.id.slogn)
    TextView tvSlogn;

    @BindView(R.id.userinfo_item_campain)
    MyEnterLayout itemCampainView;

    @BindView(R.id.userinfo_item_challengerec)
    MyEnterLayout itemChallengerecView;

    @BindView(R.id.userinfo_item_team)
    MyEnterLayout itemTeamView;

    @BindView(R.id.userinfo_item_setting)
    MyEnterLayout itemSettingView;


    @Inject
    UserInfoPresenterImpl mPresenter;

    private Customer customer;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_main_tab_me;
    }

    @Override
    protected void initData() {
        super.initData();

        if (AccountHelper.isLogin()) {
            Customer customer = AccountHelper.getUser();
            updateBaseInfo(customer);


            sendRequestData();
        }
    }

    @Override
    public void onTabReselect() {
        if (AccountHelper.isLogin()) {
            sendRequestData();
        }
    }


    @Override
    protected UserInfoPresenterImpl initInjector() {

        mFragmentComponent.inject(this);

        return mPresenter;
    }

    @OnClick({R.id.userinfo_header, R.id.userinfo_item_campain, R.id.userinfo_item_team, R.id.userinfo_item_setting,
            R.id.userinfo_item_challengerec, R.id.portrait})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userinfo_header:
                break;
            case R.id.portrait:
                break;
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void updateUserInfo(UserDetailBean detail) {
        AccountHelper.updateUserCache(detail.getCustomer());

        updateBaseInfo(detail.getCustomer());
        updateMedal(detail.getMedalList());
    }

    private void sendRequestData() {
        if (TDevice.hasInternet() && AccountHelper.isLogin()) {
            mPresenter.queryUserDetail(customer.getMobile());
        }
    }

    private void updateBaseInfo(Customer customer) {
        updatePortrait(customer.getPortrait());
    }

    private void updatePortrait(String url) {

    }

    private void updateMedal(List<MedalBean> beanList) {

    }
}
