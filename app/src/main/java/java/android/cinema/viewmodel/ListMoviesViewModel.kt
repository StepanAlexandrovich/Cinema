package java.android.cinema.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.android.cinema.PublicSettings
import java.android.cinema.activity.MainActivity
import java.android.cinema.activity.MyApp
import java.android.cinema.domen.Movie
import java.android.cinema.model.MoviesCallback
import java.android.cinema.model.RepositoryMovies
import java.io.IOException

class ListMoviesViewModel():ViewModel() {

    val liveDataLoad =  MutableLiveData<AppState>()

    val liveDates = createLiveDates()

    private fun createLiveDates(): MutableList< MutableLiveData<AppState> > {
        val liveDates = mutableListOf< MutableLiveData<AppState> >()
        repeat(PublicSettings.maxNumberOfGenres){
            liveDates.add(MutableLiveData<AppState>())
        }
        return liveDates
    }

    private var repository: RepositoryMovies? = null

    private fun choiceRepository() {
        when(PublicSettings.mode){
            // local
            PublicSettings.modeTest -> { repository = MyApp.repositoryTest }
            PublicSettings.modeDataBase -> { repository = MyApp.repositoryRoom }

            // internet
            PublicSettings.modeInternetStandard -> { repository = MyApp.repositoryInternet }
            PublicSettings.modeOkHttp   -> { repository = MyApp.repositoryOkHttp }
            PublicSettings.modeRetrofit -> { repository = MyApp.repositoryRetrofit }
        }
    }

    fun sentRequest(){
        choiceRepository()

        repeat(PublicSettings.mode.strings.size){
            liveDataLoad.postValue( AppState.Loading )
            repository?.getListMovies(PublicSettings.mode.strings[it],MoviesCallbackImpl(it));
        }
    }

    inner class MoviesCallbackImpl(private val index: Int): MoviesCallback {
        override fun onResponse(movies: MutableList<Movie>) {
            liveDates[index].postValue( AppState.SuccessData(index,movies) )
        }

        override fun onFailure(exception: IOException) {
            liveDates[index].postValue( AppState.Error(index,exception) )
        }

    }

}