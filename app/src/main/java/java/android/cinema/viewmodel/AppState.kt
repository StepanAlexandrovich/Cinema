package java.android.cinema.viewmodel

import java.android.cinema.domen.Movie

sealed class AppState {

    // сделаю массив
    data class SuccessData0(val movies: MutableList<Movie>) : AppState()
    data class SuccessData1(val movies: MutableList<Movie>) : AppState()
    data class SuccessData2(val movies: MutableList<Movie>) : AppState()
    data class SuccessData3(val movies: MutableList<Movie>) : AppState()
    data class SuccessData4(val movies: MutableList<Movie>) : AppState()
    data class SuccessData5(val movies: MutableList<Movie>) : AppState()

    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()

}