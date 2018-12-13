package com.study.yang.accoutmanagerdemokotlin.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.study.yang.accoutmanagerdemokotlin.db.DBHelper


const val AUTHORITY = "com.study.account.provider"


class AccountProvider : ContentProvider() {


    var matcher = UriMatcher(UriMatcher.NO_MATCH)
    lateinit var dbHelper: DBHelper
    var table_name = "account"

    //代码块将在构造方法之前执行
    init {
        //添加匹配码,添加
        matcher.addURI(AUTHORITY, "/account/insert", 1)
        //添加匹配码，删除
        matcher.addURI(AUTHORITY, "/account/delete", 2)
        //添加匹配码，更新
        matcher.addURI(AUTHORITY, "/account/update", 3)
        //添加匹配码，查询
        matcher.addURI(AUTHORITY, "/account/query", 4)
    }

    /**
     * 初始化的时候使用
     */
    override fun onCreate(): Boolean {
        dbHelper = DBHelper(context)
        return true
    }

    //用when代替switch
    override fun getType(uri: Uri): String? =
            when {
                matcher.match(uri) == 1 -> "添加"
                matcher.match(uri) == 2 -> "删除"
                matcher.match(uri) == 3 -> "更新"
                matcher.match(uri) == 4 -> "查询"
                else -> ""
            }

    /**
     * 可以根据传入uri的参数来针对处理相应的数据
     */
    override fun insert(uri: Uri, values: ContentValues): Uri? {
        val db = dbHelper.writableDatabase
        val insert = db.insert(table_name, null, values)
        var insertAfterUri = ContentUris.withAppendedId(uri, insert)
        //  db.close()  //不能够关闭，否则达不到自动更新的效果
        //向外界通知数据发生了变化
        context.contentResolver.notifyChange(uri, null)
        return insertAfterUri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db = dbHelper.writableDatabase
        val deleteCount = db.delete(table_name, selection, selectionArgs)
        // db.close()
        //向外界通知数据发生了变化
        context.contentResolver.notifyChange(uri, null)
        return deleteCount
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        val db = dbHelper.writableDatabase
        val updateCount = db.update(table_name, values, selection, selectionArgs)
        // db.close()  //不能够关闭，否则达不到自动更新的效果
        //向外界通知数据发生了变化
        context.contentResolver.notifyChange(uri, null)
        return updateCount
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val db = dbHelper.writableDatabase
        val cursor = db.query(table_name, projection, selection, selectionArgs, "", "", sortOrder)
        cursor.setNotificationUri(context.contentResolver, uri)
//        db.close()  //不能够关闭，否则达不到自动更新的效果
        return cursor
    }
}
