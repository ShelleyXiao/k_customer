package com.kidoo.customer.mvp.presenter;

import com.kidoo.customer.bean.NewsListResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.WondefulEventNewsContract;
import com.kidoo.customer.mvp.interactor.WondefulEventNewsInteractor;

import javax.inject.Inject;

/**
 * User: ShaudXiao
 * Date: 2017-12-12
 * Time: 20:13
 * Company: zx
 * Description:
 * FIXME
 */


public class WondfulEventNewsPresenterImpl extends BasePresenterImpl<WondefulEventNewsContract.View>
        implements WondefulEventNewsContract.Presenter {

    @Inject
    public WondefulEventNewsInteractor mInteractor;

    @Inject
    public WondfulEventNewsPresenterImpl() {

    }

    @Override
    public void doQueryNews(int type, int pageSize, int pageNum) {
        mInteractor.queryNewsAction(type, pageSize, pageNum, new WondefulEventNewsContract.Interactor.Callback() {
            @Override
            public void onSuccess(KidooApiResult<NewsListResult> result) {
                NewsListResult newsListResult = (NewsListResult) result.getData();
                mPresenterView.loadMoreContent(newsListResult.getNewsList(), result.getPageInfo());
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showError(msg);
            }
        });
    }

    @Override
    public void doQueryList(int type) {
        mInteractor.queryList(type, new WondefulEventNewsContract.Interactor.Callback() {
            @Override
            public void onSuccess(KidooApiResult<NewsListResult> result) {
                NewsListResult newsListResult = (NewsListResult) result.getData();
                mPresenterView.updateNews(newsListResult.getNewsList(), result.getPageInfo());
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showError(msg);
            }
        });
    }
}
