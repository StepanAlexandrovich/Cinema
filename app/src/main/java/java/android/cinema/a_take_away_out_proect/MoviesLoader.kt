package java.android.cinema.a_take_away_out_proect

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.android.cinema.BuildConfig
import java.android.cinema.PublicSettings
import java.android.cinema.internet.InternetUtils
import java.android.cinema.model.dto.MoviesDTO
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object MoviesLoader {

    @RequiresApi(Build.VERSION_CODES.N)
    fun request(block:(movies:MoviesDTO)->Unit){

        try {
            val uri = URL("https://imdb-api.com/en/API/SearchMovie/${BuildConfig.API_KEY}/${PublicSettings.strings[0]}")

            Thread{

                var myConnection: HttpURLConnection? = null
                myConnection = uri.openConnection() as HttpURLConnection

                try {
                    myConnection.readTimeout = 5000

                    val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                    val moviesDTO = Gson().fromJson(InternetUtils.getLines(reader), MoviesDTO::class.java)

                    block(moviesDTO)

                } catch (e: MalformedURLException) {
                } catch (e: IOException) {
                } catch (e: JsonSyntaxException) {
                } finally {
                    myConnection.disconnect()
                }

            }.start()

        }catch (e: MalformedURLException){ }

    }

}