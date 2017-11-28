package com.kidoo.customer.ui.activity.account;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
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
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kidoo.customer.AppContext;
import com.kidoo.customer.R;
import com.kidoo.customer.mvp.contract.SigninContract;
import com.kidoo.customer.mvp.presenter.SigninPresenterImpl;
import com.kidoo.customer.ui.activity.MainActivity;
import com.kidoo.customer.utils.TDevice;
import com.kidoo.customer.utils.parser.RichTextParser;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.kidoo.customer.R.id.clean_phone;


/**
 * User: ShaudXiao
 * Date: 2017-09-21
 * Time: 18:49
 * Company: zx
 * Description:
 * FIXME
 */


public class SigninInOneStepActivity extends AccountBaseActivity<SigninPresenterImpl> implements View.OnClickListener , SigninContract.View{


    @BindView(R.id.phone)
    EditText mEtRegisterUsername;
    @BindView(clean_phone)
    ImageView mCleanPhoneImg;
    @BindView(R.id.sign_in_identifying_code)
    EditText mEtRegisterAuthCode;
    @BindView(R.id.sign_in_identifying_code_sms_call)
    TextView mTvRegisterSmsCall;
    @BindView(R.id.password)
    EditText mAccountPwdInput;
    @BindView(R.id.show_pwd)
    ImageView mShowPwdImg;
    @BindView(R.id.bt_signin)
    Button mSigninBtn;

    @BindView(R.id.disclaimer_text)
    TextView tvDisclaimer;

    private Toolbar mToolBar;

    private boolean mMachPhoneNum;

    private CountDownTimer mTimer;


    private boolean showPwd = false;

    @Inject
    SigninPresenterImpl mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_sign_in_one_step;
    }

    @Override
    public void initWindow() {
        super.initWindow();
        initToolbar();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mEtRegisterUsername.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        int length = s.length();
                        if (length > 0) {
                            mCleanPhoneImg.setVisibility(View.VISIBLE);
                        } else {
                            mCleanPhoneImg.setVisibility(View.INVISIBLE);
                        }
                    }

                    @SuppressWarnings("deprecation")
                    @Override
                    public void afterTextChanged(Editable s) {
                        int length = s.length();
                        String input = s.toString();
                        mMachPhoneNum = RichTextParser.machPhoneNum(input);

                        if (mMachPhoneNum) {
                            String smsCode = mEtRegisterAuthCode.getText().toString().trim();

                            if (!TextUtils.isEmpty(smsCode)) {
                            } else {
                            }
                        } else {
                        }

                        if (length > 0 && length < 11) {

                        } else if (length == 11) {
                            if (mMachPhoneNum) {
                            } else {
                                showToastForKeyBord(R.string.hint_username_ok);
                            }
                        } else if (length > 11) {

                        } else if (length <= 0) {
                        }


                    }
                }

        );

        mEtRegisterAuthCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressWarnings("deprecation")
            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length > 0 && mMachPhoneNum) {

                } else {

                }
            }
        });

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
    protected SigninPresenterImpl initInjector() {
        mActivityComponent.inject(this);
        return mPresenter;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    private void initToolbar() {
        mToolBar = (Toolbar) findViewById(R.id.id_toolbar);
        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(false);
            }
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            TextView title = (TextView) mToolBar.findViewById(R.id.toolbar_title);
            title.setText(getString(R.string.sign_in_tilte));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //     getMenuInflater().inflate(R.menu.message_action_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @OnClick({clean_phone, R.id.sign_in_identifying_code_sms_call, R.id.bt_signin, R.id.show_pwd})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case clean_phone:
                mEtRegisterUsername.setText("");
                break;
            case R.id.sign_in_identifying_code_sms_call:
                requestSmsCode();
                break;
            case R.id.bt_signin:
                signIn();
                break;

            case R.id.show_pwd:
                if (!showPwd) {
                    //显示密码
                    mAccountPwdInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mShowPwdImg.setImageDrawable(getResources().getDrawable(R.drawable.btn_openkey));
                    showPwd = true;
                } else {
                    //否则隐藏密码
                    mAccountPwdInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mShowPwdImg.setImageDrawable(getResources().getDrawable(R.drawable.btn_closekey));
                    showPwd = false;
                }
                break;
            default:
                break;
        }
    }

    private void requestSmsCode() {
        if (!mMachPhoneNum) {
            showToastForKeyBord(R.string.hint_username_ok);
            return;
        }
        if (!TDevice.hasInternet()) {
            showToastForKeyBord(R.string.tip_network_error);
            return;
        }

        if (mTvRegisterSmsCall.getTag() == null) {
            mTvRegisterSmsCall.setTag(true);
            mTimer = new CountDownTimer(60 * 1000, 1000) {

                @SuppressLint("DefaultLocale")
                @Override
                public void onTick(long millisUntilFinished) {
                    mTvRegisterSmsCall.setText(String.format("%s%s%d%s",
                            getResources().getString(R.string.register_sms_hint), "(", millisUntilFinished / 1000, ")"));
                }

                @Override
                public void onFinish() {
                    mTvRegisterSmsCall.setTag(null);
                    mTvRegisterSmsCall.setText(getResources().getString(R.string.register_sms_hint));
                    mTvRegisterSmsCall.setAlpha(1.0f);
                }
            }.start();
            String phoneNumber = mEtRegisterUsername.getText().toString().trim();
            //请求Sms
            mPresenter.getSMS(phoneNumber);

        } else {
            AppContext.showToast(getResources().getString(R.string.register_sms_wait_hint), Toast.LENGTH_SHORT);
        }
    }

    private void signIn() {
        if (!mMachPhoneNum) {
            showToastForKeyBord(R.string.hint_username_ok);
            return;
        }
        if (!TDevice.hasInternet()) {
            showToastForKeyBord(R.string.tip_network_error);
            return;
        }

        String smsCode = mEtRegisterAuthCode.getText().toString().trim();
        if (!mMachPhoneNum || TextUtils.isEmpty(smsCode)) {
            //showToastForKeyBord(R.string.hint_username_ok);
            return;
        }

        String phoneNumber = mEtRegisterUsername.getText().toString().trim();
        String pwd = mAccountPwdInput.getText().toString().trim();
        if(RichTextParser.machPassword(pwd)) {
            mPresenter.signin(this, phoneNumber, pwd, smsCode);
        } else {
            showToastForKeyBord(R.string.sign_in_pwd_simple_hint);
            mAccountPwdInput.requestFocus();
        }

    }

    @Override
    public void stopSMSCounter(boolean smsSucess) {
        if(smsSucess) {

            showToastForKeyBord(getString(R.string.get_sms_call_success_hint));
        } else {
//            showToastForKeyBord(getString(R.string.get_sms_call_faild_hint));
        }

        if(null != mTimer) {
            mTimer.onFinish();
            mTimer.cancel();
        }
    }

    @Override
    public void goMain() {
        Intent intent = new Intent(SigninInOneStepActivity.this, MainActivity.class);
        startActivity(intent);

        finish();
    }

}
