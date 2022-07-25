package java.android.cinema.activity

import android.app.Application
import java.android.cinema.model.RepositoryMovies
import java.android.cinema.model.local.RepositoryMoviesTestImpl
import java.android.cinema.model.remote.okhttp.RepositoryMoviesRemoteOkHttpImpl
import java.android.cinema.model.remote.retrofit.RepositoryMoviesRemoteRetrofitImpl
import java.android.cinema.model.room.RepositoryMoviesLocalRoomImpl

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        myApp = this
    }

    companion object {
        private var myApp: MyApp? = null
        fun getMyApp() = myApp!!

        val repositoryRetrofit:RepositoryMovies = RepositoryMoviesRemoteRetrofitImpl()
        val repositoryOkHttp:RepositoryMovies = RepositoryMoviesRemoteOkHttpImpl()
        val repositoryRoom:RepositoryMovies = RepositoryMoviesLocalRoomImpl()
        val repositoryTest:RepositoryMovies = RepositoryMoviesTestImpl()
    }
}