package com.study.yang.accoutmanagerdemokotlin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val CREATE_TABLE = "create table if not exists account(_id integer primary key autoincrement," +
        "email varchar(12),pwd varchar(20))"

class DBHelper(context: Context) : SQLiteOpenHelper(context, "user.db", null, 1) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}