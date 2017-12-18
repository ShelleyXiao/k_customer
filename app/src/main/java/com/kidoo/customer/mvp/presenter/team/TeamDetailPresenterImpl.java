package com.kidoo.customer.mvp.presenter.team;

import com.kidoo.customer.mvp.contract.team.TeamDetailContract;
import com.kidoo.customer.mvp.presenter.BasePresenterImpl;

import javax.inject.Inject;

/**
 * User: ShaudXiao
 * Date: 2017-12-18
 * Time: 17:15
 * Company: zx
 * Description:
 * FIXME
 */


public class TeamDetailPresenterImpl extends BasePresenterImpl<TeamDetailContract.View>
        implements TeamDetailContract.Presenter {

    @Inject
    public TeamDetailPresenterImpl() {
    }

    @Override
    public void doQueryTeamDetail(String teamId) {

    }
}
