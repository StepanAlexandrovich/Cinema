package java.android.cinema.model.local

import java.android.cinema.activity.MainActivity
import java.android.cinema.domen.Movie
import java.android.cinema.model.MoviesCallback
import java.android.cinema.model.RepositoryMovies

class RepositoryMoviesTestImpl: RepositoryMovies {
    override fun getListMovies(stringGenre: String, callback: MoviesCallback) {
        Thread{

            Thread.sleep(1000)

            if(stringGenre == "comedy"){
                val movies = mutableListOf<Movie>()
                movies.add(Movie("comedy1"))
                movies.add(Movie("comedy2"))
                movies.add(Movie("comedy3"))
                callback.onResponse(movies)
            }

            if(stringGenre == "fantasy"){
                val movies = mutableListOf<Movie>()
                movies.add(Movie("fantasy1"))
                movies.add(Movie("fantasy2"))
                movies.add(Movie("fantasy3"))
                callback.onResponse(movies)
            }

        }.start()
    }
}