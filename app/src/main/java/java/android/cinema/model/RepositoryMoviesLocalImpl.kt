package java.android.cinema.model

import java.android.cinema.activity.MainActivity
import java.android.cinema.domen.Movies

class RepositoryMoviesLocalImpl:RepositoryMoviesLocal {

    override fun getGenre(index: Int): Movies.Genre{
        return MainActivity.localMovies.genres[index]
    }

}