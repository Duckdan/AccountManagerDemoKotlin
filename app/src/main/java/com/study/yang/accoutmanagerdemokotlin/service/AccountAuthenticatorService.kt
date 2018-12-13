package com.study.yang.accoutmanagerdemokotlin.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.study.yang.accoutmanagerdemokotlin.authenticator.Authenticator

class AccountAuthenticatorService : Service() {

    lateinit var authenticator: Authenticator


    override fun onCreate() {
        super.onCreate()
        authenticator = Authenticator(this)
    }

    override fun onBind(intent: Intent): IBinder {
        return authenticator.iBinder
    }


}
