package java.android.cinema.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // FIXME создать конфликт
    fun insertRoom(movieEntity: MovieEntity)

    @Query("SELECT * FROM movie_entity_table")
    fun getMovieAll():List<MovieEntity>




    // НА ВСЯКИЙ СЛУЧАЙ
    @Query("INSERT INTO movie_entity_table (name,img) VALUES(:name,:img)")
    fun insertNative1(name:String,img:String)

    @Query("INSERT INTO movie_entity_table (id,name) VALUES(:id,:name)")
    fun insertNative2(id:Long,name:String)

    //@Query("SELECT * FROM weather_entity_table WHERE lat=:mLat AND lon=:mLon")
    //fun getMovieByLocation(mLat:Double,mLon:Double):List<MovieEntity>
}