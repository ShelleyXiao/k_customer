package com.kidoo.customer.ui.activity.account;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.kidoo.customer.R;
import com.kidoo.customer.utils.LogUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * User: ShaudXiao
 * Date: 2017-09-21
 * Time: 18:30
 * Company: zx
 * Description:
 * FIXME
 */


public class LoginActivity extends AccountBaseActivity implements View.OnClickListener {


    @Bind(R.id.acnount_id)
    EditText mAccountIdInput;
    @Bind(R.id.acnount_pwd)
    EditText mAccountPwdInput;

    @Bind(R.id.clean_id)
    ImageView mCleanAcnountIdEdit;
    @Bind(R.id.show_pwd)
    ImageView mShowPwdImg;

    private boolean showPwd = false;

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initWidget() {
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

    @OnClick({R.id.show_pwd, R.id.clean_id, R.id.sigin_in, R.id.forget_pwd})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clean_id:
                mAccountIdInput.setText("");
                break;

            case R.id.show_pwd:
                LogUtils.d("show passwd");
                if(showPwd){
                    //显示密码
                    mAccountPwdInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mShowPwdImg.setImageDrawable(getDrawable(R.drawable.btn_closekey));
                    showPwd = false;
                }else{
                    //否则隐藏密码
                    mAccountPwdInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mShowPwdImg.setImageDrawable(getDrawable(R.drawable.btn_openkey));
                    showPwd = true;
                }
                break;
            case R.id.sigin_in:
                Intent intent = new Intent(LoginActivity.this, SigninInOneStepActivity.class);
                startActivity(intent);
                break;
            case R.id.forget_pwd:

                break;

        }
    }


}
