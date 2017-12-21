package com.kidoo.customer.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.R;
import com.kidoo.customer.adapter.MedalAdapter;
import com.kidoo.customer.adapter.itemDecoration.SpaceItemDecoration;
import com.kidoo.customer.bean.Customer;
import com.kidoo.customer.bean.InitData;
import com.kidoo.customer.bean.MedalBean;
import com.kidoo.customer.bean.UserDetailBean;
import com.kidoo.customer.interf.OnTabReselectListener;
import com.kidoo.customer.media.ImageGalleryActivity;
import com.kidoo.customer.mvp.contract.user.UseInfoContract;
import com.kidoo.customer.mvp.presenter.user.UserInfoPresenterImpl;
import com.kidoo.customer.ui.activity.setting.SettingActivity;
import com.kidoo.customer.ui.activity.user.MyMatchsListActivity;
import com.kidoo.customer.ui.activity.user.UserDetailActivity;
import com.kidoo.customer.ui.base.fragment.BaseMvpFragment;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.utils.TDevice;
import com.kidoo.customer.widget.MyEnterLayout;
import com.kidoo.customer.widget.glideimageview.GlideImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

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

    @BindView(R.id.user_name)
    TextView tvUserName;

    @BindView(R.id.portrait)
    GlideImageView ivPortrait;

    @BindView(R.id.garden)
    GlideImageView ivGerden;

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


    @BindView(R.id.medal_empty_hint)
    TextView tvMedalEmpty;

    @BindView(R.id.rv_medal_list)
    RecyclerView rvMedalList;

    @Inject
    UserInfoPresenterImpl mPresenter;

    private Customer customer;

    private MedalAdapter mMedalAdapter;
    private List<MedalBean> mMedalDatas = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.layout_main_tab_me;
    }


    @Override
    public void initWidget(View root) {
        super.initWidget(root);

        mMedalAdapter = new MedalAdapter(getActivity(), AppContext.context().getInitData().getQnDomain(),
                mMedalDatas);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(HORIZONTAL);
        rvMedalList.setLayoutManager(linearLayoutManager);
        rvMedalList.addItemDecoration(new SpaceItemDecoration(8));
        rvMedalList.setAdapter(mMedalAdapter);

    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        if (AccountHelper.isLogin()) {
            customer = AccountHelper.getUser();
            updateBaseInfo(customer);

            sendRequestData();
        }

        mMedalAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mMedalDatas.size() != 0) {
                    String[] picArray = new String[mMedalDatas.size()];
                    String baseUrl = AppContext.context().getInitData().getQnDomain();
                    for (int i = 0; i < mMedalDatas.size(); i++) {
                        picArray[i] = baseUrl + mMedalDatas.get(i).getPic();
                    }
                    ImageGalleryActivity.show(getActivity(), picArray, position);
                }
            }
        });
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

    @OnClick({R.id.userinfo_header, R.id.userinfo_item_campain, R.id.userinfo_item_team,
            R.id.userinfo_item_setting, R.id.userinfo_item_campain_manager,
            R.id.userinfo_item_challengerec, R.id.portrait})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userinfo_header:
                Intent intent = new Intent(getActivity(), UserDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.portrait:
                String poratait = AppContext.context().getInitData().getQnDomain() + customer.getPortrait();
                if (!TextUtils.isEmpty(poratait)) {
                    ImageGalleryActivity.show(getActivity(), poratait);
                }
                break;
            case R.id.userinfo_item_setting:
                Intent intent1 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent1);
                break;
            case R.id.userinfo_item_campain:
                Intent intent2 = new Intent(getActivity(), MyMatchsListActivity.class);
                startActivity(intent2);
                break;
            case R.id.userinfo_item_campain_manager:
                Intent intent3 = new Intent(getActivity(), MyMatchsListActivity.class);
                startActivity(intent3);
                break;
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void updateUserInfo(UserDetailBean detail) {
//        AccountHelper.updateUserCache(detail.getCustomer());

        updateBaseInfo(detail.getCustomer());
        updateMedal(detail.getMedalList());
    }

    private void sendRequestData() {
        if (TDevice.hasInternet() && AccountHelper.isLogin()) {
            if (customer != null) {
                mPresenter.queryUserDetail(customer.getMobile());
            }
        }
    }

    private void updateBaseInfo(Customer customer) {
        InitData initData = AppContext.context().getInitData();

        tvUserName.setText(TextUtils.isEmpty(customer.getNickName()) ? customer.getCustomId() : customer.getNickName());
        if (customer.getSex() == 1) {
            ivGerden.setBackgroundResource(R.drawable.w_info_gender_f_n);
        } else {
            ivGerden.setBackgroundResource(R.drawable.w_info_gender_m_p);
        }

        if (!TextUtils.isEmpty(customer.getSign())) {
            tvSlogn.setText(customer.getSign());
        }

        updatePortrait(initData.getQnDomain() + customer.getPortrait());

    }

    private void updatePortrait(String url) {
        LogUtils.i(url);

        ivPortrait.loadImage(url, R.drawable.def_user);

        getImgLoader().load(url)
                .centerCrop()
                .placeholder(R.drawable.def_user)
                .error(R.drawable.def_user)
                .into(ivPortrait);
        getImgLoader().load(url)
                .centerCrop()
                .placeholder(R.drawable.def_user)
                .error(R.drawable.def_user)
                .transform(new BlurTransformation(23, 4))
                .into(ivUsefinfoBg);
    }

    private void updateMedal(List<MedalBean> beanList) {
        if (beanList == null || beanList.size() == 0) {
            tvMedalEmpty.setVisibility(View.VISIBLE);
            rvMedalList.setVisibility(View.GONE);
        } else {
            tvMedalEmpty.setVisibility(View.GONE);
            rvMedalList.setVisibility(View.VISIBLE);
        }
        mMedalDatas.addAll(beanList);
        mMedalAdapter.notifyDataSetChanged();
    }
}
