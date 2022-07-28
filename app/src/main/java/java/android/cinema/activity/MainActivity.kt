package java.android.cinema.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import java.android.cinema.R
import java.android.cinema.databinding.ActivityMainBinding
import java.android.cinema.domen.Movies
import java.android.cinema.extra.receiver.BroadCastReceiverAirPlaneMode
import java.android.cinema.save_settings.SharedPref
import java.android.cinema.view.view_movies.ListMoviesFragment
import java.android.cinema.view.utilsToView.Navigation

class MainActivity : AppCompatActivity(){

    companion object{
        val localMovies = Movies()
    }

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        localMovies.resetAll()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SharedPref.read(this)
        notificationAirplaneMode()

        if(savedInstanceState == null){
            Navigation.createFragment(this, R.id.container, ListMoviesFragment.newInstance())
        }

        //pushNotification("not","not")
    }

    val CHANNEL_HIGH_ID = "channel_111"
    val CHANNEL_LOW_ID  = "channel_222"
    val NOTIFICATION_ID = 1

    fun pushNotification(title:String,body:String){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        //intent = PendingIntent() // TODO HW
        val intent = Intent(applicationContext,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext,0,intent,PendingIntent.FLAG_CANCEL_CURRENT)

        val notification = NotificationCompat.Builder(this,CHANNEL_HIGH_ID).apply {
            setContentIntent(pendingIntent)
            setContentTitle(title)
            setContentText("njnj")
            setSmallIcon(R.drawable.android_icon)
            priority = NotificationCompat.PRIORITY_MAX
        }

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channelHigh = NotificationChannel(CHANNEL_HIGH_ID,CHANNEL_HIGH_ID,NotificationManager.IMPORTANCE_HIGH)
            channelHigh.description = "DESCRIPTION"
            notificationManager.createNotificationChannel(channelHigh)
        }
        notificationManager.notify(NOTIFICATION_ID,notification.build())
    }

    private fun notificationAirplaneMode(){
        val receiver = BroadCastReceiverAirPlaneMode()
        registerReceiver(receiver, IntentFilter("android.intent.action.AIRPLANE_MODE"))
    }


}