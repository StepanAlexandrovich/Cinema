package java.android.cinema.activity

import androidx.appcompat.app.AppCompatActivity

object ReferenceMain {
    var activityApp: AppCompatActivity? = null
    var activityMain: MainActivity? = null

    fun init(activity: AppCompatActivity){
        activityApp = activity
        activityMain = activity as MainActivity
    }
}