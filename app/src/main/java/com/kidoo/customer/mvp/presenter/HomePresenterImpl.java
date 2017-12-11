package com.kidoo.customer.mvp.presenter;

import com.kidoo.customer.bean.NewBananersResult;
import com.kidoo.customer.bean.News;
import com.kidoo.customer.bean.NewsListResult;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.contract.HomeContract;
import com.kidoo.customer.mvp.interactor.HoemInteractor;
import com.kidoo.customer.utils.LogUtils;

import javax.inject.Inject;

/**
 * User: ShaudXiao
 * Date: 2017-12-11
 * Time: 16:52
 * Company: zx
 * Description:
 * FIXME
 */


public class HomePresenterImpl extends BasePresenterImpl<HomeContract.View> implements HomeContract.Presenter  {

    @Inject
    HoemInteractor mInteractor;

    @Inject
    public HomePresenterImpl() {

    }


    @Override
    public void doQueryBanners() {

    }

    @Override
    public void doQueryNews(int type, int pageSize, int pageNum) {
        mInteractor.queryNewsAction(type, pageSize, pageNum, new HomeContract.Interactor.GetNewsCallback(){
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
        mInteractor.queryList(type, new HomeContract.Interactor.Callback() {
            @Override
            public void onSuccess(KidooApiResult<? extends News> result) {
                if(result.getData() instanceof NewBananersResult) {
                    LogUtils.d("NewBananersResult");
                    NewBananersResult bananersResult = (NewBananersResult) result.getData();
                    mPresenterView.updateBannerInfo(bananersResult.getBannerList());

                } else if(result.getData() instanceof NewsListResult) {

                    LogUtils.d("NewsListResult");

                    NewsListResult newsListResult = (NewsListResult) result.getData();
                    mPresenterView.updateNews(newsListResult.getNewsList(), result.getPageInfo());
                }
            }

            @Override
            public void onFailure(String msg) {
                mPresenterView.showError(msg);
            }
        });
    }
}
