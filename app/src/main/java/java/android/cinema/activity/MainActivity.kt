package java.android.cinema.activity

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.android.cinema.MyThread
import java.android.cinema.R
import java.android.cinema.phone.BroadCastReceiverAirPlaneMode
import java.android.cinema.databinding.ActivityMainBinding
import java.android.cinema.view.mainscreen.ListMoviesFragment
import java.android.cinema.view.utilsToView.Navigation

class MainActivity : AppCompatActivity(){

    val myThread = MyThread()

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ReferenceMain.init(this)

        myThread.start()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            Navigation.createFragment(this, R.id.container, ListMoviesFragment.newInstance())
        }

        notificationAirplaneMode()
    }

    private fun notificationAirplaneMode(){
        val receiver = BroadCastReceiverAirPlaneMode()
        registerReceiver(receiver, IntentFilter("android.intent.action.AIRPLANE_MODE"))
    }

}