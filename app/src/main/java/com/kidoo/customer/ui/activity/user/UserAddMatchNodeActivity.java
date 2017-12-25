package com.kidoo.customer.ui.activity.user;

import android.content.DialogInterface;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kidoo.customer.AppContext;
import com.kidoo.customer.R;
import com.kidoo.customer.api.token.QNToken;
import com.kidoo.customer.media.SelectImageActivity;
import com.kidoo.customer.media.config.SelectOption;
import com.kidoo.customer.ui.base.activities.BaseBackActivity;
import com.kidoo.customer.utils.DateTimeUtils;
import com.kidoo.customer.utils.FileUtils;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.dialog.CommonDialog;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by ShaudXiao on 2017/12/25.
 */

public class UserAddMatchNodeActivity extends BaseBackActivity
        implements View.OnClickListener {

    @BindView(R.id.met_competion_node_info)
    MaterialEditText metNodeInfoInput;

    @BindView(R.id.met_competion_node_info_str_count)
    TextView tvNodeInfoStrCounter;

    @BindView(R.id.ll_node_selectTime)
    LinearLayout llNodeSelectTime;

    @BindView(R.id.tv_competion_select_time)
    TextView tvNodeSelectTime;

    @BindView(R.id.rg_competion_fomat)
    RadioGroup rgShowType;

    @BindView(R.id.iv_competion_log)
    ImageView ivNodePic;

    @BindView(R.id.bt_competion_node_upload_pic)
    Button btNodeSelectPic;

    @BindView(R.id.bt_competion_node_add_comfirm)
    Button btNodeAddConfirm;

    private DatePicker datePicker;
    private int mShowType = 1;
    private long mNodeTimestamp;

    private File mCacheFile;
    private boolean mIsUploadIcon;
    private String mUploadPath;

    private QNToken mQNToken;
    private UploadManager mUploadManager;
    private long updateQNTokenTime = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_add_match_node_layout;
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


        datePicker = new DatePicker(this, DatePicker.YEAR_MONTH_DAY);
        datePicker.setRangeStart(1970, 8, 29);//开始范围
        datePicker.setRangeEnd(2022, 1, 1);//结束范围
        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tvNodeSelectTime.setText(year + "-" + month + "-" + day);
                String foramtDate = year + "-" + month + "-" + day;
                try {
                    mNodeTimestamp = DateTimeUtils.parseMillis(foramtDate, DateTimeUtils.FORMAT_SHORT);
                    LogUtils.i(foramtDate + " time " + mNodeTimestamp);
                } catch (ParseException e) {

                }
            }
        });


    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
    }

    @OnClick({R.id.bt_competion_node_add_comfirm, R.id.bt_competion_node_upload_pic
            , R.id.ll_node_selectTime})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_competion_node_add_comfirm:
                break;
            case R.id.bt_competion_node_upload_pic:

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
            case R.id.ll_node_selectTime:
                if (!datePicker.isShowing()) {
                    datePicker.show();
                }
                break;
            default:
                break;
        }
    }

    private void uploadNewPhoto(File file) {
        if (file == null || !file.exists() || file.length() == 0) {
            AppContext.showToast(getString(R.string.title_icon_null));
        } else {
            mIsUploadIcon = true;
            this.mCacheFile = file;

            ivNodePic.setImageURI(Uri.parse(file.getAbsolutePath()));
            ivNodePic.setVisibility(View.VISIBLE);
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
                                                CommonDialog dialog = new CommonDialog(UserAddMatchNodeActivity.this);
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

}
