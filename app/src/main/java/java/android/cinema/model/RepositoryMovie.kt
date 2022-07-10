package java.android.cinema.model

import java.android.cinema.domen.Movie

fun interface RepositoryMovie {
    fun getMovie(indexGenre: Int,indexMovie: Int):Movie
}