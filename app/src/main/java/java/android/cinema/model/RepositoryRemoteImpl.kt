package java.android.cinema.model

import java.android.cinema.storage.Movie
import java.android.cinema.storage.Movies

class RepositoryRemoteImpl:Repository {
    private val movies = Movies()

    override fun getMovie(): Movie {
        // реализовать колбэк
        Thread{
            Thread.sleep(2000L)
        }.start()

        val index = (0 until movies.comedy.size).random()
        return movies.getMovie(0,index)
    }

    override fun getListMovies(): List<Movie> {
        return movies.comedy
    }

    override fun getListMovies(index:Int): List<Movie> {
        // временно
        if(index == 0){ return movies.comedy }
        if(index == 1){ return movies.fantasy }
        if(index == 2){ return movies.favorites }

        return movies.comedy
    }

}