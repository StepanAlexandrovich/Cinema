package java.android.cinema.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.android.cinema.domen.Movie
import java.android.cinema.model.RepositoryLocalImpl
import java.lang.IllegalStateException

class ListMoviesViewModel():ViewModel() {

    private var isConnect = false // временно

    private var repository = RepositoryLocalImpl()

    private val liveDataComedy = MutableLiveData<AppState>()
    private val liveDataFantasy = MutableLiveData<AppState>()
    private val liveDataFavorites = MutableLiveData<AppState>()

    fun getLiveDataComedy():MutableLiveData<AppState>{
        //choiceRepository()
        return liveDataComedy
    }

    fun getLiveDataFantasy():MutableLiveData<AppState>{
        //choiceRepository()
        return liveDataFantasy
    }

    fun getLiveDataFavorites():MutableLiveData<AppState>{
        //choiceRepository()
        return liveDataFavorites
    }


    private fun choiceRepository() = if(isConnection()){
        //repository = RepositoryRemoteImpl()
    }else{
        //repository = RepositoryLocalImpl()
    }

    fun sentRequest(){
        liveDataComedy.value = AppState.Loading

        if((0..3).random() == 1){
            liveDataComedy.postValue( AppState.Error( IllegalStateException("Что то пошло не по плану")))
        }else{
            repository.movies.addMovie(1, Movie("test")) // test
            repository.movies.addMovie(2, Movie("test")) // test

            liveDataComedy.postValue( AppState.SuccessComedy(repository.getListMovies(0)) )
            liveDataFantasy.postValue( AppState.SuccessFantasy(repository.getListMovies(1)) )
            liveDataFavorites.postValue( AppState.SuccessFavorites(repository.getListMovies(2)) )
        }

    }

    private fun isConnection(): Boolean {
        //isConnect = !isConnect
        //return isConnect
        return true
    }

}