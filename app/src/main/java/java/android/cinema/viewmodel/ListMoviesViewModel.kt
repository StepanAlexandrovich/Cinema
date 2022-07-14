package java.android.cinema.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.android.cinema.Genre
import java.android.cinema.model.LargeSuperCallback
import java.android.cinema.model.RepositoryMoviesLocalImpl
import java.android.cinema.model.RepositoryMoviesRemoteOkHttpImpl
import java.android.cinema.model.RepositoryMoviesRemoteRetrofitImpl
import java.android.cinema.model.dto.MoviesDTO
import java.io.IOException

class ListMoviesViewModel():ViewModel() {
    private var repositoryMoviesLocal = RepositoryMoviesLocalImpl()

    private var repositoryMoviesRemoteOkHttp = RepositoryMoviesRemoteOkHttpImpl()
    private var repositoryMoviesRemoteRetrofit = RepositoryMoviesRemoteRetrofitImpl()

    private val liveDataComedy = MutableLiveData<AppState>()
    private val liveDataFantasy = MutableLiveData<AppState>()
    private val liveDataFavorites = MutableLiveData<AppState>()

    private val liveDataFromInternetOkHttp = MutableLiveData<AppState>()
    private val liveDataFromInternetRetrofit = MutableLiveData<AppState>()

    fun getLiveDataComedy():MutableLiveData<AppState>{
        return liveDataComedy
    }

    fun getLiveDataFantasy():MutableLiveData<AppState>{
        return liveDataFantasy
    }

    fun getLiveDataFavorites():MutableLiveData<AppState>{
        return liveDataFavorites
    }

    fun getLiveDataFromInternetOkHttp():MutableLiveData<AppState>{
        return liveDataFromInternetOkHttp
    }

    fun getLiveDataFromInternetRetrofit():MutableLiveData<AppState>{
        return liveDataFromInternetRetrofit
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun sentRequest(){

        liveDataComedy.postValue( AppState.SuccessComedy(repositoryMoviesLocal.getListMovies(0)) )
        //liveDataFantasy.postValue( AppState.SuccessFantasy(repositoryMoviesLocal.getListMovies(1)) )
        //liveDataFavorites.postValue( AppState.SuccessFavorites(repositoryLocal.getListMovies(2)) )

        repositoryMoviesRemoteOkHttp.getListMovies(Genre.genre,callbackOkHttp)
        repositoryMoviesRemoteRetrofit.getListMovies(callbackRetrofit)
    }

    // потом оставлю один колбэк на рэтрофит
    private val callbackOkHttp = object : LargeSuperCallback{
        override fun onResponse(moviesDTO: MoviesDTO) {
            liveDataFromInternetOkHttp.postValue( AppState.SuccessFromInternetOkHttp(moviesDTO) )
        }

        override fun onFailure(e: IOException) {
            liveDataFromInternetOkHttp.postValue( AppState.Error(e) )
        }
    }

    private val callbackRetrofit = object : LargeSuperCallback{
        override fun onResponse(moviesDTO: MoviesDTO) {
            liveDataFromInternetRetrofit.postValue( AppState.SuccessFromInternetRetrofit(moviesDTO) )
        }

        override fun onFailure(e: IOException) {
            liveDataFromInternetRetrofit.postValue( AppState.Error(e) )
        }
    }

}