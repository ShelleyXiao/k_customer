package com.kidoo.customer.ui.activity.account;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.kidoo.customer.R;
import com.kidoo.customer.utils.DialogHelper;
import com.kidoo.customer.utils.LogUtils;

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


public class LoginActivity extends AccountBaseActivity implements View.OnClickListener {


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

    private Dialog mLoadingDialog;

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
        mLoadingDialog = DialogHelper.getLoadingDialog(this);
    }

    @OnClick({R.id.show_pwd, R.id.clean_id, R.id.sigin_in, R.id.forget_pwd, R.id.bt_login})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                break;

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
//                Intent intent = new Intent(LoginActivity.this, SigninInOneStepActivity.class);
//                startActivity(intent);
                mLoadingDialog.hide();
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

                mLoadingDialog.show();
                break;

        }
    }


}
