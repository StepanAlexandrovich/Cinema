package java.android.cinema.model

import java.android.cinema.storage.Movie
import java.android.cinema.storage.Movies

class RepositoryLocalImpl:Repository {
    val movies = Movies()

    override fun getListMovies(): List<Movie> {
        return movies.comedy
    }

    override fun getMovie(): Movie {
        val index = (0 until movies.comedy.size).random()
        return movies.getMovie(0,index)
    }

}