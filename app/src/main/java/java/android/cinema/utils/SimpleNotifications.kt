package java.android.cinema.utils

import android.widget.Toast
import java.android.cinema.activity.Reference

object SimpleNotifications {

    fun printLong(text:String){
        Toast.makeText(Reference.activity,text, Toast.LENGTH_LONG).show()
    }

    fun printShort(text:String){
        Toast.makeText(Reference.activity,text, Toast.LENGTH_SHORT).show()
    }

}