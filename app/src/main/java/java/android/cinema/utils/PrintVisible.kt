package java.android.cinema.utils

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import java.android.cinema.activity.MainActivity

object PrintVisible {

    fun printLong(text:String){
        Toast.makeText(MainActivity.activityApp,text, Toast.LENGTH_LONG).show()
    }

    fun printLongThread(text:String){
        Thread{
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(MainActivity.activityApp,text, Toast.LENGTH_LONG).show()
            }
        }.start()
    }

    fun printShort(text:String){
        Toast.makeText(MainActivity.activityApp,text, Toast.LENGTH_SHORT).show()
    }

}