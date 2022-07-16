package java.android.cinema.model

import java.android.cinema.domen.Movies

class RepositoryMoviesLocalImpl:RepositoryMoviesLocal {
    private val movies = Movies()

    override fun getGenre(index: Int): Movies.Genre{
        return movies.genres[index]
    }

}