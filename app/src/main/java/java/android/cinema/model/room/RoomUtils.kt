package java.android.cinema.model.room

import java.android.cinema.MyApp
import java.android.cinema.domen.Movie

object RoomUtils {

    fun convertListDaoInMovies(entityList: List<MovieEntity>): MutableList<Movie> {
        val answer = mutableListOf<Movie>()
        entityList.forEach {
            val movie = Movie(it.name)
            movie.urlImage = it.img

            answer.add(movie)
        }

        return answer
    }

    private fun convertMovieToEntity(movie: Movie): MovieEntity {
        return MovieEntity(0,movie.title,movie.urlImage)
    }

    fun addMovie(movie: Movie){
        Thread{
            MyApp.getMovieDatabase().movieDao().insertRoom( RoomUtils.convertMovieToEntity( movie ))
        }.start()
    }

}