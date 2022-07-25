package java.android.cinema.model.room

import androidx.room.Room
import java.android.cinema.ROOM_DB_NAME_ANIMATION
import java.android.cinema.ROOM_DB_NAME_COMEDY
import java.android.cinema.ROOM_DB_NAME_FANTASY
import java.android.cinema.activity.MyApp
import java.android.cinema.domen.Movie
import java.android.cinema.utils.PrintVisible

object RoomUtils {

    private val dataBases = arrayOfNulls<MovieDatabase>(3)
    private val stringKeys = listOf<String>(ROOM_DB_NAME_COMEDY,ROOM_DB_NAME_FANTASY,ROOM_DB_NAME_ANIMATION )

    private fun createDataBase(key:String):MovieDatabase{
         val database = Room.databaseBuilder(
            MyApp.getMyApp(),
            MovieDatabase::class.java,
            key
        ).build()
        return database
    }

    fun getMovieDataBase(index:Int):MovieDatabase{
        if(dataBases[index] == null){ dataBases[index] = createDataBase(stringKeys[index]) };
        return dataBases[index]!!
    }

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

    fun addMovie(indexGenre:Int, movie: Movie){
        Thread{
            getMovieDataBase(indexGenre).movieDao().insertRoom( RoomUtils.convertMovieToEntity( movie ))
            // PrintVisible.printShortThread("movie added")  // если раскомитить , то прога падает с ошибкой после того как из фрагмента Details Добавил фильм и попбэкстэком возвращаешся назад ???????????
        }.start()
    }

}