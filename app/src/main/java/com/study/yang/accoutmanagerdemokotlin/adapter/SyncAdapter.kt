package com.study.yang.accoutmanagerdemokotlin.adapter

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.Context
import android.content.SyncResult
import android.net.Uri
import android.os.Bundle

class SyncAdapter(context: Context, autoInitialize: Boolean) : AbstractThreadedSyncAdapter(context, autoInitialize) {


    /**
     * 不管系统自动同步还是手动同步，都会调用这个方法
     * 在这个方法中可以处理与服务器交互从而同步数据
     *
     * account:当前同步的账号
     * extras:同步的额外参数，目前没发现效果
     * authority:sync_adapter.xml中配置的contentAuthority的值
     * provider:与authority相匹配的ContentProvider的Client
     * syncResult:同步结果
     */
    override fun onPerformSync(account: Account?, extras: Bundle?, authority: String?,
                               provider: ContentProviderClient?, syncResult: SyncResult?) {
//        context.contentResolver.notifyChange()
        println("${account?.name}===${account?.type}")
        println("$authority")
        println(extras)
        var uri = Uri.parse("content://com.study.account.provider/account")
        val projections = arrayOf("email", "pwd")
        val cursor = provider?.query(uri, projections, "email=?", arrayOf(account?.name), null)
        cursor?.takeIf {
            it.count > 0
        }.let {
            it?.moveToFirst()
            println(it?.getString(it.getColumnIndex("pwd")))
        }
    }

}