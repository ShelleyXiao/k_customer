package com.kidoo.customer.ui.activity;

import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ViewTarget;
import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.AppContext;
import com.kidoo.customer.R;
import com.kidoo.customer.bean.Customer;
import com.kidoo.customer.media.SelectImageActivity;
import com.kidoo.customer.media.config.SelectOption;
import com.kidoo.customer.mvp.contract.UseDetailContract;
import com.kidoo.customer.mvp.presenter.UserDetailPresenterImpl;
import com.kidoo.customer.ui.base.activities.BaseBackMvpActivity;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.widget.CircleImageView;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * User: ShaudXiao
 * Date: 2017-12-04
 * Time: 10:08
 * Company: zx
 * Description:
 * FIXME
 */


public class UserDetailActivity extends BaseBackMvpActivity<UserDetailPresenterImpl> implements UseDetailContract.View {

    @BindView(R.id.ll_head)
    LinearLayout llHead;

    @BindView(R.id.met_name)
    MaterialEditText metName;

    @BindView(R.id.tv_select_sex)
    TextView tvSelectSex;

    @BindView(R.id.tv_select_date)
    TextView tvSelectDate;

    @BindView(R.id.met_sign)
    MaterialEditText metSign;

    @BindView(R.id.iv_head)
    CircleImageView ivHead;

    @BindView(R.id.ll_select_sex)
    LinearLayout llSelectSex;

    @BindView(R.id.ll_select_birth)
    LinearLayout llSelectBirth;

    @Inject
    public UserDetailPresenterImpl mPresenter;

    private OptionPicker mSexPicker;
    private DatePicker datePicker;
    private ViewTarget viewTarget;

    private UploadManager mUploadManager;

    private Customer mCustomer;

    private File mCacheFile;

    private boolean mIsUploadIcon;


    @Override
    public void initWidget() {
        super.initWidget();

        selectBirthDay();
        selectSex();

        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(30) // 服务器响应超时。默认60秒
                .zone(FixedZone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        mUploadManager = new UploadManager(config);

    }

    @Override
    public void initData() {
        super.initData();
        mCustomer = AccountHelper.getUser();
        String url = AppContext.context().getInitData().getQnDomain() + mCustomer.getPortrait();
        loadImageViewTarget(url);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected UserDetailPresenterImpl initInjector() {
        mActivityComponent.inject(this);

        return mPresenter;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user_detail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_commit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_commit) {
            save();

        }
        return false;
    }

    @OnClick(R.id.ll_head)
    public void selectHeadPhoto() {
        SelectImageActivity.show(this, new SelectOption.Builder()
                .setSelectCount(1)
                .setHasCam(true)
                .setCrop(700, 700)
                .setCallback(new SelectOption.Callback() {
                    @Override
                    public void doSelected(String[] images) {
                        String path = images[0];
                        uploadNewPhoto(new File(path));

                    }
                }).build());
    }


    private void loadImageViewTarget(String img_url) {

        getImageLoader().load(img_url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.def_user)
                .centerCrop()
                .into(ivHead);

    }

    void save() {
        int sex = 0;
        if (tvSelectSex.getText().toString().equals("男")) {
            sex = 1;
        } else {
            sex = 2;
        }

        if (metName == null) {
            return;
        }
        if (metName.getText().toString().equals("")) {
            showToast("请输入昵称～");
            return;
        }

        String qnToken = AppContext.context().getInitData().getQnToken();
        String qnTokenTime = AppContext.context().getInitData().getQnTokenTime();
        if(mCacheFile != null) {
            mUploadManager.put(mCacheFile, mCacheFile.getName(), qnToken,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject res) {
                            //res包含hash、key等信息，具体字段取决于上传策略的设置
                            if (info.isOK()) {

                            } else {
                                LogUtils.i("qiniu", "Upload Fail");
                                //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                            }
                            LogUtils.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                        }
                    }, null);
        }



    }


    //选择性别
    private void selectSex() {
        mSexPicker = new OptionPicker(this, new String[]{"男", "女"});
        mSexPicker.setOffset(2);
        mSexPicker.setTitleText("请选择性别");
        mSexPicker.setTitleTextSize(18);
        mSexPicker.setTitleTextColor(getResources().getColor(R.color.kidoo_black_text));
        mSexPicker.setTopHeight(50);
        mSexPicker.setHeight(400);
        mSexPicker.setSubmitTextSize(18);
        mSexPicker.setCancelVisible(false);
        mSexPicker.setTextSize(24);
        mSexPicker.setSubmitText("完成");
        mSexPicker.setSelectedIndex(0);
        mSexPicker.setTextSize(11);
        mSexPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int position, String option) {
                tvSelectSex.setText(option);
            }

        });
        llSelectSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSexPicker.show();
            }
        });
    }

    private void selectBirthDay() {
        datePicker = new DatePicker(this, DatePicker.YEAR_MONTH_DAY);
        datePicker.setRangeStart(1970, 8, 29);//开始范围
        datePicker.setRangeEnd(2022, 1, 1);//结束范围
        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tvSelectDate.setText(year + "-" + month + "-" + day);
            }
        });
        llSelectBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show();

            }
        });

    }

    private void uploadNewPhoto(File file) {
        // 获取头像缩略图
        if (file == null || !file.exists() || file.length() == 0) {
            AppContext.showToast(getString(R.string.title_icon_null));
        } else {
            mIsUploadIcon = true;
            this.mCacheFile = file;

            ivHead.setImageURI(Uri.parse(file.getAbsolutePath()));

        }

    }

    @Override
    public void updateSuccess(boolean sccuss) {

    }
}
