package java.android.cinema.a_take_away_out_proect

import com.google.gson.Gson
import okhttp3.*
import java.android.cinema.BuildConfig
import java.android.cinema.PublicSettings
import java.android.cinema.model.LargeSuperCallback
import java.android.cinema.model.RepositoryMoviesRemote
import java.android.cinema.model.dto.MoviesDTO
import java.io.IOException

class RepositoryMoviesRemoteOkHttpImpl: RepositoryMoviesRemote {

    override fun getListMovies(indexGenre:Int, callback: LargeSuperCallback) {
        val client = OkHttpClient()
        val builder = Request.Builder()

        builder.url("https://imdb-api.com/en/API/SearchMovie/${BuildConfig.API_KEY}/${PublicSettings.strings[indexGenre]}")

        val request: Request = builder.build()
        val cal: Call = client.newCall(request)

        cal.enqueue( object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(indexGenre,e)
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){}
                if(response.code in 200..299&&response.body!=null){
                    response.body?.let {
                        val responseString = it.string()
                        val moviesDTO = Gson().fromJson(responseString, MoviesDTO::class.java)
                        callback.onResponse(indexGenre, moviesDTO)
                    }
                }else{
                    callback.onFailure(indexGenre,IOException("403 404"))
                }
            }

        })
    }


}