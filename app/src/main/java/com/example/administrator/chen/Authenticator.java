package com.example.administrator.chen;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

/**
 * Created by Administrator on 2015/3/31.
 */
public class Authenticator extends AbstractAccountAuthenticator {

    private Context context;

    public Authenticator(Context context) {
        super(context);
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {//添加账号
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(constant.accountType, accountType);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {//获得tpken

        if (authTokenType.equals(constant.TokenType)) {//判断token的type不是testToken
            Bundle bundle = new Bundle();
            bundle.putString(AccountManager.KEY_ERROR_MESSAGE, "你错了哈哈哈哈哈哈哈哈哈不！");
            return bundle;
        }
        String token = AccountManager.get(context).peekAuthToken(account, constant.TOKEN_TYPE);
        if (!TextUtils.isEmpty(token)) {
            Bundle bundle=new Bundle();
            bundle.putString(AccountManager.KEY_ACCOUNT_NAME,account.name);
            bundle.putString(AccountManager.KEY_ACCOUNT_TYPE,constant.ACCOUNT_TYPE);
            bundle.putString(AccountManager.KEY_AUTHTOKEN,token);
            return bundle;
        }
        Intent intent=new Intent(context,LoginActivity.class);
        intent.putExtra(constant.loginname, account.name);
        intent.putExtra(constant.TokenType, authTokenType);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle serverBundle = new Bundle();
        serverBundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return serverBundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {//设置token标签
        String testToken = authTokenType.equals(constant.TokenType) ? authTokenType : null;
        return testToken;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }

    private  void  getToken(){





    }

}
