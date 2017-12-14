package com.kidoo.customer.mvp.contract.news;

import com.kidoo.customer.bean.BannerBean;
import com.kidoo.customer.bean.News;
import com.kidoo.customer.bean.NewsBean;
import com.kidoo.customer.bean.NewsListResult;
import com.kidoo.customer.bean.PageInfo;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.mvp.presenter.BasePresenter;
import com.kidoo.customer.mvp.view.BaseView;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * User: ShaudXiao
 * Date: 2017-12-11
 * Time: 16:48
 * Company: zx
 * Description:
 * FIXME
 */


public interface HomeContract {

    interface View extends BaseView {

        void updateBannerInfo(List<BannerBean> bannerBeans);

        void updateNews(List<NewsBean> datas, PageInfo info);

        void loadMoreContent(List<NewsBean> datas, PageInfo info);

        void showError(String msg);

    }


    interface Presenter extends BasePresenter<View> {

        void doQueryBanners();

        void doQueryNews(int type, int pageSize, int pageNum);

        void doQueryList(int type);

    }

    interface Interactor {

        //        Disposable queryBannersAction(HomeContract.Interactor.GetBannerCallback callback);

        Disposable queryNewsAction(int type, int pageSize, int pageNo, GetNewsCallback callback);

        Disposable queryList(int type, Callback callback);

        interface Callback {
            void onSuccess(KidooApiResult<? extends News> result);
            void onFailure(String msg);
        }

        interface GetNewsCallback {
            void onSuccess(KidooApiResult<NewsListResult> result);
            void onFailure(String msg);
        }
    }
}
