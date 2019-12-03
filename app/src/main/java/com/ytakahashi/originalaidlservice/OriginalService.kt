package com.ytakahashi.originalaidlservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.ytakahashi.IMyAidlInterface

class OriginalService : Service() {
    private val binder: IMyAidlInterface.Stub = object : IMyAidlInterface.Stub() {
        override fun getMessage(name: String?): String {
            return "Hello $name."
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }
}