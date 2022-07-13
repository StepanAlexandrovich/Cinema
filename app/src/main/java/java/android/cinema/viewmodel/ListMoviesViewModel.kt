package java.android.cinema.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.android.cinema.model.RepositoryLocalImpl
import java.android.cinema.model.RepositoryRemoteImpl

class ListMoviesViewModel():ViewModel() {
    private var repositoryLocal = RepositoryLocalImpl()

    private val liveDataComedy = MutableLiveData<AppState>()
    private val liveDataFantasy = MutableLiveData<AppState>()
    private val liveDataFavorites = MutableLiveData<AppState>()

    private var repositoryRemote = RepositoryRemoteImpl()
    private val liveDataFromInternet = MutableLiveData<AppState>()

    fun getLiveDataComedy():MutableLiveData<AppState>{
        return liveDataComedy
    }

    fun getLiveDataFantasy():MutableLiveData<AppState>{
        return liveDataFantasy
    }

    fun getLiveDataFavorites():MutableLiveData<AppState>{
        return liveDataFavorites
    }

    fun getLiveDataFromInternet():MutableLiveData<AppState>{
        return liveDataFromInternet
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun sentRequest(){

        liveDataComedy.postValue( AppState.SuccessComedy(repositoryLocal.getListMovies(0)) )
        liveDataFantasy.postValue( AppState.SuccessFantasy(repositoryLocal.getListMovies(1)) )
        liveDataFavorites.postValue( AppState.SuccessFavorites(repositoryLocal.getListMovies(2)) )
    }

}