package com.example.a24012021085_practical4

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmBroadcastReceiver : BroadcastReceiver() {
    companion object{

        val SERVICE_KEY= "Service1"

        val START_VALUE="start"

        val STOP_VALUE="stop"
    }
    override fun onReceive(context: Context, intent: Intent) {
        val str1=intent.getStringExtra(SERVICE_KEY)
        if (str1 == START_VALUE || str1== STOP_VALUE){
            val intentService= Intent(context, AlarmService::class.java)
            if (str1== START_VALUE)
                context.startService(intentService)
            else
                context.stopService(intentService)
        }

    }
}