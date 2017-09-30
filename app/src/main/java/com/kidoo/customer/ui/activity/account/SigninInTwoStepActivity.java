package com.kidoo.customer.ui.activity.account;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kidoo.customer.R;
import com.kidoo.customer.ui.base.activities.BackActivity;
import com.kidoo.customer.utils.parser.RichTextParser;

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


public class SigninInTwoStepActivity extends BackActivity implements View.OnClickListener {


    @BindView(R.id.phone)
    EditText mPhoneEdit;
    @BindView(clean_phone)
    ImageView mCleanPhoneImg;
    @BindView(R.id.sign_in_identifying_code)
    EditText mIddentifyingCodeEdit;
    @BindView(R.id.sign_in_identifying_code_sms_call)
    TextView mIdentifyingCodeText;
    @BindView(R.id.sigin_in_btn)
    Button mSigninBtn;

    private boolean mMachPhoneNum;

    private CountDownTimer mTimer;

    private int mRequestType = 1;//1. 请求发送验证码  2.请求phoneToken

    @Override
    protected int getContentView() {
        return R.layout.activity_sign_in_one_step;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mPhoneEdit.addTextChangedListener(
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
                            String smsCode = mIddentifyingCodeEdit.getText().toString().trim();

                            if (!TextUtils.isEmpty(smsCode)) {
                            } else {
                            }
                        } else {
                        }

                        if (length > 0 && length < 11) {

                        } else if (length == 11) {
                            if (mMachPhoneNum) {
                            } else {
                            }
                        } else if (length > 11) {

                        } else if (length <= 0) {
                        }


                    }
                }

        );

        mIddentifyingCodeEdit.addTextChangedListener(new TextWatcher() {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
   //     getMenuInflater().inflate(R.menu.message_action_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public String getToolbarTitle() {
        return getString(R.string.sign_in_tilte);
    }



    @OnClick({clean_phone, R.id.sign_in_identifying_code_sms_call, R.id.sigin_in_btn})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case clean_phone:
                break;
            case R.id.sign_in_identifying_code_sms_call:
                break;
            case R.id.sigin_in_btn:
                break;
            default:
                break;
        }
    }
}
