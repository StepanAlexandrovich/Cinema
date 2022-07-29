package java.android.cinema.a_take_away_out_proect

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class LaunchRegisterReceiver {

    private val activityApp: AppCompatActivity? = null

    fun fromEverywhereLaunch(){

        activityApp?.registerReceiver(object: BroadcastReceiver(){
            override fun onReceive(p0: Context?, p1: Intent?) {
                Log.d("@@@","onReceive ${Thread.currentThread()}")
            }

        }, IntentFilter("answer"))

        activityApp?.sendBroadcast(Intent().apply {
            action = "myAction"
        })
    }

    fun localLaunch(){

        activityApp?.let {
            LocalBroadcastManager.getInstance(it).registerReceiver(object: BroadcastReceiver(){
                override fun onReceive(p0: Context?, p1: Intent?) {
                    Log.d("@@@","onReceive ${Thread.currentThread()}")
                }

            }, IntentFilter("answer"))
        }

        activityApp?.sendBroadcast(Intent().apply {
            action = "myAction"
        })
    }

}