package java.android.cinema.model

import java.android.cinema.domen.Movie
import java.io.IOException

interface MoviesCallback {
    fun onResponse(movies: MutableList<Movie>)
    fun onFailure(exception: IOException)
}