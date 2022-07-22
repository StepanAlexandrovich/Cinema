package java.android.cinema.activity

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.android.cinema.*
import java.android.cinema.phone.BroadCastReceiverAirPlaneMode
import java.android.cinema.databinding.ActivityMainBinding
import java.android.cinema.domen.Movies
import java.android.cinema.model.room.RoomUtils
import java.android.cinema.storage.SharedPref
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

        activityApp = this
        activityMain = this

        //CreateMovies.create(localMovies)
        //CreateMovies.createDefault(localMovies)
        localMovies.resetAll()

        //val movieEntity: MovieEntity = RoomСonversion.convertMovieToEntity(Movie("jnjnj"))

        //PrintVisible.printLong(movieEntity.name)
        //MyApp.getMyApp().onCreate()
        //PrintVisible.printLong(MyApp.getMovieDatabase().toString())

        //MyApp.getMovieDatabase().movieDao().insertRoom( RoomUtils.convertMovieToEntity(Movie("Movie1")))
        //MyApp.getMovieDatabase().movieDao().insertRoom( RoomСonversion.convertMovieToEntity(Movie("Movie2")))

        //val movies = RoomСonversion.convertListDaoInMovies(MyApp.getMovieDatabase().movieDao().getMovieAll())
        //PrintVisible.printLong("${movies[2].title}__${movies[3].title}" )

        infiniteThread.start()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            Navigation.createFragment(this, R.id.container, ListMoviesFragment.newInstance())
        }

        SharedPref.read()

        notificationAirplaneMode()
    }

    private fun notificationAirplaneMode(){
        val receiver = BroadCastReceiverAirPlaneMode()
        registerReceiver(receiver, IntentFilter("android.intent.action.AIRPLANE_MODE"))
    }

}