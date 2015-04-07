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
import android.util.Log;

/**
 * Created by Administrator on 2015/3/31.
 */
public class Authenticator extends AbstractAccountAuthenticator {//被系统调用

    private Context mContext;

    public Authenticator(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }


    /**在系统账户中直接添加账户时被调用*/
    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {//添加账号
        Intent intent = new Intent(mContext, LoginActivity.class);
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

    /**在登陆时检测token临牌是调用*/
    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {//获得tpken
        if (authTokenType.equals(constant.TOKEN_TYPE)) {//判断token的type不是testToken
            Bundle bundle = new Bundle();
            bundle.putString(AccountManager.KEY_ERROR_MESSAGE, "你错了哈哈哈哈哈哈哈哈哈不！");
            return bundle;
        }
        String token = AccountManager.get(mContext).peekAuthToken(account, constant.TOKEN_TYPE);
        if (!TextUtils.isEmpty(token)) {//判断是否获得token值
            Bundle bundle=new Bundle();
            bundle.putString(AccountManager.KEY_ACCOUNT_NAME,account.name);
            bundle.putString(AccountManager.KEY_ACCOUNT_TYPE,constant.ACCOUNT_TYPE);
            bundle.putString(AccountManager.KEY_AUTHTOKEN,token);
            return bundle;
        }
        Intent intent=new Intent(mContext,LoginActivity.class);//如果以上都没有执行就跳转到登陆界面
        intent.putExtra(constant.loginname, account.name);
        intent.putExtra(constant.TokenType, authTokenType);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle serverBundle = new Bundle();
        serverBundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return serverBundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {//设置token标签
        String testToken = authTokenType.equals(constant.TOKEN_TYPE) ? authTokenType : null;
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

}
