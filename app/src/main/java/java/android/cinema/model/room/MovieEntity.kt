package java.android.cinema.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_entity_table")
class MovieEntity (
    @PrimaryKey( autoGenerate = true)
    val id: Long,
    val name: String,
    val img: String
)