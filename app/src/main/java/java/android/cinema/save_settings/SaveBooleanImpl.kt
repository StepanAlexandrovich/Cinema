package java.android.cinema.save_settings

import android.content.Context
import android.content.SharedPreferences
import java.android.cinema.activity.MyApp

class SaveBooleanImpl(private val key:String):SaveBoolean {
    private var sp: SharedPreferences = MyApp.getMyApp().getSharedPreferences(key,Context.MODE_PRIVATE)

    override fun read(): Boolean {
        return sp.getBoolean(key,true)
    }

    override fun write(b:Boolean) {
        sp.edit().putBoolean(key,b).apply()
    }
}