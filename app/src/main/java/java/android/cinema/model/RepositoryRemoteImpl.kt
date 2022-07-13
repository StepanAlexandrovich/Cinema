package java.android.cinema.model

import java.android.cinema.domen.Movie

class RepositoryRemoteImpl:RepositoryMovie,RepositoryMovies {
    private var movies = mutableListOf<Movie>()


    override fun getMovie(indexGenre: Int, indexMovie: Int): Movie {
        return movies[0]
    }

    override fun getListMovies(index: Int): List<Movie> {
        return movies
    }

    override fun getListMovies(): List<Movie> {
        return movies
    }





}