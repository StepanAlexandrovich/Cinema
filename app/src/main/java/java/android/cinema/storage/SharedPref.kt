package java.android.cinema.storage

import android.content.Context
import android.content.SharedPreferences
import java.android.cinema.PublicSettings
import java.android.cinema.activity.MainActivity

object SharedPref {
    private const val KEY_ADULT = "bhbhbjij"
    private lateinit var sp:SharedPreferences

    fun read(){
        sp = MainActivity.activityApp.getSharedPreferences(KEY_ADULT, Context.MODE_PRIVATE)
        PublicSettings.isAdult = sp.getBoolean(KEY_ADULT,true)
        //......
        //......
    }

    fun writeAdult(){
        sp.edit().putBoolean(KEY_ADULT,PublicSettings.isAdult).apply()
    }
}