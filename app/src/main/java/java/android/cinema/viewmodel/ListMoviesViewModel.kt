package java.android.cinema.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.android.cinema.model.LargeSuperCallback
import java.android.cinema.model.RepositoryMoviesLocalImpl
import java.android.cinema.model.RepositoryMoviesRemoteRetrofitImpl
import java.android.cinema.model.dto.MoviesDTO
import java.io.IOException

class ListMoviesViewModel():ViewModel() {
    private var repositoryMoviesLocal = RepositoryMoviesLocalImpl()

    private var repositoryMoviesRemoteRetrofit = RepositoryMoviesRemoteRetrofitImpl()

    val liveDataComedy = MutableLiveData<AppState>()
    val liveDataFantasy = MutableLiveData<AppState>()
    val liveDataFavorites = MutableLiveData<AppState>()

    val liveDataFromInternetRetrofit1 = MutableLiveData<AppState>()
    val liveDataFromInternetRetrofit2 = MutableLiveData<AppState>()

    @RequiresApi(Build.VERSION_CODES.N)
    fun sentRequest(){
        var indexLocal = 0;
        liveDataComedy.postValue( AppState.SuccessComedy(repositoryMoviesLocal.getGenre(indexLocal++)) )
        liveDataFantasy.postValue( AppState.SuccessFantasy(repositoryMoviesLocal.getGenre(indexLocal++)) )
        liveDataFavorites.postValue( AppState.SuccessFavorites(repositoryMoviesLocal.getGenre(indexLocal++)) )

        var indexRemote = 0;
        repositoryMoviesRemoteRetrofit.getListMovies(indexRemote++,callbackRetrofit);
        repositoryMoviesRemoteRetrofit.getListMovies(indexRemote++,callbackRetrofit);
    }

    private val callbackRetrofit = object : LargeSuperCallback{
        override fun onResponse(indexGenre:Int,moviesDTO: MoviesDTO) {
            funOnOnResponse(indexGenre,moviesDTO)
        }

        override fun onFailure(indexGenre:Int,e: IOException) {
            funOnOnFailure(indexGenre,e)
        }
    }

    private var funOnOnResponse = fun(indexGenre:Int, moviesDTO:MoviesDTO){
        if(indexGenre==0){ liveDataFromInternetRetrofit1.postValue( AppState.SuccessFromInternetRetrofit1(moviesDTO) ) }
        if(indexGenre==1){ liveDataFromInternetRetrofit2.postValue( AppState.SuccessFromInternetRetrofit2(moviesDTO) ) }
    }

    private var funOnOnFailure = fun(indexGenre:Int,e: IOException){
        if(indexGenre==0){ liveDataFromInternetRetrofit1.postValue( AppState.Error(e) ) }
        if(indexGenre==1){ liveDataFromInternetRetrofit2.postValue( AppState.Error(e) ) }
    }

}