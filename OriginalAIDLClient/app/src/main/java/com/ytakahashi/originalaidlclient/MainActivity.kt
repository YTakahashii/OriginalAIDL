package com.ytakahashi.originalaidlclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.widget.Toast
import com.ytakahashi.IMyAidlInterface
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var svc: IMyAidlInterface? = null
    enum class Hand(val value: Int) {
        ROCK(0), SCISSOR(1), PAPER(2),
    }
    private val hands = arrayOf("✊", "✌️", "✋")

    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            svc = IMyAidlInterface.Stub.asInterface(service)
            Toast.makeText(applicationContext, "Service Connected", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            svc = null
            Toast.makeText(applicationContext, "Service Disconnected", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onStart() {
        super.onStart()
        if(svc == null) {
            val it = Intent("originalservice")
            it.setPackage("com.ytakahashi.originalaidlservice")
            bindService(it, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            try {
                val message = svc?.getMessage("yuta")
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            } catch (e: RemoteException) {
                e.printStackTrace()
                Log.d("client", e.toString())
            }
        }

        button_rock.setOnClickListener {
            janken(Hand.ROCK);
        }

        button_scissor.setOnClickListener {
            janken(Hand.SCISSOR);
        }

        button_paper.setOnClickListener {
            janken(Hand.PAPER);
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }

    private fun janken(hand: Hand) {
        try {
            val cpuHands = svc?.getCpuHands()!!
            val result = svc?.getJankenResult(hand.value, cpuHands)
            textView_cpu_hands.text = hands[cpuHands]
            textView_result.text = result

        } catch (e: RemoteException) {
            e.printStackTrace()
            Log.d("client", e.toString())
        }
    }

}
