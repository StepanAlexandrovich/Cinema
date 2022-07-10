package java.android.cinema.utils

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.android.cinema.BuildConfig
import java.android.cinema.model.dto.MoviesDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object MoviesLoader {

    @RequiresApi(Build.VERSION_CODES.N)
    fun request(block:(movies:MoviesDTO)->Unit){
        val uri = URL("https://imdb-api.com/en/API/SearchMovie/${BuildConfig.API_KEY}/inception 2010")

        var myConnection: HttpURLConnection? = null

        myConnection = uri.openConnection() as HttpURLConnection
        myConnection.readTimeout = 5000
        myConnection.addRequestProperty("X-Yandex-API-Key","24a19ebb-e3cb-4fd7-bb54-dfd6884fdc54")

        //val handler = Handler(Looper.myLooper()!!)
        val handler = Handler(Looper.getMainLooper())

        Thread{
            val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
            val moviesDTO = Gson().fromJson(Internet.getLines(reader), MoviesDTO::class.java)
            block(moviesDTO)
        }.start()
    }

}