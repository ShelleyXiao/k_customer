package com.kidoo.customer.mvp.presenter.channelCampaign;

import com.kidoo.customer.bean.AllChannelResultBean;
import com.kidoo.customer.bean.MatchListResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.channelCampaign.ChannelCampaignContract;
import com.kidoo.customer.mvp.interactor.channelCampaign.ChannelCampaignInteractor;
import com.kidoo.customer.mvp.presenter.BasePresenterImpl;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-12-13
 * Time: 14:27
 * Company: zx
 * Description:
 * FIXME
 */


public class ChannelCampaignPresnterImpl extends BasePresenterImpl<ChannelCampaignContract.View>
        implements ChannelCampaignContract.Presenter {

    @Inject
    public ChannelCampaignInteractor mInteractor;

    @Inject
    public ChannelCampaignPresnterImpl() {

    }

    @Override
    public void doQueryAllChannels() {
        Disposable disposable = mInteractor.queryAllChannelsAction(new ChannelCampaignContract.Interactor.GetAllChannelsCallback() {
            @Override
            public void onSuccess(AllChannelResultBean result) {
//                LogUtils.i(result.getChannelAList().size());
                mPresenterView.updateChannelInfo(result);
            }

            @Override
            public void onFailure(String msg) {

            }
        });

        addDisposable(disposable);
    }

    @Override
    public void doQueryMatchs(int channelCID, int pageSize, int pageNo) {
        Disposable disposable = mInteractor.queryMoreMatchsAction(channelCID, pageSize, pageNo, new ChannelCampaignContract.Interactor.GetMatchsCallback() {
            @Override
            public void onSuccess(KidooApiResult<MatchListResult> result) {
                mPresenterView.loadMoreContent(result.getData().getMatchList(), result.getPageInfo());
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showError(msg, ChannelCampaignContract.Type.more);
            }
        });

        addDisposable(disposable);
    }

    @Override
    public void doQuery(int channelCID) {
        LogUtils.i("channelCID = " + channelCID);
        Disposable disposable = mInteractor.queryMacths(channelCID, new ChannelCampaignContract.Interactor.GetMatchsCallback() {
            @Override
            public void onSuccess(KidooApiResult<MatchListResult> result) {
//                LogUtils.i(result.toString());
                if (result.getData() != null) {
                    mPresenterView.updateMatch(result.getData().getMatchList(), result.getPageInfo());
                } else {
                    mPresenterView.updateMatch(null, null);
                }
            }

            @Override
            public void onFailure(String msg) {
                LogUtils.e(msg);
                mPresenterView.showError(msg, ChannelCampaignContract.Type.all);
            }
        });

        addDisposable(disposable);
    }
}
