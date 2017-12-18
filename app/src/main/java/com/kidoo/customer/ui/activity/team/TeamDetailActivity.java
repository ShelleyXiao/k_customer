package com.kidoo.customer.ui.activity.team;

import android.os.Bundle;

import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.TeamBean;
import com.kidoo.customer.mvp.contract.team.TeamDetailContract;
import com.kidoo.customer.mvp.presenter.team.TeamDetailPresenterImpl;
import com.kidoo.customer.ui.base.activities.BaseBackMvpActivity;

import javax.inject.Inject;

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

    @Inject
    TeamDetailPresenterImpl mPresenter;

    private int teamId = -1;

    @Override
    protected int getContentView() {
        return R.layout.activity_team_detail_layout;
    }

    @Override
    protected boolean initBundle(Bundle bundle) {
        if(bundle != null) {
            teamId = bundle.getInt(Constants.TEAM_ID_KEY, -1);
        }

        return super.initBundle(bundle);
    }

    @Override
    public void updateTeamDetail(TeamBean teamBean) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    protected TeamDetailPresenterImpl initInjector() {
        mActivityComponent.inject(this);
        return mPresenter;
    }


}
