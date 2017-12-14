package com.kidoo.customer.ui.activity.account;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.R;
import com.kidoo.customer.mvp.contract.LoginContract;
import com.kidoo.customer.mvp.presenter.account.LoginPresenterImpl;
import com.kidoo.customer.ui.activity.MainActivity;
import com.kidoo.customer.utils.TDevice;

import javax.inject.Inject;

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


public class LoginActivity extends AccountBaseActivity<LoginPresenterImpl> implements View.OnClickListener , View.OnFocusChangeListener, LoginContract.View{


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

    @BindView(R.id.disclaimer_text)
    TextView tvDisclaimer;

    private boolean showPwd = false;

    @Inject
    public LoginPresenterImpl mPresenter;

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
        super.initWidget();
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

        String tilte = getString(R.string.disclaimer_title);
        String url = getString(R.string.disclaimer_about);
        String disclaimer = tilte + url;
        SpannableString str = new SpannableString(disclaimer);
        str.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //todo
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.argb(255, 255, 0, 0));
                ds.setUnderlineText(false);
            }

        }, tilte.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDisclaimer.setMovementMethod(LinkMovementMethod.getInstance());
        tvDisclaimer.setHighlightColor(getResources().getColor(android.R.color.transparent));
        tvDisclaimer.setText(str);
    }

    @Override
    protected LoginPresenterImpl initInjector() {
        mActivityComponent.inject(this);
        return mPresenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
                if(!showPwd){
                    //显示密码
                    mAccountPwdInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mShowPwdImg.setImageDrawable(getResources().getDrawable(R.drawable.btn_openkey));
                    showPwd = true;
                }else{
                    //否则隐藏密码
                    mAccountPwdInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mShowPwdImg.setImageDrawable(getResources().getDrawable(R.drawable.btn_closekey));
                    showPwd = false;
                }
                break;
            case R.id.sigin_in:
                Intent intent = new Intent(LoginActivity.this, SigninInOneStepActivity.class);
                startActivity(intent);

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

//
//    @Override
//    public void setPresenter(BasePresenter presenter) {
//        this.mPresenter = (LoginContract.Presenter)presenter;
//    }

    public void showNetworkError(String str) {
        showToastForKeyBord(str);
    }

//    @Override
//    public void refreshTempKeyNotify(boolean success, final String errorMsg) {
//        if(success) {
//            refreshkeyTimeStamp = System.currentTimeMillis();
//            mHandler.sendEmptyMessage(LOGIN_MSG);
//        } else {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    showToastForKeyBord(errorMsg);
//                }
//            });
//        }
//    }

    @Override
    public void goMainPage() {
        showToastForKeyBord(R.string.login_success_hint);
        AccountHelper.holdAccount(this, mAccountIdInput.getText().toString().trim());
        // 执行通知，广播消息，登陆成功
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
    }



    private void requestTempToken() {
        String accoutId = mAccountIdInput.getText().toString().trim();
        String pwd = mAccountPwdInput.getText().toString().trim();

        if (!TextUtils.isEmpty(accoutId) && !TextUtils.isEmpty(pwd) && TDevice.isMobileNO(accoutId)) {
            if (TDevice.hasInternet()) {
//                mLoginPresenter.refreshTempToken(accoutId);
            } else {
                showToastForKeyBord(R.string.footer_type_net_error);
            }

        }  else {
            showToastForKeyBord(R.string.login_input_username_pwd_hint_error);
        }
    }

    private void requestLogin() {
//        if(System.currentTimeMillis() - refreshkeyTimeStamp > TEMP_KEY_TIME) {
//            mHandler.sendEmptyMessage(REFRESH_TEMP_KEY_MSG);
//            LogUtils.w("refresh token");
////            return;
//        }
        String accoutId = mAccountIdInput.getText().toString().trim();
        String pwd = mAccountPwdInput.getText().toString().trim();

        if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(accoutId)) {
            if (TDevice.hasInternet()) {
                mPresenter.loginAction(this, accoutId, pwd);
            } else {
                showToastForKeyBord(R.string.footer_type_net_error);
            }

        } else {
            showToastForKeyBord(R.string.login_input_username_pwd_hint_error);
        }
    }


    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
