package java.android.cinema.model.retrofit

import retrofit2.Call
import retrofit2.http.GET
import java.android.cinema.BuildConfig
import java.android.cinema.model.dto.MoviesDTO

interface MoviesAPI {

    @GET("/en/API/SearchMovie/${BuildConfig.API_KEY}/inception 2010")
    fun getMovies():Call<MoviesDTO>

}