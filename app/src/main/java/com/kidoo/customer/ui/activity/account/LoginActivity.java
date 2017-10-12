package com.kidoo.customer.ui.activity.account;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.R;
import com.kidoo.customer.mvp.contract.LoginContract;
import com.kidoo.customer.mvp.presenter.LoginPresenter;
import com.kidoo.customer.ui.activity.MainActivity;
import com.kidoo.customer.utils.LogUtils;
import com.kidoo.customer.utils.TDevice;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * User: ShaudXiao
 * Date: 2017-09-21
 * Time: 18:30
 * Company: zx
 * Description:
 * FIXME
 */


public class LoginActivity extends AccountBaseActivity implements View.OnClickListener , View.OnFocusChangeListener, LoginContract.View{

    private static final int TEMP_KEY_TIME = 10 * 60 * 1000;

    private static final int REFRESH_TEMP_KEY_MSG = 0X01;
    private static final int LOGIN_MSG = 0X02;

    @BindView(R.id.acnount_id)
    EditText mAccountIdInput;
    @BindView(R.id.acnount_pwd)
    EditText mAccountPwdInput;

    @BindView(R.id.clean_id)
    ImageView mCleanAcnountIdEdit;
    @BindView(R.id.show_pwd)
    ImageView mShowPwdImg;

    @BindView(R.id.bt_login)
    Button mLoginBtn;

    private boolean showPwd = false;


    private LoginContract.Presenter mPresenter;

    private long refreshkeyTimeStamp;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGIN_MSG:
                    requestLogin();
                    break;
                case REFRESH_TEMP_KEY_MSG:
                    requestTempToken();
                    break;
            }
        }
    };

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        super.initData();

        mAccountIdInput.setText(AccountHelper.getAccount(this));
    }

    @Override
    public void initWidget() {

        mPresenter = new LoginPresenter(this);

        mAccountIdInput.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        int length = s.length();
                        if (length > 0) {
                            mCleanAcnountIdEdit.setVisibility(View.VISIBLE);
                        } else {
                            mCleanAcnountIdEdit.setVisibility(View.INVISIBLE);
                        }
                    }

                    @SuppressWarnings("deprecation")
                    @Override
                    public void afterTextChanged(Editable s) {
                        int length = s.length();
                        String input = s.toString();
                    }
                }

        );

    }

    @OnClick({R.id.show_pwd, R.id.clean_id, R.id.sigin_in, R.id.forget_pwd, R.id.bt_login})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                requestLogin();
                break;

            case R.id.clean_id:
                mAccountIdInput.setText("");
                break;

            case R.id.show_pwd:
                LogUtils.d("show passwd");
                if(!showPwd){
                    //显示密码
                    mAccountPwdInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mShowPwdImg.setImageDrawable(getDrawable(R.drawable.btn_openkey));
                    showPwd = true;
                }else{
                    //否则隐藏密码
                    mAccountPwdInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mShowPwdImg.setImageDrawable(getDrawable(R.drawable.btn_closekey));
                    showPwd = false;
                }
                break;
            case R.id.sigin_in:
//                Intent intent = new Intent(LoginActivity.this, SigninInOneStepActivity.class);
//                startActivity(intent);

                break;
            case R.id.forget_pwd:
//                SelectImageActivity.show(this, new SelectOption.Builder()
//                        .setHasCam(true)
//                        .setSelectCount(3)
//                        .setCrop(200, 200)
//                        .setCallback(new SelectOption.Callback() {
//                            @Override
//                            public void doSelected(String[] images) {
//
//                            }
//                        }).build());


                break;

        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @Override
    public void showToast(String msg) {
        showToastForKeyBord(msg);
    }

    @Override
    public void refreshTempKeyNotify(boolean success, final String errorMsg) {
        if(success) {
            refreshkeyTimeStamp = System.currentTimeMillis();
            mHandler.sendEmptyMessage(LOGIN_MSG);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToastForKeyBord(errorMsg);
                }
            });
        }
    }

    @Override
    public void loginResultNotify(boolean success) {
        if(success) {
            showToastForKeyBord(R.string.login_success_hint);
            AccountHelper.holdAccount(this, mAccountIdInput.getText().toString().trim());
            // 执行通知，广播消息，登陆成功
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            finish();
        }
    }

    private void requestTempToken() {
        String accoutId = mAccountIdInput.getText().toString().trim();
        String pwd = mAccountPwdInput.getText().toString().trim();

        if (!TextUtils.isEmpty(accoutId) && !TextUtils.isEmpty(pwd) && TDevice.isMobileNO(accoutId)) {
            if (TDevice.hasInternet()) {
                mPresenter.refreshTempToken(accoutId);
            } else {
                showToastForKeyBord(R.string.footer_type_net_error);
            }

        }  else {
            showToastForKeyBord(R.string.login_input_username_pwd_hint_error);
        }
    }

    private void requestLogin() {
        if(System.currentTimeMillis() - refreshkeyTimeStamp > TEMP_KEY_TIME) {
            mHandler.sendEmptyMessage(REFRESH_TEMP_KEY_MSG);
            LogUtils.w("refresh token");
            return;
        }
        String accoutId = mAccountIdInput.getText().toString().trim();
        String pwd = mAccountPwdInput.getText().toString().trim();

        if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(accoutId)) {

            if (TDevice.hasInternet()) {
                mPresenter.loginAction(accoutId, pwd);
            } else {
                showToastForKeyBord(R.string.footer_type_net_error);
            }

        } else {
            showToastForKeyBord(R.string.login_input_username_pwd_hint_error);
        }
    }


}
