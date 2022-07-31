package java.android.cinema.activity

import android.app.Application
import java.android.cinema.model.RepositoryMovies
import java.android.cinema.model.test.RepositoryMoviesTestImpl
import java.android.cinema.model.remote.okhttp.RepositoryMoviesRemoteOkHttpImpl
import java.android.cinema.model.remote.retrofit.RepositoryMoviesRemoteRetrofitImpl
import java.android.cinema.model.remote.standart.RepositoryMoviesRemoteStandardImpl
import java.android.cinema.model.room.RepositoryMoviesLocalRoomImpl

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        myApp = this
    }

    companion object {
        private var myApp: MyApp? = null
        fun getMyApp() = myApp!!

        val repositoryTest:RepositoryMovies = RepositoryMoviesTestImpl()
        val repositoryRoom:RepositoryMovies = RepositoryMoviesLocalRoomImpl()
        val repositoryInternet:RepositoryMovies = RepositoryMoviesRemoteStandardImpl()
        val repositoryOkHttp:RepositoryMovies = RepositoryMoviesRemoteOkHttpImpl()
        val repositoryRetrofit:RepositoryMovies = RepositoryMoviesRemoteRetrofitImpl()

    }
}