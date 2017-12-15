package com.kidoo.customer.ui.fragment.channelCampaign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.ChannelA;
import com.kidoo.customer.bean.ChannelB;
import com.kidoo.customer.bean.ChannelC;
import com.kidoo.customer.bean.InitData;
import com.kidoo.customer.bean.MatchBean;
import com.kidoo.customer.ui.base.fragment.BaseFragment;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.glideimageview.GlideImageLoader;
import com.kidoo.customer.widget.glideimageview.GlideImageView;
import com.kidoo.customer.widget.glideimageview.progress.CircleProgressView;
import com.kidoo.customer.widget.glideimageview.progress.OnGlideImageViewListener;

import java.util.List;

import butterknife.BindView;

/**
 * User: ShaudXiao
 * Date: 2017-12-14
 * Time: 19:32
 * Company: zx
 * Description:
 * FIXME
 */


public class CampaignBaseInfoFragment extends BaseFragment {

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


    private MatchBean mMatchBean;

    private int selectChannalAIndex = 0;
    private int selectChannalBIndex = 0;
    private int selectChannalCIndex = 0;

    private String channelAName = null;
    private String channelBName = null;
    private String channelCName = null;

    private ChannelC mChannelC;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        mMatchBean = (MatchBean) bundle.getSerializable(Constants.MATCH_BEAN_DATA_KEY);
        LogUtils.i(mMatchBean.toString());

        List<ChannelA> channelAList = AppContext.context().getgChannelAList();
        if (channelAList != null) {

            ChannelA channelA = channelAList.get(selectChannalAIndex);
            LogUtils.i(selectChannalAIndex + " " + channelA.getName());
            channelAName = channelA.getName();
            ChannelB channelB = channelA.getChannelBList().get(selectChannalBIndex);
            channelBName = channelB.getName();
            mChannelC = channelB.getChannelCList().get(selectChannalCIndex);
            channelCName = mChannelC.getName();
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

            tvMatchFormat.setText(getResources().getStringArray(R.array.campaign_format)[mMatchBean.getFormat()]);
            tvMatchLevel.setText(getResources().getStringArray(R.array.campaign_level)[mMatchBean.getLevel()]);
            tvMatchStatus.setText(getResources().getStringArray(R.array.match_status)[mMatchBean.getState()]);
            tvMatchInfo.setText(mMatchBean.getMsg());
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
}
