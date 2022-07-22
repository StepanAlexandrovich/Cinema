package java.android.cinema

import android.app.Application
import androidx.room.Room
import java.android.cinema.model.room.MovieDatabase

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        myApp = this
    }

    companion object {
        private var myApp: MyApp? = null
        private var movieDatabase: MovieDatabase? = null
        fun getMyApp() = myApp!!
        fun getMovieDatabase(): MovieDatabase {
            if (movieDatabase == null) {
                movieDatabase = Room.databaseBuilder(
                    getMyApp(),
                    MovieDatabase::class.java,
                    ROOM_DB_NAME_MOVIE
                )
                    //.allowMainThreadQueries() // TODO HW убрать allowMainThreadQueries
                    .build()
            }
            return movieDatabase!!
        }
    }
}