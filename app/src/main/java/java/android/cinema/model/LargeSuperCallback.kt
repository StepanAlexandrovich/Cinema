package java.android.cinema.model

import java.android.cinema.model.dto.MoviesDTO
import java.io.IOException

interface LargeSuperCallback {
    fun onResponse(moviesDTO: MoviesDTO)
    fun onFailure(exception: IOException)
}