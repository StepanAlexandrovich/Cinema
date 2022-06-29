package java.android.cinema.model

import java.android.cinema.storage.Movie
import java.android.cinema.storage.Movies

class RepositoryRemoteImpl:Repository {
    val movies = Movies()

    override fun getListMovies(): List<Movie> {
        return movies.comedy
    }

    override fun getMovie(): Movie {
        // реализовать колбэк
        Thread{
            Thread.sleep(2000L)
        }.start()

        val index = (0 until movies.comedy.size).random()
        return movies.getMovie(0,index)
    }

}