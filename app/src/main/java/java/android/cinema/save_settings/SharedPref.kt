package java.android.cinema.save_settings

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import java.android.cinema.KEY_ADULT
import java.android.cinema.PublicSettings

object SharedPref {
    private lateinit var sp:SharedPreferences

    fun read(activityApp:AppCompatActivity){
        sp = activityApp.getSharedPreferences(KEY_ADULT, Context.MODE_PRIVATE)
        PublicSettings.isAdult = sp.getBoolean(KEY_ADULT,true)
    }

    fun writeAdult(){
        sp.edit().putBoolean(KEY_ADULT,PublicSettings.isAdult).apply()
    }
}