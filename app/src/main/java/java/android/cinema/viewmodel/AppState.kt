package java.android.cinema.viewmodel

import java.android.cinema.domen.Movies
import java.android.cinema.model.dto.MoviesDTO

sealed class AppState {
    data class SuccessComedy(val genre: Movies.Genre) : AppState()
    data class SuccessFantasy(val genre: Movies.Genre) : AppState()
    data class SuccessFavorites(val genre: Movies.Genre) : AppState()

    data class SuccessFromInternetRetrofit1(val movieList: MoviesDTO) : AppState()
    data class SuccessFromInternetRetrofit2(val movieList: MoviesDTO) : AppState()

    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}