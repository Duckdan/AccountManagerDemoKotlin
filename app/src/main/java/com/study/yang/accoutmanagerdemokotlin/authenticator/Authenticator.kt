package com.study.yang.accoutmanagerdemokotlin.authenticator

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.study.yang.accoutmanagerdemokotlin.LoginActivity
import com.study.yang.accoutmanagerdemokotlin.R

class Authenticator(context: Context) : AbstractAccountAuthenticator(context) {

    var context = context

    /**
     * 至于这个方法，没有找到触发的方式
     * 个人理解：是根据authTokenType获取authenticator.xml中配置的android:label
     * 所以在这个地方赋值为@string/account_label
     */
    override fun getAuthTokenLabel(authTokenType: String?): String {
        println("getAuthTokenLabel")
        return context.getString(R.string.account_label)
    }

    /**
     * accountManage.confirmCredentials(it, null, this@LoginActivity, null, null)
     * 调用confirmCredentials
     */
    override fun confirmCredentials(response: AccountAuthenticatorResponse?, account: Account?, options: Bundle?): Bundle {
        println("confirmCredentials")
        return Bundle()
    }

    /**
     * accountManage.updateCredentials(it, ACCOUNT_TYPE, null, this@LoginActivity, null, null)
     */
    override fun updateCredentials(response: AccountAuthenticatorResponse?, account: Account?, authTokenType: String?, options: Bundle?): Bundle {
        println("updateCredentials")
        return Bundle()
    }

    /**
     * accountManage.getAuthToken(it, ACCOUNT_TYPE, null, this@LoginActivity, null, null)
     * accountManage.blockingGetAuthToken(account, ACCOUNT_TYPE, true)
     * 都会调用getAuthToken方法，blockingGetAuthToken必须在子线程中调用，否则会造成死锁
     */
    override fun getAuthToken(response: AccountAuthenticatorResponse?, account: Account?, authTokenType: String?, options: Bundle?): Bundle {
        println("getAuthToken")
        return Bundle()
    }

    /**
     *  accountManage.hasFeatures(it, features, null, null)
     */
    override fun hasFeatures(response: AccountAuthenticatorResponse?, account: Account?, features: Array<out String>?): Bundle {
        println("hasFeatures")
        return Bundle()
    }

    /**
     *  accountManage.editProperties(ACCOUNT_TYPE, this@LoginActivity, null, null)
     */
    override fun editProperties(response: AccountAuthenticatorResponse?, accountType: String?): Bundle {
        println("editProperties")
        return Bundle()
    }

    /**
     * 该方法在手机中“设置->安全与账户->
     * 用户和账户列表页面点击“添加”按钮->
     * 添加账户列表->选中自己的应用->
     * 调用addAccount方法”
     */
    override fun addAccount(response: AccountAuthenticatorResponse?, accountType: String?, authTokenType: String?, requiredFeatures: Array<out String>?, options: Bundle?): Bundle {
        println("addAccount")
        val addAccountIntent = Intent(context, LoginActivity::class.java)
        addAccountIntent.putExtra("authTokenType", authTokenType)
        if (options != null) {
            addAccountIntent.putExtras(options)
        }
        //一定要把response传入intent的extra中，便于将登录操作的结果回调给AccountManager
        addAccountIntent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)

        var bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, addAccountIntent)
        return bundle
    }

}