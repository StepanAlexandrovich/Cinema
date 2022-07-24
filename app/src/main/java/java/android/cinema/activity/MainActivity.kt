package java.android.cinema.activity

import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import java.android.cinema.*
import java.android.cinema.databinding.ActivityMainBinding
import java.android.cinema.domen.Movies
import java.android.cinema.phone.BroadCastReceiverAirPlaneMode
import java.android.cinema.storage.SharedPref
import java.android.cinema.utils.PrintVisible
import java.android.cinema.view.ContentProviderFragment
import java.android.cinema.view.GeolocationFragment
import java.android.cinema.view.mainscreen.ListMoviesFragment
import java.android.cinema.view.utilsToView.Navigation

class MainActivity : AppCompatActivity(){

    companion object{
        lateinit var activityApp:AppCompatActivity
        lateinit var activityMain:MainActivity
        val localMovies = Movies()
    }

    val infiniteThread = InfiniteThread()
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        this.openOptionsMenu()

        activityApp = this
        activityMain = this

        localMovies.resetAll()

        infiniteThread.start()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SharedPref.read()

        notificationAirplaneMode()


    }

    private fun notificationAirplaneMode(){
        val receiver = BroadCastReceiverAirPlaneMode()
        registerReceiver(receiver, IntentFilter("android.intent.action.AIRPLANE_MODE"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_phones -> {
                Navigation.createFragment(this, R.id.container, ContentProviderFragment())
                true
            }
            R.id.menu_main_screen -> {
                Navigation.createFragment(this, R.id.container, ListMoviesFragment.newInstance())
                true
            }
            R.id.menu_geolocation -> {
                Navigation.createFragment(this, R.id.container, GeolocationFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}