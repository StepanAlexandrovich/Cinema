package java.android.cinema.model.remote.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.android.cinema.BuildConfig
import java.android.cinema.model.MoviesCallback
import java.android.cinema.model.remote.dto.ConvertDTOinMovies
import java.android.cinema.model.RepositoryMovies
import java.android.cinema.model.remote.dto.MoviesDTO
import java.io.IOException

class RepositoryMoviesRemoteRetrofitImpl: RepositoryMovies {

    override fun getListMovies(stringGenre:String, callback: MoviesCallback) {
        val retrofitImpl = Retrofit.Builder()
        retrofitImpl.baseUrl("https://imdb-api.com")
        retrofitImpl.addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        val api = retrofitImpl.build().create(MoviesAPI::class.java)

        val key: String = "k_71rwtkzg"  //запаска
        //api.getMovies("/en/API/SearchMovie/${BuildConfig.API_KEY}/${stringGenre}").enqueue(object : Callback<MoviesDTO> {
        api.getMovies("/en/API/SearchMovie/${key}/${stringGenre}").enqueue(object : Callback<MoviesDTO> {
            override fun onResponse(call: Call<MoviesDTO>, response: Response<MoviesDTO>) {
                if(response.isSuccessful&&response.body()!=null){
                    callback.onResponse(ConvertDTOinMovies.returnList(response.body()!!))
                }else {
                    callback.onFailure(IOException("403 404")) // подработать
                }
            }
            override fun onFailure(call: Call<MoviesDTO>, t: Throwable) {
                callback.onFailure(t as IOException) //костыль
            }
        })
    }

}