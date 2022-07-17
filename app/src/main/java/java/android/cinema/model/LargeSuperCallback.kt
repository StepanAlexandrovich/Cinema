package java.android.cinema.model

import java.android.cinema.model.dto.MoviesDTO
import java.io.IOException

interface LargeSuperCallback {
    fun onResponse(indexGenre:Int, moviesDTO: MoviesDTO)
    fun onFailure(indexGenre:Int,exception: IOException)
}