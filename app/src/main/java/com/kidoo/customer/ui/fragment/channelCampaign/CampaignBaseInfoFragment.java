package com.kidoo.customer.ui.fragment.channelCampaign;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.ChannelA;
import com.kidoo.customer.bean.ChannelB;
import com.kidoo.customer.bean.ChannelC;
import com.kidoo.customer.bean.ChannelCMap;
import com.kidoo.customer.bean.CompetionDetailResult;
import com.kidoo.customer.bean.CompetionEnrollbean;
import com.kidoo.customer.bean.InitData;
import com.kidoo.customer.bean.MatchBean;
import com.kidoo.customer.component.RxBus;
import com.kidoo.customer.media.ImageGalleryActivity;
import com.kidoo.customer.mvp.presenter.channelCampaign.CompetionEnrollActionPresenterImpl;
import com.kidoo.customer.ui.activity.channelCampaign.CompetionEnrollSituationActivity;
import com.kidoo.customer.ui.base.fragment.BaseMvpFragment;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.dialog.CommonDialog;
import com.kidoo.customer.widget.glideimageview.GlideImageLoader;
import com.kidoo.customer.widget.glideimageview.GlideImageView;
import com.kidoo.customer.widget.glideimageview.progress.CircleProgressView;
import com.kidoo.customer.widget.glideimageview.progress.OnGlideImageViewListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * User: ShaudXiao
 * Date: 2017-12-14
 * Time: 19:32
 * Company: zx
 * Description:
 * FIXME
 */


public class CampaignBaseInfoFragment extends BaseMvpFragment<CompetionEnrollActionPresenterImpl> {

    @BindView(R.id.channel_a)
    TextView tvChannelAName;

    @BindView(R.id.channel_b)
    TextView tvChannelBName;

    @BindView(R.id.channel_c)
    TextView tvChannelCName;

    @BindView(R.id.iv_match_pic)
    GlideImageView ivMatchPic;

    @BindView(R.id.pv_match_pic_progress)
    CircleProgressView pvMatchProgress;


    @BindView(R.id.tv_match_format)
    TextView tvMatchFormat;

    @BindView(R.id.tv_match_info)
    TextView tvMatchInfo;

    @BindView(R.id.tv_match_status)
    TextView tvMatchStatus;

    @BindView(R.id.tv_match_level)
    TextView tvMatchLevel;

    @BindView(R.id.bt_quit_enroll)
    Button btQuitEnrrol;

    @BindView(R.id.bt_enroll)
    Button btEnrollSituation;

    @BindView(R.id.ll_manager)
    LinearLayout llManager;

    @BindView(R.id.bt_modify)
    Button btModify;

    @BindView(R.id.bt_cancel)
    Button btCacnel;

    @BindView(R.id.bt_over)
    Button btOver;

    @Inject
    public CompetionEnrollActionPresenterImpl mPresenter;

    private MatchBean mMatchBean;

    private int selectChannalAIndex = 0;
    private int selectChannalBIndex = 0;
    private int selectChannalCIndex = 0;

    private String channelAName = null;
    private String channelBName = null;
    private String channelCName = null;

    private ChannelC mChannelC;

    private boolean isInMatch = false;

    private boolean fromManager = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        mMatchBean = (MatchBean) bundle.getSerializable(Constants.MATCH_BEAN_DATA_KEY);
        fromManager = bundle.getBoolean(Constants.FROM_MAMAGER_KEY, false);
        LogUtils.i(mMatchBean.toString());

        List<ChannelA> channelAList = AppContext.context().getgChannelAList();
//        if (channelAList != null) {
//
//            ChannelA channelA = channelAList.get(selectChannalAIndex);
//            LogUtils.i(selectChannalAIndex + " " + channelA.getName());
//            channelAName = channelA.getName();
//            ChannelB channelB = channelA.getChannelBList().get(selectChannalBIndex);
//            channelBName = channelB.getName();
//            mChannelC = channelB.getChannelCList().get(selectChannalCIndex);
//            channelCName = mChannelC.getName();
//        }


        List<ChannelCMap> channelCMaps = AppContext.context().getgChannelCMaps();
        List<ChannelB> channelBList = new ArrayList<>();

