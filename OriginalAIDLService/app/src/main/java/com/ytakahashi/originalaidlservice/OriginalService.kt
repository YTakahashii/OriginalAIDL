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

        override fun getCpuHands(): Int {
            return (0..2).random()
        }

        override fun getJankenResult(player_hands: Int, cpu_hands: Int): String {
            val resultNum = (player_hands - cpu_hands + 3) % 3
            var resultMessage = "";
            when(resultNum) {
                0 -> {
                    resultMessage = "あいこ"
                }
                1 -> {
                    resultMessage = "負け"
                }
                2 -> {
                    resultMessage = "勝ち"
                }
                else -> {
                    resultMessage = "不正な手です"
                }
            }

            return resultMessage
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }
}