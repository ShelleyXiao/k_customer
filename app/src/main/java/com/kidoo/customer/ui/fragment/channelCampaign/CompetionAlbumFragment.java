package com.kidoo.customer.ui.fragment.channelCampaign;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.adapter.competion.CompetionAlbumAdapter;
import com.kidoo.customer.adapter.itemDecoration.SpaceItemDecoration;
import com.kidoo.customer.api.AddMatchPicApi;
import com.kidoo.customer.api.token.QNToken;
import com.kidoo.customer.bean.CompetionAlbumBean;
import com.kidoo.customer.bean.CompetionDetailResult;
import com.kidoo.customer.bean.MatchBean;
import com.kidoo.customer.bean.ReturnNullBean;
import com.kidoo.customer.component.RxBus;
import com.kidoo.customer.kidoohttp.api.KidooApiResult;
import com.kidoo.customer.kidoohttp.http.KidooApiManager;
import com.kidoo.customer.kidoohttp.http.function.RetryExceptionFunc;
import com.kidoo.customer.kidoohttp.http.utils.RxUtil;
import com.kidoo.customer.media.ImageGalleryActivity;
import com.kidoo.customer.media.SelectImageActivity;
import com.kidoo.customer.media.config.SelectOption;
import com.kidoo.customer.ui.base.fragment.BaseFragment;
import com.kidoo.customer.utils.DialogHelper;
import com.kidoo.customer.utils.FileUtils;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.utils.NetWorkUtil;
import com.kidoo.customer.widget.EmptyLayout;
import com.kidoo.customer.widget.dialog.CommonDialog;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by Administrator on 2017/12/16.
 */

public class CompetionAlbumFragment extends BaseFragment {

    @BindView(R.id.album)
    RecyclerView rvAlbum;

    @BindView(R.id.bt_add_pic)
    Button btAddPic;

    @BindView(R.id.error_layout)
    EmptyLayout elEmptylayout;

    private List<CompetionAlbumBean> mAlbumDatas = new ArrayList<>();
    private CompetionAlbumAdapter albumAdapter;

    private MatchBean mMatchBean;
    private boolean fromManager = false;

