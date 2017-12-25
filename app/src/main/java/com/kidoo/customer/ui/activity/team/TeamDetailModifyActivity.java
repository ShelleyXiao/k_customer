package com.kidoo.customer.ui.activity.team;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kidoo.customer.AppContext;
import com.kidoo.customer.Constants;
import com.kidoo.customer.R;
import com.kidoo.customer.api.token.QNToken;
import com.kidoo.customer.bean.TeamBean;
import com.kidoo.customer.media.ImageGalleryActivity;
import com.kidoo.customer.media.SelectImageActivity;
import com.kidoo.customer.media.config.SelectOption;
import com.kidoo.customer.mvp.contract.team.TeamInfoModifyContract;
import com.kidoo.customer.mvp.presenter.team.TeamInfoModifyPresenter;
import com.kidoo.customer.ui.base.activities.BaseBackMvpActivity;
import com.kidoo.customer.utils.FileUtils;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.EditTextLengthFilter;
import com.kidoo.customer.widget.dialog.CommonDialog;
import com.kidoo.customer.widget.glideimageview.GlideImageView;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.kidoo.customer.media.ImageGalleryActivity.KEY_IMAGE;

/**
 * User: ShaudXiao
 * Date: 2017-12-19
 * Time: 17:00
 * Company: zx
 * Description:
 * FIXME
 */


public class TeamDetailModifyActivity extends BaseBackMvpActivity<TeamInfoModifyPresenter> implements TeamInfoModifyContract.View
        , View.OnClickListener {

    @BindView(R.id.ll_head)
    LinearLayout llHead;

    @BindView(R.id.iv_team_logo)
    GlideImageView ivTeamLogo;

    @BindView(R.id.met_name)
    MaterialEditText metName;

    @BindView(R.id.met_team_msg)
    MaterialEditText metTeamMsg;

    @BindView(R.id.bt_modify_team)
    Button btModify;

    @BindView(R.id.str_count)
    TextView tvStrCount;

    @Inject
    public TeamInfoModifyPresenter mPresenter;

    private QNToken mQNToken;
    private UploadManager mUploadManager;

    private TeamBean mCurrentTeam;
    private String baseUrl;

    private File mCacheFile;
    private boolean mIsUploadIcon;
    private String mCropHeadPath;


    @Override
    protected boolean initBundle(Bundle bundle) {
        if (bundle != null) {
            mCurrentTeam = (TeamBean) bundle.getSerializable(Constants.TEAM_ID_KEY);
        }
        return super.initBundle(bundle);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_team_modify_info_layout;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(30) // 服务器响应超时。默认60秒
                .zone(FixedZone.zone2) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();

        mUploadManager = new UploadManager(config);

        baseUrl = AppContext.context().getInitData().getQnDomain();

        ivTeamLogo.loadImage(baseUrl + mCurrentTeam.getIcon(), R.color.placeholder);
        metName.setText(mCurrentTeam.getName());


        metTeamMsg.setFilters(new InputFilter[]{new EditTextLengthFilter(this, 50, tvStrCount, null)});
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();


    }

    @Override
    public void updateSuccess(boolean sccuss) {
        dismissLoadingDialog();
        CommonDialog dialog = new CommonDialog(TeamDetailModifyActivity.this);
        dialog.setTitle(null);

        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if (sccuss) {
            dialog.setMessage(getString(R.string.team_modify_success_msg));
        } else {
            dialog.setMessage(getString(R.string.team_modify_fail_msg));
        }
        dialog.show();
    }

    @Override
    public void updateQNToken(QNToken token) {
        mQNToken = token;
        LogUtils.i(mQNToken.toString());
    }

    @Override
    protected TeamInfoModifyPresenter initInjector() {
        mActivityComponent.inject(this);
        return mPresenter;
    }

    @OnClick({R.id.ll_head, R.id.iv_team_logo, R.id.bt_modify_team})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_head:
                SelectImageActivity.show(this, new SelectOption.Builder()
                        .setSelectCount(1)
                        .setHasCam(true)
                        .setCrop(800, 800)
                        .setCallback(new SelectOption.Callback() {
                            @Override
                            public void doSelected(String[] images) {
                                String path = images[0];
                                mCropHeadPath = path;
                                uploadNewPhoto(new File(path));

                            }
                        }).build());
                break;
            case R.id.iv_team_logo:
                LogUtils.i("onClick tema pic");
                Intent intent = new Intent(this, ImageGalleryActivity.class);
                intent.putExtra(KEY_IMAGE, new String[]{baseUrl + mCurrentTeam.getIcon()});
                ActivityOptionsCompat compat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(this, ivTeamLogo, getString(R.string.transitional_image));
                ActivityCompat.startActivity(this, intent, compat.toBundle());
                break;
            case R.id.bt_modify_team:
                CommonDialog dialog = new CommonDialog(TeamDetailModifyActivity.this);
                dialog.setTitle(null);
                dialog.setMessage(getString(R.string.team_modify_confirm));
                dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        save();
                    }
                });
                dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

                break;
        }
    }


    private void uploadNewPhoto(File file) {
        // 获取头像缩略图
        if (file == null || !file.exists() || file.length() == 0) {
            AppContext.showToast(getString(R.string.title_icon_null));
        } else {
            mIsUploadIcon = true;
            this.mCacheFile = file;

            ivTeamLogo.setImageURI(Uri.parse(file.getAbsolutePath()));

        }

    }

    private void save() {
        final String nickName = metName.getText().toString();
        if (TextUtils.isEmpty(nickName)) {
            showToast(getString(R.string.user_detail_nickname_empty));
            return;
        }

        final String teamMsg = metTeamMsg.getText().toString().trim();
        showLoadingDialog("");

        if (mCacheFile == null) {
            mPresenter.doUpdateTeamInfo(mCurrentTeam.getId() + "", nickName, teamMsg, "");
            return;
        }

        final String qnToken = mQNToken.getQnToken();
        String qnTokenTime = mQNToken.getQnTokenTime();
        try {
            Luban.with(this)
                    .load(mCacheFile)                     //传人要压缩的图片
                    .ignoreBy(100)
                    .setCompressListener(new OnCompressListener() { //设置回调

                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            final String path = file.getPath();
                            if (path == null) {
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
                                                String ext = FileUtils.getFileFormat(path);
                                                mPresenter.doUpdateTeamInfo(mCurrentTeam.getId() + "",
                                                        nickName, teamMsg,
                                                        hash);

                                            } else {
                                                LogUtils.i("qiniu", "Upload Fail");
                                                //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因

                                                mPresenter.doUpdateTeamInfo(mCurrentTeam.getId() + "",
                                                        nickName, teamMsg, "");
                                                dismissLoadingDialog();
                                                CommonDialog dialog = new CommonDialog(TeamDetailModifyActivity.this);
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


                        }
                    }).launch();    //启动压缩


        } catch (NullPointerException e) {

        }


    }

    private String getLimitSubstring(String inputStr) {
        int orignLen = inputStr.length();
        int resultLen = 0;
        String temp = null;
        for (int i = 0; i < orignLen; i++) {
            temp = inputStr.substring(i, i + 1);
            try {// 3 bytes to indicate chinese word,1 byte to indicate english
                // word ,in utf-8 encode
                if (temp.getBytes("utf-8").length == 3) {
                    resultLen += 2;
                } else {
                    resultLen++;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (resultLen > 30) {
                return inputStr.substring(0, i);
            }
        }
        return inputStr;
    }


}
