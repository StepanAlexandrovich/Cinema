package java.android.cinema.viewmodel

import java.android.cinema.domen.Movie

sealed class AppState {
    data class SuccessData(val index:Int,val movies: MutableList<Movie>) : AppState()
    data class Error(val index:Int,val error: Throwable) : AppState()
    object Loading : AppState()
}