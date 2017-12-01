package com.kidoo.customer.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.kidoo.customer.kidoohttp.api.ComParamContact;
import com.kidoo.customer.mvp.contract.MyCampaignContract;
import com.kidoo.customer.bean.CampaignScore;
import com.kidoo.customer.bean.MyCampaignResult;
import com.kidoo.customer.bean.TeambaseResult;
import com.kidoo.customer.utils.LogUtils;
import com.zhouyou.http.EasyHttp;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * User: ShaudXiao
 * Date: 2017-10-19
 * Time: 13:57
 * Company: zx
 * Description:
 * FIXME
 */


public class MyCampaignPresenter implements MyCampaignContract.Presenter {

    private Context mContext;
    private MyCampaignContract.View mView;

    public MyCampaignPresenter(Context context, MyCampaignContract.View view) {
        attachView(view);
        this.mContext = context;
    }

    @Override
    public void attachView(MyCampaignContract.View view) {
        this.mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void queryMyCampaign(String customerId) {

    }

    @Override
    public void queryTeambaseInfo(String customerId, String teamIds) {

    }

    @Override
    public void onRefreshCampainInfo(final String customerId) {
        Observable<MyCampaignResult> myCompaignResultObservable = EasyHttp.get(ComParamContact.MyCompaign.PATH)
                .params(ComParamContact.MyCompaign.USER_ID, customerId)
                .execute(MyCampaignResult.class);
        myCompaignResultObservable.flatMap(new Function<MyCampaignResult, ObservableSource<TeambaseResult>>() {
            @Override
            public ObservableSource<TeambaseResult> apply(@NonNull MyCampaignResult myCompaignResult) throws Exception {
                StringBuilder builder = new StringBuilder();
                if(myCompaignResult != null) {

                    mView.updateCampainInfo(myCompaignResult);
                    List<CampaignScore> scores = myCompaignResult.getCampaignScoreList();
                    for(CampaignScore compaignScore: scores) {
                        builder.append(compaignScore.getTeamId());
                        builder.append("@");
                    }
                    final String teamIds = builder.toString();
                    if(!TextUtils.isEmpty(teamIds)) {
                        return   EasyHttp.get(ComParamContact.TeamBaseInfo.PATH)
                                .params(ComParamContact.TeamBaseInfo.USER_ID, customerId)
                                .params(ComParamContact.TeamBaseInfo.TEAM_IDS, teamIds)
                                .execute(TeambaseResult.class);
                    }
                } else {
//                    mView.showNetworkError(mContext.getString(R.string.error_view_load_error_click_to_refresh));
                }

                return null;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TeambaseResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull TeambaseResult teambase) {
                        LogUtils.w(teambase.getTeamList().get(0).toString());
                        mView.updateTeambaseInfo(teambase.getTeamList());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    @Override
    public void addDisposable(Disposable disposable) {

    }

    @Override
    public void unsubscribe() {

    }
}
