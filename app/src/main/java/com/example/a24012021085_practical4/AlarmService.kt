package com.example.a24012021085_practical4

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class AlarmService : Service() {
    var mp: MediaPlayer?=null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent!=null){
            if(mp==null){
                mp= MediaPlayer.create(this,R.raw.alarm)
            }
            mp?.start()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        mp?.stop()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}