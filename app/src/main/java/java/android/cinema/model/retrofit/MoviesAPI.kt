package java.android.cinema.model.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url
import java.android.cinema.model.dto.MoviesDTO

interface MoviesAPI {

    @GET
    fun getMovies(@Url url:String):Call<MoviesDTO>

}