package com.kidoo.customer.ui.activity.team;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.adapter.MedalAdapter;
import com.kidoo.customer.adapter.itemDecoration.SpaceItemDecoration;
import com.kidoo.customer.adapter.team.TeamMemberAdapter;
import com.kidoo.customer.bean.ChannelA;
import com.kidoo.customer.bean.ChannelB;
import com.kidoo.customer.bean.ChannelC;
import com.kidoo.customer.bean.Customer;
import com.kidoo.customer.bean.MedalBean;
import com.kidoo.customer.bean.TeamBean;
import com.kidoo.customer.bean.TeamDetailResult;
import com.kidoo.customer.bean.TeamMemberBen;
import com.kidoo.customer.mvp.contract.team.TeamDetailContract;
import com.kidoo.customer.mvp.presenter.team.TeamDetailPresenterImpl;
import com.kidoo.customer.ui.base.activities.BaseBackMvpActivity;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.dialog.CommonDialog;
import com.kidoo.customer.widget.glideimageview.GlideImageView;
import com.kidoo.customer.widget.recylerview.SquareListDivider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

/**
 * User: ShaudXiao
 * Date: 2017-12-18
 * Time: 17:17
 * Company: zx
 * Description:
 * FIXME
 */


public class TeamDetailActivity extends BaseBackMvpActivity<TeamDetailPresenterImpl>
        implements TeamDetailContract.View {

    @BindView(R.id.channel_a)
    TextView tvChannelAName;

    @BindView(R.id.channel_b)
    TextView tvChannelBName;

    @BindView(R.id.channel_c)
    TextView tvChannelCName;

    @BindView(R.id.iv_team_logo)
    GlideImageView ivTeamLogo;

    @BindView(R.id.tv_team_name)
    TextView tvTeanName;

    @BindView(R.id.rv_team_medal)
    RecyclerView rvMedalList;

    @BindView(R.id.tv_team_medal_empty)
    TextView tvTeamMedalEmpty;

    @BindView(R.id.rv_team_member)
    RecyclerView rvMemberList;

    @BindView(R.id.lv_apply_member)
    LinearLayout lvMemberApply;

    @BindView(R.id.rv_team_apply_member)
    RecyclerView rvMemberApplyList;

    @BindView(R.id.bt_join_apply)
    Button btJoinOrApply;

    @BindView(R.id.bt_modify_team)
    Button btModifyTeam;

    @Inject
    TeamDetailPresenterImpl mPresenter;

    private List<Customer> mTeamMemberInfoList = new ArrayList<>();
    private List<TeamMemberBen> mTeamApplyMemberList = new ArrayList<>();
    private List<MedalBean> mMedalList = new ArrayList<>();

    private List<Integer> mCustomerIds = new ArrayList<>();

    private TeamMemberAdapter mTeamMemberAdapter;
    private MedalAdapter mMedalAdapter;

    private TeamBean mCurrentTeam;

    private ChannelC mChannelC;

    private int selectChannalAIndex = 0;
    private int selectChannalBIndex = 0;
    private int selectChannalCIndex = 0;

    private String baseUrl;

    private boolean isInTeam = false;

    public static void showTeamDetail(Context context, int selectA, int selectB, int selectC
            , TeamBean teamBean) {
        Intent intent = new Intent(context, TeamDetailActivity.class);
        Bundle bundle = new Bundle();

        bundle.putInt(Constants.SELECT_A_INDEX, selectA);
        bundle.putInt(Constants.SELECT_B_INDEX, selectB);
        bundle.putInt(Constants.SELECT_C_INDEX, selectC);
        bundle.putSerializable(Constants.TEAM_ID_KEY, teamBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_team_detail_layout;
    }

    @Override
    protected boolean initBundle(Bundle bundle) {
        if (bundle != null) {

            selectChannalAIndex = bundle.getInt(Constants.SELECT_A_INDEX);
            selectChannalBIndex = bundle.getInt(Constants.SELECT_B_INDEX);
            selectChannalCIndex = bundle.getInt(Constants.SELECT_C_INDEX);

            mCurrentTeam = (TeamBean) bundle.getSerializable(Constants.TEAM_ID_KEY);
        }

        return super.initBundle(bundle);
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        baseUrl = AppContext.context().getInitData().getQnDomain();

        String channelAName = null;
        String channelBName = null;
        String channelCName = null;


        List<ChannelA> channelAList = AppContext.context().getgChannelAList();
        if (channelAList != null) {

            ChannelA channelA = channelAList.get(selectChannalAIndex);
            LogUtils.i(selectChannalAIndex + " " + channelA.getName());
            channelAName = channelA.getName();
            ChannelB channelB = channelA.getChannelBList().get(selectChannalBIndex);
            channelBName = channelB.getName();
            mChannelC = channelB.getChannelCList().get(selectChannalCIndex);
            channelCName = mChannelC.getName();
        }

        tvChannelAName.setText(channelAName);
        tvChannelBName.setText(channelBName);
        tvChannelCName.setText(channelCName);

        tvTeanName.setText(mCurrentTeam.getName());
        ivTeamLogo.loadImage(baseUrl + mCurrentTeam.getIcon(), R.color.placeholder);

        mTeamMemberAdapter = new TeamMemberAdapter(this, baseUrl, mCurrentTeam.getCaptainId(), mTeamMemberInfoList);
        mMedalAdapter = new MedalAdapter(this, baseUrl, mMedalList);

        rvMemberList.setLayoutManager(new LinearLayoutManager(this));
        rvMemberList.setHasFixedSize(true);
        rvMemberList.addItemDecoration(new SquareListDivider(this,
                HORIZONTAL, 2,
                getResources().getColor(R.color.list_divider_color)));
        rvMemberList.isNestedScrollingEnabled();
        rvMemberList.setAdapter(mTeamMemberAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(HORIZONTAL);
        rvMedalList.setLayoutManager(linearLayoutManager);
        rvMedalList.addItemDecoration(new SpaceItemDecoration(8));
        rvMedalList.setAdapter(mMedalAdapter);

        rvMemberApplyList.setLayoutManager(new LinearLayoutManager(this));
        rvMemberApplyList.setHasFixedSize(true);
        rvMemberApplyList.addItemDecoration(new SquareListDivider(this,
                HORIZONTAL, 2,
                getResources().getColor(R.color.list_divider_color)));
        rvMemberApplyList.isNestedScrollingEnabled();

        rvMemberApplyList.setAdapter(mTeamMemberAdapter);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.doQueryTeamDetail(String.valueOf(mCurrentTeam.getId()));
    }

    @Override
    protected TeamDetailPresenterImpl initInjector() {
        mActivityComponent.inject(this);
        return mPresenter;
    }


    @Override
    public void updateTeamDetail(TeamDetailResult teamBean) {
        if (teamBean == null) {
            return;
        }
        LogUtils.i(teamBean.getTeam().toString());
        List<MedalBean> medalBeanList = teamBean.getMedalList();
        if (medalBeanList != null && medalBeanList.size() > 0) {
            mMedalList.addAll(medalBeanList);
            mMedalAdapter.notifyDataSetChanged();
            rvMedalList.setVisibility(View.VISIBLE);
            tvTeamMedalEmpty.setVisibility(View.GONE);
        }

        List<TeamMemberBen> memberBens = teamBean.getTeam().getMemberList();
        String ids = setCustomerIds(memberBens);
        if (!TextUtils.isEmpty(ids)) {
            mPresenter.doQueryMemberInfo(ids);
        }

        List<TeamMemberBen> applyTeamMembers = teamBean.getApplyMemberList();
        if (applyTeamMembers == null || applyTeamMembers.size() == 0) {
            lvMemberApply.setVisibility(View.GONE);
        }
        mTeamMemberAdapter.notifyDataSetChanged();

        long userId = AccountHelper.getUserId();
        if (teamBean.getTeam().getCaptainId() == userId) {
            btModifyTeam.setVisibility(View.VISIBLE);
        }

        for (TeamMemberBen bean : memberBens) {
            if (bean.getCustomerId() == userId) {
                isInTeam = true;
                break;
            }
        }
        LogUtils.i(isInTeam);
        if (isInTeam) {
            btJoinOrApply.setText(R.string.team_member_quit);
            btJoinOrApply.setBackground(getResources().getDrawable(R.drawable.btn_myjoin_bg));
        } else {
            btJoinOrApply.setText(R.string.team_join_btn);
        }

    }

    @Override
    public void updateMemberInfo(List<Customer> memberInfos) {
        LogUtils.i(memberInfos.size());
        mTeamMemberInfoList.addAll(memberInfos);
        mTeamMemberAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void joinTeamResult(boolean isSuccess, String errorMsg) {
        dismissLoadingDialog();
        if(isSuccess) {
            CommonDialog dialog = new CommonDialog(this);
            dialog.setTitle(null);
            dialog.setMessage(getString(R.string.team_join_success_msg));
            dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {
            CommonDialog dialog = new CommonDialog(this);
            dialog.setTitle(null);
            dialog.setMessage(errorMsg);
            dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    }

    @Override
    public void quitTeamResult(boolean isSuccess, String errorMsg) {
        dismissLoadingDialog();
        if(isSuccess) {
            CommonDialog dialog = new CommonDialog(this);
            dialog.setTitle(null);
            dialog.setMessage(getString(R.string.team_quit_success_msg));
            dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {
            CommonDialog dialog = new CommonDialog(this);
            dialog.setTitle(null);
            dialog.setMessage(errorMsg);
            dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    @OnClick({R.id.bt_join_apply, R.id.bt_modify_team})
    public void viewClick(View view) {
        switch (view.getId()) {
            case R.id.bt_join_apply:
                if(isInTeam) {
                    CommonDialog dialog = new CommonDialog(this);
                    dialog.setTitle(null);
                    dialog.setMessage(getString(R.string.team_quit_dailog_msg));
                    dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showLoadingDialog("");
                            mPresenter.doQuitTeam(mCurrentTeam.getId()+"");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {
                    CommonDialog dialog = new CommonDialog(this);
                    dialog.setTitle(null);
                    dialog.setMessage(getString(R.string.team_join_dailog_msg));
                    dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showLoadingDialog("");
                            mPresenter.doJoinTeam(mCurrentTeam.getId()+"");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                break;
            case R.id.bt_modify_team:
                Intent intent = new Intent(this, TeamDetailModifyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.TEAM_ID_KEY, mCurrentTeam);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    private String setCustomerIds(List<TeamMemberBen> datas) {
        StringBuilder builder = new StringBuilder();
        for (TeamMemberBen bean : datas) {
            builder.append("@" + bean.getCustomerId());
        }

        return builder.toString();
    }

}
