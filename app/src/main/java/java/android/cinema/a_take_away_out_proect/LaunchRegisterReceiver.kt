package java.android.cinema.a_take_away_out_proect

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.android.cinema.activity.MainActivity

class LaunchRegisterReceiver {

    fun fromEverywhereLaunch(){

        MainActivity.activityApp.  registerReceiver(object: BroadcastReceiver(){
            override fun onReceive(p0: Context?, p1: Intent?) {
                Log.d("@@@","onReceive ${Thread.currentThread()}")
            }

        }, IntentFilter("answer"))

        MainActivity.activityApp. sendBroadcast(Intent().apply {
            action = "myAction"
        })
    }

    fun localLaunch(){

        LocalBroadcastManager.getInstance(  MainActivity.activityApp  ).registerReceiver(object: BroadcastReceiver(){
            override fun onReceive(p0: Context?, p1: Intent?) {
                Log.d("@@@","onReceive ${Thread.currentThread()}")
            }

        }, IntentFilter("answer"))

        MainActivity.activityApp.  sendBroadcast(Intent().apply {
            action = "myAction"
        })
    }

}