        if (channelAList != null && channelCMaps != null) {

            ChannelCMap channelCMap = channelCMaps.get(mMatchBean.getChannelCId());
            channelCName = channelCMap.getName();
            for (ChannelA channelA : channelAList) {
                if (channelA.getId() == channelCMap.getChannelAId()) {
                    channelBList = channelA.getChannelBList();
                    channelAName = channelA.getName();
                    break;
                }
            }
            for (ChannelB channelB : channelBList) {
                if (channelB.getId() == channelCMap.getChannelBId()) {
                    channelBName = channelB.getName();
                    break;
                }
            }
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_campaign_base_info_layout;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        tvChannelAName.setText(channelAName);
        tvChannelBName.setText(channelBName);
        tvChannelCName.setText(channelCName);

        if (mMatchBean != null) {
            loadMatchPic();

            tvMatchFormat.setText(getResources().getStringArray(R.array.campaign_format)[mMatchBean.getFormat() - 1]);
            tvMatchLevel.setText(getResources().getStringArray(R.array.campaign_level)[mMatchBean.getLevel() - 1]);
            tvMatchStatus.setText(getResources().getStringArray(R.array.match_status)[mMatchBean.getState()]);
            tvMatchInfo.setText(mMatchBean.getMsg());
        }

        if(fromManager) {
            llManager.setVisibility(View.VISIBLE);
            btQuitEnrrol.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        RxBus.getDefault().toObservableSticky(CompetionDetailResult.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CompetionDetailResult>() {
                    @Override
                    public void accept(CompetionDetailResult result) throws Exception {
//                        LogUtils.i(result.toString());
                        if (result != null) {
                            isInMatch = isInMatch(result.getEnrollList());
                            if (!isInMatch && result.getMatch().getState() == 1) {
                                findView(R.id.bt_quit_enroll).setVisibility(View.VISIBLE);
                                ((Button)findView(R.id.bt_quit_enroll)).setText(R.string.campaign_erroll_btn);
                            } else if (isInMatch) {
                                findView(R.id.bt_quit_enroll).setVisibility(View.VISIBLE);
                                ((Button)findView(R.id.bt_quit_enroll)).setText(R.string.campaign_quit_errol_btn);
                            } else {
                                findView(R.id.bt_quit_enroll).setVisibility(View.GONE);
                            }

                            mMatchBean = result.getMatch();
                            if(fromManager) {
                                findView(R.id.bt_quit_enroll).setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }


    @Override
    protected CompetionEnrollActionPresenterImpl initInjector() {
        mFragmentComponent.inject(this);
        return mPresenter;
    }

    @OnClick(R.id.iv_match_pic)
    public void matchPicClick() {
        String picUrl = AppContext.context().getInitData().getQnDomain() + mMatchBean.getPic();
        if (!TextUtils.isEmpty(picUrl)) {
            ImageGalleryActivity.show(getActivity(), picUrl);
        }
    }

    @OnClick(R.id.bt_enroll)
    public void goToEnrollSituation() {
        Intent intent = new Intent(getActivity(), CompetionEnrollSituationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.MATCH_ID_KEY, mMatchBean.getId());
        LogUtils.i(mMatchBean.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick
    public void quitEnroll() {
        CommonDialog dialog = new CommonDialog(getActivity());
        dialog.setTitle(null);
        if (isInMatch) {
            dialog.setMessage(getString(R.string.competion_quit_enroll_msg));
            dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mPresenter.doQuitCompetionEnroll(mMatchBean.getId());
                    dialog.dismiss();
                }
            });
            dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {
            dialog.setMessage(getString(R.string.competion_enroll_msg));
            dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mPresenter.doCompetionEnroll(mMatchBean.getId());
                    dialog.dismiss();
                }
            });
            dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        }

    }

    private void loadMatchPic() {
        InitData initData = AppContext.context().getInitData();
        String baseUrl = initData.getQnDomain();

        RequestOptions requestOptions = ivMatchPic.requestOptions(R.color.placeholder)
                .centerCrop();

        RequestOptions requestOptionsWithoutCache = ivMatchPic.requestOptions(R.color.placeholder)
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        GlideImageLoader imageLoader = ivMatchPic.getImageLoader();
        imageLoader.setOnGlideImageViewListener(baseUrl + mMatchBean.getPic(), new OnGlideImageViewListener() {
            @Override
            public void onProgress(int percent, boolean isDone, GlideException exception) {
                if (exception != null && !TextUtils.isEmpty(exception.getMessage())) {
                    Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                }
                pvMatchProgress.setProgress(percent);
                pvMatchProgress.setVisibility(isDone ? View.GONE : View.VISIBLE);
            }
        });
        imageLoader.requestBuilder(baseUrl + mMatchBean.getPic(), requestOptionsWithoutCache)
                .thumbnail(Glide.with(getActivity())
                        .load(baseUrl + mMatchBean.getPicMini())
                        .apply(requestOptions))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivMatchPic);
    }

    private boolean isInMatch(List<CompetionEnrollbean> datas) {
        long userId = AccountHelper.getUserId();
        if (datas != null && datas.size() != 0) {
            for (CompetionEnrollbean bean : datas) {
                if (bean.getCustomerId() == userId) {
                    return true;
                }
            }
        }

        return false;
    }
}
