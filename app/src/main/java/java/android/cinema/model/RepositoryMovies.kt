package java.android.cinema.model

import java.android.cinema.domen.Movie

fun interface RepositoryMovies {
    fun getListMovies(index:Int):List<Movie>
}