package java.android.cinema.model

import java.android.cinema.domen.Movie
import java.android.cinema.domen.Movies

class RepositoryLocalImpl:RepositoryMovie,RepositoryMovies {
    val movies = Movies()

    override fun getMovie(indexGenre: Int, indexMovie: Int): Movie {
        val listMovie:List<Movie> = getListMovies(indexGenre)
        return listMovie[indexMovie]
    }

    override fun getListMovies(index: Int): List<Movie> {
        // временно
        if(index == 0){ return movies.comedy }
        if(index == 1){ return movies.fantasy }
        if(index == 2){ return movies.animated }

        return movies.comedy
    }

}