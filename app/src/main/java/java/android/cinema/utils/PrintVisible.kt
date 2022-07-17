package java.android.cinema.utils

import android.widget.Toast
import java.android.cinema.activity.MainActivity

object PrintVisible {

    fun printLong(text:String){
        Toast.makeText(MainActivity.activityApp,text, Toast.LENGTH_LONG).show()
    }

    fun printShort(text:String){
        Toast.makeText(MainActivity.activityApp,text, Toast.LENGTH_SHORT).show()
    }

}