package java.android.cinema.viewmodel

import java.android.cinema.domen.Movie
import java.android.cinema.model.dto.MoviesDTO

sealed class AppState {
    data class SuccessComedy(val movieList: List<Movie>) : AppState()
    data class SuccessFantasy(val movieList: List<Movie>) : AppState()
    data class SuccessFavorites(val movieList: List<Movie>) : AppState()

    data class SuccessFromInternetOkHttp(val movieList: MoviesDTO) : AppState()
    data class SuccessFromInternetRetrofit(val movieList: MoviesDTO) : AppState()

    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}