    private UploadManager mUploadManager;
    private File mCacheFile;
    private boolean mIsUploadIcon;
    private String mUploadPath;
    private QNToken mQNToken;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        mMatchBean = (MatchBean) bundle.getSerializable(Constants.MATCH_BEAN_DATA_KEY);
        fromManager = bundle.getBoolean(Constants.FROM_MAMAGER_KEY, false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_competion_album_layout;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(30) // 服务器响应超时。默认60秒
                .zone(FixedZone.zone2) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();

        mUploadManager = new UploadManager(config);

        albumAdapter = new CompetionAlbumAdapter(getActivity(), mAlbumDatas);
        rvAlbum.setAdapter(albumAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.setAutoMeasureEnabled(true);
        rvAlbum.setLayoutManager(gridLayoutManager);
        rvAlbum.setHasFixedSize(true);
        rvAlbum.addItemDecoration(new SpaceItemDecoration(15));
        if (fromManager) {
            btAddPic.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        RxBus.getDefault().toObservableSticky(CompetionDetailResult.class)
                .subscribe(new Consumer<CompetionDetailResult>() {
                    @Override
                    public void accept(CompetionDetailResult result) throws Exception {
//                        LogUtils.i(result.toString());
                        if (result != null && (result.getAlbumList() != null && result.getAlbumList().size() > 0)) {
                            albumAdapter.replaceData(result.getAlbumList());
                        } else {
                            if (!fromManager && elEmptylayout != null) {
                                elEmptylayout.setVisibility(View.VISIBLE);
                                elEmptylayout.setErrorType(EmptyLayout.NODATA);
                            }
                        }
                    }
                });
        RxBus.getDefault().toObservableSticky(QNToken.class)
                .subscribe(new Consumer<QNToken>() {
                    @Override
                    public void accept(QNToken token) throws Exception {
                        if (token != null) {
                            mQNToken = token;
                        }
                    }
                });

        albumAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArrayList<String> picUrls = new ArrayList<>();
                String baseUrl = AppContext.context().getInitData().getQnDomain();
                for (CompetionAlbumBean bean : mAlbumDatas) {
                    String url = baseUrl + bean.getPic();
                    picUrls.add(url);
                }

                ImageGalleryActivity.show(getActivity(), (String[]) picUrls.toArray(), position);
            }
        });
    }

    @OnClick(R.id.bt_add_pic)
    public void addPic() {
        if (NetWorkUtil.isNetWorkAvailable(getContext())) {
            SelectImageActivity.show(getActivity(), new SelectOption.Builder()
                    .setSelectCount(1)
                    .setHasCam(true)
                    .setCallback(new SelectOption.Callback() {
                        @Override
                        public void doSelected(String[] images) {
                            String path = images[0];
                            mUploadPath = path;
                            uploadNewPhoto(new File(path));

                        }
                    }).build());
        } else {
            Dialog dialog = DialogHelper.getNetworkErrorDialog(getActivity());
            dialog.show();
        }

    }


    private void uploadNewPhoto(File file) {
        if (file == null || !file.exists() || file.length() == 0) {
            AppContext.showToast(getString(R.string.title_icon_null));
        } else {
            mIsUploadIcon = true;
            this.mCacheFile = file;
            LogUtils.i(" " + file.getAbsoluteFile());
            uploadImagFileToQN(file);

        }
    }

    private void uploadImagFileToQN(File file) {
        LogUtils.i(mQNToken);
        if (mQNToken == null) {
            return;
        }
        final String qnToken = mQNToken.getQnToken();
        String qnTokenTime = mQNToken.getQnTokenTime();
        try {
            Luban.with(getActivity())
                    .load(file)                     //传人要压缩的图片
                    .ignoreBy(100)
                    .setCompressListener(new OnCompressListener() { //设置回调

                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            showLoadingDialog("");
                        }

                        @Override
                        public void onSuccess(File compeessedFile) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            final String path = compeessedFile.getPath();
                            if (path == null) {
                                LogUtils.e("path null");
                                return;
                            }
                            LogUtils.i(path);
                            final String fileHashCode = FileUtils.getHash(path, "sha1") + "." + FileUtils.getFileFormat(path);
                            mUploadManager.put(mCacheFile, fileHashCode, qnToken,
                                    new UpCompletionHandler() {
                                        @Override
                                        public void complete(String key, ResponseInfo info, JSONObject res) {
                                            //res包含hash、key等信息，具体字段取决于上传策略的设置
                                            LogUtils.i(key + info.toString());
                                            if (info.isOK()) {
                                                String hash = null;
                                                try {
                                                    hash = res.getString("key");
                                                } catch (Exception e) {

                                                }
                                                LogUtils.i(hash);
                                                mUploadPath = hash;

                                                CompetionAlbumBean bean = new CompetionAlbumBean();
                                                bean.setMatchId(mMatchBean.getId());
                                                bean.setCreateTime(System.currentTimeMillis());
                                                bean.setPic(hash);
                                                bean.setPicMini(hash);
                                                albumAdapter.addData(bean);

                                                addPicAction(mMatchBean.getId() + "", hash, hash);

                                                dismissLoadingDialog();

                                            } else {
                                                LogUtils.e("qiniu", "Upload Fail");
                                                //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因

                                                dismissLoadingDialog();
                                                CommonDialog dialog = new CommonDialog(getActivity());
                                                dialog.setTitle(null);
                                                dialog.setMessage(getString(R.string.pic_upload_faild));
                                                dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                                dialog.show();
                                            }

                                            LogUtils.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                                        }
                                    }, null);


                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过去出现问题时调用
                            LogUtils.e(e.getMessage());
                            dismissLoadingDialog();

                        }
                    }).launch();    //启动压缩


        } catch (NullPointerException e) {
            LogUtils.e(e.getMessage());
            dismissLoadingDialog();
        }

    }

    private void addPicAction(String matchId, String pic, String picMini) {
        Observable observable = AddMatchPicApi.addMatchPicApi(matchId, pic, picMini)
                .compose(RxUtil.io_main())
                .retryWhen(new RetryExceptionFunc(KidooApiManager.getRetryCount(),
                        KidooApiManager.getRetryDelay(),
                        KidooApiManager.getRetryIncreaseDelay()));
        Disposable disposable = observable.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                dismissLoadingDialog();
                KidooApiResult<ReturnNullBean> result = (KidooApiResult<ReturnNullBean>) o;
                CommonDialog dialog = new CommonDialog(getActivity());
                dialog.setTitle(null);
                dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                if (result.isSuccess()) {
                    dialog.setMessage(getString(R.string.pic_upload_success));
                } else {
                    dialog.setMessage(getString(R.string.pic_upload_faild));
                }
                dialog.show();

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                dismissLoadingDialog();
            }
        });


    }
}
