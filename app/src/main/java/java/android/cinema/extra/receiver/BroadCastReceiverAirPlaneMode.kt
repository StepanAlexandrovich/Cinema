package java.android.cinema.extra.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import java.android.cinema.activity.MainActivity
import java.android.cinema.utils.PrintVisible

class BroadCastReceiverAirPlaneMode:BroadcastReceiver() {
    override fun onReceive(p0: Context?, intent: Intent?) {

        if(Settings.Global.getInt(MainActivity.activityApp.contentResolver, Settings.Global.AIRPLANE_MODE_ON,0)!=0){
            PrintVisible.printLong("РЕЖИМ ПОЛЁТА ВКЛЮЧЕН")
        }else{
            PrintVisible.printLong("РЕЖИМ ПОЛЁТА ВЫКЛЮЧЕН")
        }

    }
}