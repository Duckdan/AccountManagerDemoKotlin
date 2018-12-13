package com.study.yang.accoutmanagerdemokotlin


import android.net.Uri
import android.view.View
import android.widget.ArrayAdapter
import android.accounts.Account
import android.accounts.AccountAuthenticatorActivity
import android.accounts.AccountManager
import android.content.*
import android.os.*

import com.study.yang.accoutmanagerdemokotlin.adapter.MyCursorAdapter
import com.study.yang.accoutmanagerdemokotlin.data.DefineAccount
import com.study.yang.accoutmanagerdemokotlin.provider.AUTHORITY

import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

/**
 * I want to use Kotlin to implement the program,but
 * it's not good to use Kotlin here.
 */
class LoginActivity : AccountAuthenticatorActivity() {

    private lateinit var accountManage: AccountManager
    private val ACCOUNT_TYPE = "com.study.account"
    val accountStrings = arrayListOf<String>()
    var handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            var account = msg?.obj as DefineAccount
            handlerLoginResult(account.email, account.pwd)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        accountManage = getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
        //根据账户类型获取账号
        accountManage.getAccountsByType(ACCOUNT_TYPE)
                ?.takeIf {
                    //判断当前获取到的账号列表是否为空
                    it.isNotEmpty()
                }?.let {
                    //可变参数时需要引入“*”号
                    Arrays.asList(*it)
                }?.map {
                    var account = it as Account
                    accountStrings.add(it.name)
                    if (it.name == "1") {
                        val password = accountManage.getUserData(it, "password")
                        val email = accountManage.getUserData(it, "account")
                        println(password)
                        println(email)
                        /**
                         * 更新相应账户的UserData
                         */
                        accountManage.setUserData(it, "password", "1111")
                    }
                }

        /**
         * 给AutoCompleteTextView添加自动提示功能
         */
        email.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, accountStrings))


    }

    /**
     * 登录
     */
    fun login(view: View) {
        val email = email.text.toString().trim()
        val password = password.text.toString().trim()
        val msg = handler.obtainMessage()
        var defineAccount = DefineAccount(email, password)
        msg.obj = defineAccount
        handler.sendMessageDelayed(msg, 2000)


    }

    /**
     * 查询用户
     */
    fun queryUser(view: View) {
        var uri = Uri.parse("content://com.study.account.provider/account")
        //cursor不能关闭，一旦关闭CursorAdapter自动更新时将会报错
        //此处传入的uri及其子层级的uri发生变动的时候都会被更新
        val cursor = contentResolver.query(uri, null, null, null, null)
        var cursorAdapter = MyCursorAdapter(this, cursor)
        lv.adapter = cursorAdapter
    }

    /**
     * 处理登录结果
     */
    private fun handlerLoginResult(email: String, password: String) {
        val account = Account(email, ACCOUNT_TYPE)
        val userData = Bundle()
        userData.putString("account", email)
        userData.putString("password", "==$password==")
        userData.putString("auth_token", "")

        var uri = Uri.parse("content://com.study.account.provider/account")
        var values = ContentValues()
        values.put("email", email)
        values.put("pwd", "==$password==")
        contentResolver.insert(uri, values)

        /**
         * 添加账户，建立App的同步机制
         * 例如：
         * 在应用中注册能够接受频繁广播事件的广播，在某种程度上可以达到App包活的效果
         * 最好是设置自动同步，自动同步是由设备自动触发
         */
        accountManage.addAccountExplicitly(account, password, userData)
        //自动同步
        /**
         * 当前账号是否可同步，大于1意味着可以同步
         */
        ContentResolver.setIsSyncable(account, AUTHORITY, 1)
        /**
         * 当前账号是否自动同步
         */
        ContentResolver.setSyncAutomatically(account, AUTHORITY, true)
        /**
         * 当前账号同步的频率
         */
        ContentResolver.addPeriodicSync(account, AUTHORITY, userData, 30)
        //手动同步
        // ContentResolver.requestSync(account, AUTHORITY, userData)
    }

}
