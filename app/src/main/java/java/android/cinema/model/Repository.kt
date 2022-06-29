package java.android.cinema.model

import java.android.cinema.storage.Movie

interface Repository {
    fun getListMovies():List<Movie>
    fun getMovie():Movie
}