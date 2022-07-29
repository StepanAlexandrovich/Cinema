package java.android.cinema.model.room

import java.android.cinema.domen.Movie
import java.android.cinema.model.MoviesCallback
import java.android.cinema.model.RepositoryMovies

class RepositoryMoviesLocalRoomImpl:RepositoryMovies {
    override fun getListMovies(stringGenre: String, callback: MoviesCallback) {
        var movies:MutableList<Movie> = mutableListOf<Movie>()

        Thread{
            when(stringGenre){
                "comedy"    -> { movies = RoomUtils.convertListDaoInMovies(RoomUtils.getMovieDataBase(0).movieDao().getMovieAll()) }
                "fantasy"   -> { movies = RoomUtils.convertListDaoInMovies(RoomUtils.getMovieDataBase(1).movieDao().getMovieAll()) }
                "animation" -> { movies = RoomUtils.convertListDaoInMovies(RoomUtils.getMovieDataBase(2).movieDao().getMovieAll()) }
            }
            callback.onResponse(movies)
        }.start()

        // не работает ???
        //MainActivity.activityMain.infiniteThread.handler?.let {
            //val movies = RoomUtils.convertListDaoInMovies(MyApp.getMovieDatabase().movieDao().getMovieAll())
            //callback.onResponse(movies)
        //}

    }

    private fun convertHistoryEntityToMovie(entityList: List<MovieEntity>): MutableList<Movie> {
         val answer = mutableListOf<Movie>()
         entityList.forEach {
             answer.add(Movie(it.name))
         }

        return answer
    }

    private fun convertMovieToEntity(movie: Movie): MovieEntity {
        return MovieEntity(0, movie.title,movie.urlImage)
    }
}