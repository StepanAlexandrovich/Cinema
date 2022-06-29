package java.android.cinema.model

import java.android.cinema.storage.Movie

class RepositoryLocalImpl:Repository {

    override fun getListMovies(): List<Movie> {
        return listOf(Movie("Movie",199))
    }

    override fun getMovie(): Movie {
        return Movie("Movie1",1991)
    }
}