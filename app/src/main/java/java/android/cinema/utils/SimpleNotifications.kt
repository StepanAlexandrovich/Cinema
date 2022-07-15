package java.android.cinema.utils

import android.widget.Toast
import java.android.cinema.activity.ReferenceMain

object SimpleNotifications {

    fun printLong(text:String){
        Toast.makeText(ReferenceMain.activityApp,text, Toast.LENGTH_LONG).show()
    }

    fun printShort(text:String){
        Toast.makeText(ReferenceMain.activityApp,text, Toast.LENGTH_SHORT).show()
    }

}