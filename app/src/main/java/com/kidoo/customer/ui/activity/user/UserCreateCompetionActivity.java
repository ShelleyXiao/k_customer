package com.kidoo.customer.ui.activity.user;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.R;
import com.kidoo.customer.api.token.QNToken;
import com.kidoo.customer.bean.ChannelA;
import com.kidoo.customer.bean.ChannelC;
import com.kidoo.customer.bean.MatchBean;
import com.kidoo.customer.media.SelectImageActivity;
import com.kidoo.customer.media.config.SelectOption;
import com.kidoo.customer.mvp.contract.user.CreateCompetionContract;
import com.kidoo.customer.mvp.presenter.user.CreateCompetionPresenter;
import com.kidoo.customer.ui.base.activities.BaseBackMvpActivity;
import com.kidoo.customer.utils.DialogHelper;
import com.kidoo.customer.utils.FileUtils;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.EditTextLengthFilter;
import com.kidoo.customer.widget.dialog.CommonDialog;
import com.kidoo.customer.widget.expandMenu.SelectMenuView;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * User: ShaudXiao
 * Date: 2017-12-22
 * Time: 15:06
 * Company: zx
 * Description:
 * FIXME
 */


public class UserCreateCompetionActivity extends BaseBackMvpActivity<CreateCompetionPresenter> implements View.OnClickListener
        , SelectMenuView.OnMenuSelectDataChangedListener, EasyPermissions.PermissionCallbacks,
        CreateCompetionContract.View {

    private static final int RC_EXTERNAL_STORAGE = 0x04;

    @BindView(R.id.search_menu)
    SelectMenuView smChannelMenu;

    @BindView(R.id.met_create_competion_name)
    MaterialEditText mtNameInput;

    @BindView(R.id.met_create_competion_name_str_count)
    TextView tvNameCounter;

    @BindView(R.id.rg_competion_level)
    RadioGroup rgCompetionLevel;

    @BindView(R.id.rg_competion_fomat)
    RadioGroup rgCompetionFormat;

    @BindView(R.id.met_create_competion_info)
    MaterialEditText metMsgInput;

    @BindView(R.id.create_competion_info_str_count)
    TextView tvMsgCounter;

    @BindView(R.id.iv_competion_log)
    ImageView ivCompetionLogo;

    @BindView(R.id.bt_create_competion_upload_logo)
    Button btLogoUpload;

    @BindView(R.id.bt_create_competion_comfirm)
    Button btCreateComfirm;

    private File mCacheFile;
    private boolean mIsUploadIcon;
    private String mUploadPath;

    private QNToken mQNToken;
    private UploadManager mUploadManager;
    private long updateQNTokenTime = 0;

    private List<ChannelA> mChannelList = new ArrayList<>();
    private int mSelectChannelID = -1;
    private int mSelectChannelAIndex = 0;
    private int mSelectChannelBIndex = 0;
    private int mSelectChannelCIndex = 0;

    private int competionLevel = 1;
    private int competionFormat = 1;

    @Inject
    public CreateCompetionPresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_create_competion_layout;
    }


    @Override
    protected CreateCompetionPresenter initInjector() {
        mActivityComponent.inject(this);
        return mPresenter;
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


        requestExternalStorage();

        smChannelMenu.setOnMenuSelectDataChangedListener(this);

        List<ChannelA> channelAList = AppContext.context().getgChannelAList();
        if (channelAList != null && channelAList.size() > 0) {
            smChannelMenu.setDataList(channelAList);
            mChannelList.addAll(channelAList);

            mSelectChannelID = channelAList.get(mSelectChannelAIndex).getChannelBList()
                    .get(mSelectChannelBIndex)
                    .getChannelCList().get(mSelectChannelCIndex)
                    .getId();
        }
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        mtNameInput.setFilters(new InputFilter[]{new EditTextLengthFilter(this, 20, tvNameCounter, null)});
        tvMsgCounter.setFilters(new InputFilter[]{new EditTextLengthFilter(this, 500, tvMsgCounter, null)});

        rgCompetionLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_competion_level_0:
                        competionLevel = 1;
                        break;
                    case R.id.rb_competion_level_1:
                        competionLevel = 2;
                        break;
                    case R.id.rb_competion_level_2:
                        competionLevel = 3;
                        break;
                    default:
                        break;
                }
            }
        });

        rgCompetionFormat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_competion_fomat_0:
                        competionFormat = 1;
                        break;
                    case R.id.rb_competion_fomat_1:
                        competionFormat = 2;
                        break;
                    case R.id.rb_competion_fomat_2:
                        competionFormat = 3;
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        if (System.currentTimeMillis() - updateQNTokenTime > (7200 * 1000)) {
            mPresenter.doQueryPicInfo();
        }
    }


    @OnClick({R.id.bt_create_competion_comfirm, R.id.bt_create_competion_upload_logo})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_create_competion_upload_logo:
                SelectImageActivity.show(this, new SelectOption.Builder()
                        .setSelectCount(1)
                        .setCrop(800, 480)
                        .setHasCam(true)
                        .setCallback(new SelectOption.Callback() {
                            @Override
                            public void doSelected(String[] images) {
                                String path = images[0];
                                mUploadPath = path;
                                uploadNewPhoto(new File(path));

                            }
                        }).build());
                break;
            case R.id.bt_create_competion_comfirm:
                CommonDialog dialog = new CommonDialog(UserCreateCompetionActivity.this);
                dialog.setTitle(null);
                dialog.setMessage(getString(R.string.create_competion_confirm));
                dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        createCompetionAction();
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

    @Override
    public void updateSuccess(boolean sccuss) {
        dismissLoadingDialog();
        CommonDialog dialog = new CommonDialog(UserCreateCompetionActivity.this);
        dialog.setTitle(null);

        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if (sccuss) {
            dialog.setMessage(getString(R.string.create_competion_success_msg));
        } else {
            dialog.setMessage(getString(R.string.create_competion_fiald_msg));
        }
        dialog.show();
    }

    @Override
    public void updateQNToken(QNToken token) {
        mQNToken = token;
        updateQNTokenTime = System.currentTimeMillis();
    }

    @Override
    public void onSubjectABChanged(int indexChannalA, int indexChannalB) {

    }

    @Override
    public void onSubjectCChanged(int indexChannalC) {
        mSelectChannelCIndex = indexChannalC;
        ChannelC selectChannelC = mChannelList.get(mSelectChannelAIndex)
                .getChannelBList().get(mSelectChannelBIndex)
                .getChannelCList().get(indexChannalC);
        mSelectChannelID = selectChannelC.getId();
    }

    @Override
    public void onViewClicked(View view) {

    }

    @Override
    public void onSelectedDismissed(int indexChannalA, int indexChannalB) {

    }

    private void createCompetionAction() {
        MatchBean bean = new MatchBean();
        String name = mtNameInput.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Dialog dialog = DialogHelper.getWarningDailt(this, getString(R.string.empty_name_input_prompt));
            dialog.show();
            return;
        }
        bean.setName(name);

        String msg = metMsgInput.getText().toString();
        if (TextUtils.isEmpty(msg)) {
            Dialog dialog = DialogHelper.getWarningDailt(this, getString(R.string.empty_msg_input_prompt));
            dialog.show();
            return;
        }
        bean.setMsg(msg);

        bean.setFormat(competionFormat);
        bean.setLevel(competionLevel);
        bean.setCreateTime(System.currentTimeMillis());

        bean.setChannelCId(mSelectChannelID);
        bean.setPic(mUploadPath);
        bean.setPicMini(mUploadPath);


        String matchJons = new Gson().toJson(bean);
        LogUtils.d(bean.toString());

        mPresenter.createCompetion(AccountHelper.getUserId() + "", matchJons);

    }

    private void uploadNewPhoto(File file) {
// 获取头像缩略图
        if (file == null || !file.exists() || file.length() == 0) {
            AppContext.showToast(getString(R.string.title_icon_null));
        } else {
            mIsUploadIcon = true;
            this.mCacheFile = file;

            ivCompetionLogo.setImageURI(Uri.parse(file.getAbsolutePath()));
            ivCompetionLogo.setVisibility(View.VISIBLE);
            uploadImagFileToQN(file);
        }
    }

    private void uploadImagFileToQN(File file) {
        showLoadingDialog("");

        final String qnToken = mQNToken.getQnToken();
        String qnTokenTime = mQNToken.getQnTokenTime();
        try {
            Luban.with(this)
                    .load(file)                     //传人要压缩的图片
                    .ignoreBy(100)
                    .setCompressListener(new OnCompressListener() { //设置回调

                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File compeessedFile) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            final String path = compeessedFile.getPath();
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
                                                mUploadPath = hash;
                                                dismissLoadingDialog();

                                            } else {
                                                LogUtils.i("qiniu", "Upload Fail");
                                                //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因

                                                dismissLoadingDialog();
                                                CommonDialog dialog = new CommonDialog(UserCreateCompetionActivity.this);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == RC_EXTERNAL_STORAGE) {
            DialogHelper.getConfirmDialog(this, "", getString(R.string.media_select_image_store_perm_hint), getString(R.string.media_select_image_perm_setting_ok),
                    getString(R.string.media_select_image_perm_setting_cancel), false, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                            finish();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    public void requestExternalStorage() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        } else {
            EasyPermissions.requestPermissions(this, "", RC_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }
}
