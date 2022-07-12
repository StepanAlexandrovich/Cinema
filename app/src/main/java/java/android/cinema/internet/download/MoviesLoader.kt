package java.android.cinema.internet.download

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.android.cinema.BuildConfig
import java.android.cinema.internet.InternetUtils
import java.android.cinema.model.dto.MoviesDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object MoviesLoader {

    @RequiresApi(Build.VERSION_CODES.N)
    fun request(block:(movies:MoviesDTO)->Unit){

        //val typeMovies = "inception 2010"
        val typeMovies = "lost 2004"

        val uri = URL("https://imdb-api.com/en/API/SearchMovie/${BuildConfig.API_KEY}/${typeMovies}")

        var myConnection: HttpURLConnection? = null

        myConnection = uri.openConnection() as HttpURLConnection
        myConnection.readTimeout = 5000

        Thread{
            val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
            val moviesDTO = Gson().fromJson(InternetUtils.getLines(reader), MoviesDTO::class.java)
            block(moviesDTO)
        }.start()
    }

}