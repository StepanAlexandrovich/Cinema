package java.android.cinema.model.room

import java.android.cinema.MyApp
import java.android.cinema.domen.Movie
import java.android.cinema.model.MoviesCallback
import java.android.cinema.model.RepositoryMovies

class RepositoryMoviesLocalRoomImpl:RepositoryMovies {
    override fun getListMovies(stringGenre: String, callback: MoviesCallback) {

        // добавить реализацию для поля stringGenge

        Thread{
            val movies = RoomUtils.convertListDaoInMovies(MyApp.getMovieDatabase().movieDao().getMovieAll())
            callback.onResponse(movies)
        }.start()

        // не работает ???
        //MainActivity.activityMain.infiniteThread.handler?.let {
            //val movies = RoomСonversion.convertListDaoInMovies(MyApp.getMovieDatabase().movieDao().getMovieAll())
            //callback.onResponse(movies)
        //}

    }

    //override fun getWeather(city: City, callback: CommonWeatherCallback) {
        //callback.onResponse(MyApp.getWeatherDatabase().weatherDao().getWeatherByLocation(city.lat,city.lon).let{
            //convertHistoryEntityToWeather(it).last()
        //})
    //}

    //fun addMovie(movie: Movie) {
        //MyApp.getMovieDatabase().movieDao().insertRoom(convertWeatherToEntity(weather))
    //}

    //override fun getWeatherAll(callback: CommonListWeatherCallback) {
        //callback.onResponse(convertHistoryEntityToWeather(MyApp.getWeatherDatabase().weatherDao().getWeatherAll()))
    //}

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