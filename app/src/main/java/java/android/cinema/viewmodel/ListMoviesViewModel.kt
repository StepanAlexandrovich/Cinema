package java.android.cinema.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.android.cinema.PublicSettings
import java.android.cinema.domen.Movie
import java.android.cinema.model.MoviesCallback
import java.android.cinema.model.RepositoryMovies
import java.android.cinema.model.local.RepositoryMoviesTestImpl
import java.android.cinema.model.remote.okhttp.RepositoryMoviesRemoteOkHttpImpl
import java.android.cinema.model.remote.retrofit.RepositoryMoviesRemoteRetrofitImpl
import java.android.cinema.model.room.RepositoryMoviesLocalRoomImpl
import java.io.IOException

class ListMoviesViewModel():ViewModel() {

    val liveData =  MutableLiveData<AppState>()
    private var repository: RepositoryMovies? = null

    private fun choiceRepository() {
        when(PublicSettings.mode){

            PublicSettings.modeTest -> { repository = RepositoryMoviesTestImpl() }

            PublicSettings.modeDataBase -> { repository = RepositoryMoviesLocalRoomImpl() }

            PublicSettings.modeOkHttp   -> { repository = RepositoryMoviesRemoteOkHttpImpl() }

            PublicSettings.modeRetrofit -> { repository = RepositoryMoviesRemoteRetrofitImpl() }

        }
    }

    fun sentRequest(){
        choiceRepository()

        repeat(PublicSettings.mode!!.strings.size){
            repository?.getListMovies(PublicSettings.mode!!.strings[it],MoviesCallbackImpl(it));
        }
    }

    inner class MoviesCallbackImpl(private val index: Int): MoviesCallback {
        override fun onResponse(movies: MutableList<Movie>) {
            funOnOnResponse(index,movies)
        }

        override fun onFailure(exception: IOException) {
            funOnOnFailure(index,exception)
        }

    }

    private var funOnOnResponse = fun(index:Int, movies: MutableList<Movie>){
        liveData.postValue( AppState.SuccessData(index,movies) )
    }

    private var funOnOnFailure = fun(index:Int,e: IOException){
        // обработать индекс
        liveData.postValue( AppState.Error(e) )
    }



}