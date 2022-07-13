package java.android.cinema.activity

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import java.android.cinema.phone.BroadCastReceiverAirPlaneMode
import java.android.cinema.R
import java.android.cinema.databinding.ActivityMainBinding
import java.android.cinema.view.mainscreen.ListMoviesFragment
import java.android.cinema.view.utilsToView.Navigation

class MainActivity : AppCompatActivity(){

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Reference.activity = this

        if(Settings.Global.getInt(contentResolver,Settings.Global.AIRPLANE_MODE_ON,0)!=0){

        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            Navigation.createFragment(this,R.id.container, ListMoviesFragment.newInstance())
        }

        //startService(Intent(this,MyService::class.java).apply {
            //putExtra(BUNDLE_KEY,"Hello")
        //})

        //val receiver = MyBroadCastReceiver()
        //registerReceiver(receiver, IntentFilter("android.intent.action.AIRPLANE_MODE"))
        //registerReceiver(receiver, IntentFilter("myAction"))

        fun1()


    }

    fun fun1(){
        val receiver = BroadCastReceiverAirPlaneMode()
        registerReceiver(receiver, IntentFilter("android.intent.action.AIRPLANE_MODE"))
        //registerReceiver(receiver, IntentFilter("myAction"))
    }





}