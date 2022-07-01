package java.android.cinema.model

import java.android.cinema.storage.Movie

interface Repository {

    fun getMovie():Movie
    fun getListMovies():List<Movie>
    fun getListMovies(index:Int):List<Movie>

}