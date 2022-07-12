package java.android.cinema.model

import java.android.cinema.domen.Movie

interface RepositoryMovies {
    fun getListMovies(index:Int):List<Movie>
    fun getListMovies():List<Movie>
}