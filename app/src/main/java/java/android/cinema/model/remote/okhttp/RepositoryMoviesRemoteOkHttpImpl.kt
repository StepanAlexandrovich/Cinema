package java.android.cinema.model.remote.okhttp

import com.google.gson.Gson
import okhttp3.*

import java.android.cinema.BuildConfig
import java.android.cinema.model.MoviesCallback
import java.android.cinema.model.RepositoryMovies

import java.android.cinema.model.remote.dto.MoviesDTO
import java.android.cinema.model.remote.dto.ConvertDTOinMovies
import java.io.IOException

class RepositoryMoviesRemoteOkHttpImpl: RepositoryMovies {

    override fun getListMovies(stringGenre: String, callback: MoviesCallback) {
        val client = OkHttpClient()
        val builder = Request.Builder()

        //builder.url("https://imdb-api.com/en/API/SearchMovie/${BuildConfig.API_KEY}/${stringGenre}")

        val key = "k_cc86op97"
        builder.url("https://imdb-api.com/en/API/SearchMovie/${key}/${stringGenre}")

        val request: Request = builder.build()
        val cal: Call = client.newCall(request)

        cal.enqueue( object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){}
                if(response.code in 200..299&&response.body!=null){
                    response.body?.let {
                        val responseString = it.string()
                        val moviesDTO = Gson().fromJson(responseString, MoviesDTO::class.java)
                        callback.onResponse( ConvertDTOinMovies.returnList(moviesDTO))
                    }
                }else{
                    callback.onFailure(IOException("403 404"))
                }
            }

        })
    }


}