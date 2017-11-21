package com.kidoo.customer.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.kidoo.customer.kidoohttp.api.ComParamContact;
import com.kidoo.customer.mvp.contract.BroadcastDetailContract;
import com.kidoo.customer.bean.DetailResult;
import com.kidoo.customer.bean.Player;
import com.kidoo.customer.bean.TeambaseResult;
import com.kidoo.customer.mvp.view.BaseView;
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
 * description:
 * autour: ShaudXiao
 * date: 2017/10/22
 * update: 2017/10/22
 * version:
 */

public class BroadcastDetailPresenter implements BroadcastDetailContract.Presenter {

    private Context mContext;
    private BroadcastDetailContract.View mView;
    private int mCustomerId;
    private int mBroadcastId;

    public BroadcastDetailPresenter(Context context, BroadcastDetailContract.View view, int customId, int broadcastId) {
        attachView(view);
//        mView.setPresenter(this);

        mCustomerId = customId;
        mBroadcastId = broadcastId;
    }


    @Override
    public void onRefreshing() {
        Observable<DetailResult> myCompaignResultObservable = EasyHttp.get(ComParamContact.BroadcastDetail.PATH)
                .params(ComParamContact.BroadcastDetail.USER_ID, mCustomerId + "")
                .params(ComParamContact.BroadcastDetail.BROADCAST_ID, mBroadcastId + "")
                .execute(DetailResult.class);
        myCompaignResultObservable.flatMap(new Function<DetailResult, ObservableSource<TeambaseResult>>() {
            @Override
            public ObservableSource<TeambaseResult> apply(@NonNull DetailResult detailResult) throws Exception {
                StringBuilder builder = new StringBuilder();
                if (detailResult != null) {
                    LogUtils.w(detailResult.getBroadcast().toString());
                    List<Player> playerList = detailResult.getPlayerList();
                    if(playerList.size() > 0) {
                        for (Player player : playerList) {
                            builder.append(player.getTeamId());
                            builder.append("@");
                        }
                    }  else {
                        builder.append("@");
                    }

                    final String teamIds = builder.toString();
                    if (!TextUtils.isEmpty(teamIds)) {
                        return EasyHttp.get(ComParamContact.TeamBaseInfo.PATH)
                                .params(ComParamContact.TeamBaseInfo.USER_ID, mCustomerId + "")
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
//                        mView.updateTeambaseInfo(teambase.getTeamList());
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
    public void onLoadMore() {

    }

    @Override
    public void attachView(BaseView view) {
        mView = (BroadcastDetailContract.View)view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void getDetail() {

    }
}
