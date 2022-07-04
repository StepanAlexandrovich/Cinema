package java.android.cinema.model

import java.android.cinema.domen.Movie
import java.android.cinema.domen.Movies

class RepositoryRemoteImpl:RepositoryMovie {
    private val movies = Movies()

    override fun getMovie(indexGenre: Int, indexMovie: Int): Movie {
        TODO("Not yet implemented")
    }

}