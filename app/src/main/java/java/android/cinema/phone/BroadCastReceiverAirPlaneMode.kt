package java.android.cinema.phone

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import java.android.cinema.activity.ReferenceMain
import java.android.cinema.utils.SimpleNotifications

class BroadCastReceiverAirPlaneMode:BroadcastReceiver() {
    override fun onReceive(p0: Context?, intent: Intent?) {

        if(Settings.Global.getInt(ReferenceMain.activityApp!!.contentResolver, Settings.Global.AIRPLANE_MODE_ON,0)!=0){
            SimpleNotifications.printLong("РЕЖИМ ПОЛЁТА ВКЛЮЧЕН")
        }else{
            SimpleNotifications.printLong("РЕЖИМ ПОЛЁТА ВЫКЛЮЧЕН")
        }

    }
}