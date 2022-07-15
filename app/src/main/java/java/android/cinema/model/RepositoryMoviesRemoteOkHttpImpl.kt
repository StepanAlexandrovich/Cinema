package java.android.cinema.model

import com.google.gson.Gson
import okhttp3.*
import java.android.cinema.BuildConfig
import java.android.cinema.model.dto.MoviesDTO
import java.io.IOException

class RepositoryMoviesRemoteOkHttpImpl:RepositoryMoviesRemote {

    override fun getListMovies(callback: LargeSuperCallback){
        // TODO
    }

    override fun getListMovies(genre: String, callback: LargeSuperCallback) {
        val client = OkHttpClient()
        val builder = Request.Builder()

        builder.url("https://imdb-api.com/en/API/SearchMovie/${BuildConfig.API_KEY}/$genre")

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
                        callback.onResponse(moviesDTO)
                    }
                }else{
                    callback.onFailure(IOException("403 404"))
                }
            }

        })
    }


}