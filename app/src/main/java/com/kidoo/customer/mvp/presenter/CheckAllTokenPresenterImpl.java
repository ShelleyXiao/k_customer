package com.kidoo.customer.mvp.presenter;

import com.kidoo.customer.AccountHelper;
import com.kidoo.customer.api.token.AuthModel;
import com.kidoo.customer.api.token.RSAKey;
import com.kidoo.customer.api.token.TokenManager;
import com.kidoo.customer.bean.CheckAllTokenBean;
import com.kidoo.customer.cipher.rsa.Base64Utils;
import com.kidoo.customer.cipher.rsa.RSAUtil;
import com.kidoo.customer.mvp.contract.CheckAllTokenContract;
import com.kidoo.customer.mvp.interactor.CheckAllTokenInteractor;
import com.kidoo.customer.utils.LogUtils;

import java.util.Date;

import javax.inject.Inject;

/**
 * User: ShaudXiao
 * Date: 2017-11-28
 * Time: 11:08
 * Company: zx
 * Description:
 * FIXME
 */


public class CheckAllTokenPresenterImpl extends  BasePresenterImpl<CheckAllTokenContract.View> implements CheckAllTokenContract.Presenter {


    @Inject
    public CheckAllTokenInteractor mInteractor;


    @Inject
    public CheckAllTokenPresenterImpl() {}


    @Override
    public void checkAllTokenAction() {
        AuthModel authModel = TokenManager.getInstance().getAuthModel(TokenManager.KEY_AUTH);
        RSAKey rsaKey = TokenManager.getInstance().getRSAKey(TokenManager.KEY_RSA);
        LogUtils.d(authModel);
        String customerId = String.valueOf(AccountHelper.getUserId());
        long diffTime = Long.valueOf(authModel.getDifTime());
        String tokenId = authModel.getTokenId();
        long nowTime = new Date().getTime();
        try {
            byte[] encryptTokenByte = RSAUtil.encryptByPublicKey(String.valueOf(nowTime - diffTime), rsaKey.getPublickKey());
            String encryptToken = Base64Utils.encode(encryptTokenByte);

            mInteractor.doCheckAllToken(customerId, encryptToken, tokenId, new CheckAllTokenContract.Interactor.CheckAllTokenCallback() {

                @Override
                public void onSuccess(CheckAllTokenBean result) {
                    mPresenterView.goMain();
                }

                @Override
                public void onFailure(String msg) {
                    mPresenterView.goLogin();

                    TokenManager.getInstance().clearAuth(TokenManager.KEY_AUTH);
                    TokenManager.getInstance().clearAuth(TokenManager.KEY_RSA);
                    AccountHelper.clearUserCache();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            mPresenterView.showToast("");
            mPresenterView.goLogin();

        }


    }
}
