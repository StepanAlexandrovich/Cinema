package java.android.cinema.model.remote.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url
import java.android.cinema.model.remote.dto.MoviesDTO

fun interface MoviesAPI {

    @GET
    fun getMovies(@Url url:String):Call<MoviesDTO>

}