package com.kidoo.customer.mvp.contract;

import com.kidoo.customer.mvp.model.MyCampaignResult;
import com.kidoo.customer.mvp.model.Teambase;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-10-19
 * Time: 11:49
 * Company: zx
 * Description:
 * FIXME
 */


public interface MyCampaignContract {

    interface  View extends BaseView {
        void updateCampainInfo(MyCampaignResult myBroadcastResult);
        void updateTeambaseInfo(List<Teambase> teambases);
    }

    interface Presenter extends BasePresenter<MyCampaignContract.View> {
        void queryMyCampaign(String customerId);
        void queryTeambaseInfo(String customerId, String teamIds);

        void onRefreshCampainInfo(String customerId);
    }
}
