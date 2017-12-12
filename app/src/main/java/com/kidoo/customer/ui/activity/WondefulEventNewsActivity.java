package com.kidoo.customer.ui.activity;

import com.kidoo.customer.R;
import com.kidoo.customer.bean.NewsBean;
import com.kidoo.customer.bean.PageInfo;
import com.kidoo.customer.mvp.contract.WondefulEventNewsContract;
import com.kidoo.customer.mvp.presenter.WondfulEventNewsPresenterImpl;
import com.kidoo.customer.ui.base.activities.BaseBackMvpActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * User: ShaudXiao
 * Date: 2017-12-12
 * Time: 20:16
 * Company: zx
 * Description:
 * FIXME
 */


public class WondefulEventNewsActivity extends BaseBackMvpActivity<WondfulEventNewsPresenterImpl> implements WondefulEventNewsContract.View {



    @Inject
    public WondfulEventNewsPresenterImpl mPresenter;

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void updateNews(List<NewsBean> datas, PageInfo info) {

    }

    @Override
    public void loadMoreContent(List<NewsBean> datas, PageInfo info) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    protected WondfulEventNewsPresenterImpl initInjector() {
        mActivityComponent.inject(this);

        return mPresenter;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_wonderful_event_news;
    }
}
