package java.android.cinema.model.local

import java.android.cinema.activity.MainActivity
import java.android.cinema.domen.Movie
import java.android.cinema.model.MoviesCallback
import java.android.cinema.model.RepositoryMovies

class RepositoryMoviesTestImpl: RepositoryMovies {
    override fun getListMovies(stringGenre: String, callback: MoviesCallback) {
        Thread{



            if(stringGenre == "comedy"){
                Thread.sleep(500)

                val movies = mutableListOf<Movie>()
                movies.add(Movie("comedy1"))
                movies.add(Movie("comedy2"))
                movies.add(Movie("comedy3"))
                callback.onResponse(movies)
            }

            if(stringGenre == "fantasy"){
                Thread.sleep(1000)

                val movies = mutableListOf<Movie>()
                movies.add(Movie("fantasy1"))
                movies.add(Movie("fantasy2"))
                movies.add(Movie("fantasy3"))
                callback.onResponse(movies)
            }

            if(stringGenre == "1"){
                Thread.sleep(1500)

                val movies = mutableListOf<Movie>()
                movies.add(Movie("1"))
                movies.add(Movie("1"))
                movies.add(Movie("1"))
                callback.onResponse(movies)
            }

            if(stringGenre == "2"){
                Thread.sleep(2000)

                val movies = mutableListOf<Movie>()
                movies.add(Movie("2"))
                movies.add(Movie("2"))
                movies.add(Movie("2"))
                callback.onResponse(movies)
            }

        }.start()
    }
}