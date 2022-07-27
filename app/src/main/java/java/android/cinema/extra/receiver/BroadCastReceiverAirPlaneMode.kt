package java.android.cinema.extra.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import java.android.cinema.utils.PrintVisible

class BroadCastReceiverAirPlaneMode:BroadcastReceiver() {
    private val activityApp:AppCompatActivity? = null

    override fun onReceive(p0: Context?, intent: Intent?) {

        if(Settings.Global.getInt(activityApp?.contentResolver, Settings.Global.AIRPLANE_MODE_ON,0)!=0){
            PrintVisible.printLong("РЕЖИМ ПОЛЁТА ВКЛЮЧЕН")
        }else{
            PrintVisible.printLong("РЕЖИМ ПОЛЁТА ВЫКЛЮЧЕН")
        }

    }
}