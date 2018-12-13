package com.study.yang.accoutmanagerdemokotlin.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.study.yang.accoutmanagerdemokotlin.adapter.SyncAdapter

class SyncAdapterService : Service() {
    lateinit var syncAdapter: SyncAdapter

    override fun onCreate() {
        super.onCreate()
        syncAdapter = SyncAdapter(this, true)
    }

    override fun onBind(intent: Intent): IBinder {
        return syncAdapter.syncAdapterBinder
    }
}
