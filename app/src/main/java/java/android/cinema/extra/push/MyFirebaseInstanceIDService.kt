package java.android.cinema.extra.push

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.android.cinema.R
import java.android.cinema.activity.MainActivity

class MyFirebaseInstanceIDService:FirebaseMessagingService(){

    val NOTIFICATION_KEY_TITLE = "myTitle"
    val NOTIFICATION_KEY_BODY = "myBody"

    override fun onNewToken(token: String) {
        pushNotification(token,token)
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {

        val data = message.data
        val title = data[NOTIFICATION_KEY_TITLE]
        val body = data[NOTIFICATION_KEY_BODY]

        //if(title.isNullOrEmpty()&&body.isNullOrEmpty()){
            pushNotification("$title","$body")
        //}

        super.onMessageReceived(message)
    }

    val CHANNEL_HIGH_ID = "channel_111"
    val CHANNEL_LOW_ID  = "channel_222"
    val NOTIFICATION_ID = 1

    fun pushNotification(title:String,body:String){

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext,0,intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val notification = NotificationCompat.Builder(this,CHANNEL_HIGH_ID).apply {
            setContentIntent(pendingIntent)
            setContentTitle(title)
            setContentText("fcm")
            setSmallIcon(R.drawable.android_icon)
            priority = NotificationCompat.PRIORITY_MAX
        }

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channelHigh = NotificationChannel(CHANNEL_HIGH_ID,CHANNEL_HIGH_ID,
                NotificationManager.IMPORTANCE_HIGH)
            channelHigh.description = "DESCRIPTION"
            notificationManager.createNotificationChannel(channelHigh)
        }
        notificationManager.notify(NOTIFICATION_ID,notification.build())
    }

}