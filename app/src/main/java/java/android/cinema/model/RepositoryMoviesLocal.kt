package java.android.cinema.model

import java.android.cinema.domen.Movie

interface RepositoryMoviesLocal {
    fun getMovie(indexGenre: Int, indexMovie: Int): Movie
    fun getListMovies(index: Int): List<Movie>
}