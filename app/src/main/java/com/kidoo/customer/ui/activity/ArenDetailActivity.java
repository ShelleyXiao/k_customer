package com.kidoo.customer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.kidoo.customer.R;
import com.kidoo.customer.bean.AreanaBean;
import com.kidoo.customer.bean.ChannelA;
import com.kidoo.customer.bean.ChannelB;
import com.kidoo.customer.bean.ChannelC;
import com.kidoo.customer.bean.InitData;
import com.kidoo.customer.ui.base.activities.BaseBackActivity;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.glideimageview.GlideImageLoader;
import com.kidoo.customer.widget.glideimageview.GlideImageView;
import com.kidoo.customer.widget.glideimageview.progress.CircleProgressView;
import com.kidoo.customer.widget.glideimageview.progress.OnGlideImageViewListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Shelley on 2017/12/10.
 */

public class ArenDetailActivity extends BaseBackActivity {

    private static final String SELECT_A_INDEX = "index_a";
    private static final String SELECT_B_INDEX = "index_B";
    private static final String SELECT_C_INDEX = "index_C";
    private static final String ARENB_KEY = "aren_bean";

    @BindView(R.id.channel_a)
    TextView tvChannelAName;

    @BindView(R.id.channel_b)
    TextView tvChannelBName;

    @BindView(R.id.channel_c)
    TextView tvChannelCName;

    @BindView(R.id.aren_pic)
    GlideImageView ivArenPic;

    @BindView(R.id.aren_pic_progress)
    CircleProgressView pvArenPic;

    @BindView(R.id.aren_name)
    TextView tvArenName;

    @BindView(R.id.aren_contact)
    TextView tvArenContact;

    @BindView(R.id.aren_address)
    TextView tvArenAddress;

    @BindView(R.id.aren_detail)
    TextView tvArenDetail;

    private AreanaBean mAreanaBean;

    private ChannelC mChannelC;

    private int selectChannalAIndex = 0;
    private int selectChannalBIndex = 0;
    private int selectChannalCIndex = 0;


    public static void showArenDetail(Context context, ChannelC bean) {
        Intent intent = new Intent(context, ArenDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("channelC", bean);
        context.startActivity(intent);
    }

    public static void showArenDetail(Context context, int selectA, int selectB, int selectC
            , AreanaBean bean) {
        Intent intent = new Intent(context, ArenDetailActivity.class);
        Bundle bundle = new Bundle();

        bundle.putInt(SELECT_A_INDEX, selectA);
        bundle.putInt(SELECT_B_INDEX, selectB);
        bundle.putInt(SELECT_C_INDEX, selectC);
        bundle.putSerializable(ARENB_KEY, bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    protected int getContentView() {
        return R.layout.ativity_aren_info_layout;
    }

    @Override
    protected boolean initBundle(Bundle bundle) {
        if (bundle != null) {
            selectChannalAIndex = bundle.getInt(SELECT_A_INDEX);
            selectChannalBIndex = bundle.getInt(SELECT_B_INDEX);
            selectChannalCIndex = bundle.getInt(SELECT_C_INDEX);

            LogUtils.i(selectChannalAIndex + selectChannalBIndex + selectChannalCIndex);

            mAreanaBean = (AreanaBean) bundle.getSerializable(ARENB_KEY);
        }
        return super.initBundle(bundle);
    }

    @Override
    protected void initWidget() {
        super.initWidget();

    }

    @Override
    protected void initData() {
        super.initData();

        String channelAName = null;
        String channelBName = null;
        String channelCName = null;


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

        tvChannelAName.setText(channelAName);
        tvChannelBName.setText(channelBName);
        tvChannelCName.setText(channelCName);

        loadArenPic();
        tvArenName.setText(mAreanaBean.getName());
        tvArenContact.setText(mAreanaBean.getContact());
        tvArenAddress.setText(mAreanaBean.getAddress());
        tvArenDetail.setText(mAreanaBean.getMsg());
    }

    @Override
    public void showToast(String msg) {

    }

    private void loadArenPic() {
        InitData initData = AppContext.context().getInitData();
        String baseUrl = initData.getQnDomain();

        RequestOptions requestOptions = ivArenPic.requestOptions(R.color.placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);


        GlideImageLoader imageLoader = ivArenPic.getImageLoader();
        imageLoader.setOnGlideImageViewListener(baseUrl + mAreanaBean.getPic(), new OnGlideImageViewListener() {
            @Override
            public void onProgress(int percent, boolean isDone, GlideException exception) {
                if (exception != null && !TextUtils.isEmpty(exception.getMessage())) {
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                }
                pvArenPic.setProgress(percent);
                pvArenPic.setVisibility(isDone ? View.GONE : View.VISIBLE);
            }
        });
        imageLoader.requestBuilder(baseUrl + mAreanaBean.getPic(), requestOptions)
                .thumbnail(Glide.with(ArenDetailActivity.this)
                        .load(baseUrl + mAreanaBean.getPicMini())
                        .apply(requestOptions))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivArenPic);
    }
}
