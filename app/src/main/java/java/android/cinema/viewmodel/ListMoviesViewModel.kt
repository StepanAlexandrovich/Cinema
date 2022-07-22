package java.android.cinema.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.android.cinema.PublicSettings
import java.android.cinema.domen.Movie
import java.android.cinema.model.MoviesCallback
import java.android.cinema.model.RepositoryMovies
import java.android.cinema.model.remote.okhttp.RepositoryMoviesRemoteOkHttpImpl
import java.android.cinema.model.remote.retrofit.RepositoryMoviesRemoteRetrofitImpl
import java.android.cinema.model.room.RepositoryMoviesLocalRoomImpl
import java.io.IOException

class ListMoviesViewModel():ViewModel() {

    private var repository: RepositoryMovies? = null

    private fun choiceRepository() {
        when(PublicSettings.mode){

            //PublicSettings.modeDataBase -> { repository = RepositoryMoviesDataBaseImpl() }

            PublicSettings.modeDataBase -> { repository = RepositoryMoviesLocalRoomImpl() }

            PublicSettings.modeOkHttp   -> { repository = RepositoryMoviesRemoteOkHttpImpl() }

            PublicSettings.modeRetrofit -> { repository = RepositoryMoviesRemoteRetrofitImpl() }

        }
    }

    val liveDates = mutableListOf< MutableLiveData<AppState> >()

    fun liveDatesInit(times:Int){
        repeat(times){
            liveDates.add( MutableLiveData<AppState>() )
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
        // будет только одна строчка

        if(index==0){ liveDates[index].postValue( AppState.SuccessData0(movies) ) }
        if(index==1){ liveDates[index].postValue( AppState.SuccessData1(movies) ) }
        if(index==2){ liveDates[index].postValue( AppState.SuccessData2(movies) ) }
        if(index==3){ liveDates[index].postValue( AppState.SuccessData3(movies) ) }
        if(index==4){ liveDates[index].postValue( AppState.SuccessData4(movies) ) }
        if(index==5){ liveDates[index].postValue( AppState.SuccessData5(movies) ) }
    }

    private var funOnOnFailure = fun(index:Int,e: IOException){
        liveDates[index].postValue( AppState.Error(e) )
    }



}