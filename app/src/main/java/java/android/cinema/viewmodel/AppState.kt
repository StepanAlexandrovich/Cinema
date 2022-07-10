package java.android.cinema.viewmodel

import java.android.cinema.domen.Movie

sealed class AppState {
    data class SuccessOne(val movieData: Movie) : AppState()

    data class SuccessComedy(val movieList: List<Movie>) : AppState()
    data class SuccessFantasy(val movieList: List<Movie>) : AppState()
    data class SuccessFavorites(val movieList: List<Movie>) : AppState()
    data class SuccessFromInternet(val movieList: List<Movie>) : AppState()

    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}