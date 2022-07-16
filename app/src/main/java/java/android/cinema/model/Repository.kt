package java.android.cinema.model

import java.android.cinema.domen.Movies

interface RepositoryMoviesLocal {
    fun getGenre(index: Int): Movies.Genre
}

interface RepositoryMoviesRemote {
    fun getListMovies(indexGenre: Int, callback: LargeSuperCallback)
}