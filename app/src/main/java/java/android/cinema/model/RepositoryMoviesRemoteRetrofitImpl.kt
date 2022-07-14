package java.android.cinema.model

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.android.cinema.model.dto.MoviesDTO
import java.android.cinema.model.retrofit.MoviesAPI
import java.io.IOException

class RepositoryMoviesRemoteRetrofitImpl: RepositoryMoviesRemote {

    override fun getListMovies(callback: LargeSuperCallback) {
        // асинхронный запрос

        val retrofitImpl = Retrofit.Builder()
        retrofitImpl.baseUrl("https://imdb-api.com")
        retrofitImpl.addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        val api = retrofitImpl.build().create(MoviesAPI::class.java)

        api.getMovies().enqueue(object : Callback<MoviesDTO> {
            override fun onResponse(call: Call<MoviesDTO>, response: Response<MoviesDTO>) {
                if(response.isSuccessful&&response.body()!=null){
                    callback.onResponse(response.body()!!)
                }else {
                    callback.onFailure(IOException("403 404")) // подработать
                }
            }
            override fun onFailure(call: Call<MoviesDTO>, t: Throwable) {
                callback.onFailure(t as IOException) //костыль
            }
        })
    }

    override fun getListMovies(genre: String, callback: LargeSuperCallback) {
        // перенесу сюда
    }
}