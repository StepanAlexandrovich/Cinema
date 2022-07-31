package java.android.cinema.model.remote.standart

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.android.cinema.API_KEY
import java.android.cinema.model.MoviesCallback
import java.android.cinema.model.RepositoryMovies
import java.android.cinema.model.remote.dto.ConvertDTOinMovies
import java.android.cinema.model.remote.dto.MoviesDTO
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class RepositoryMoviesRemoteStandardImpl:RepositoryMovies {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getListMovies(stringGenre: String, callback: MoviesCallback) {
        try {
            val uri = URL("https://imdb-api.com/en/API/SearchMovie/${API_KEY}/${stringGenre}")

            Thread{

                var myConnection: HttpURLConnection? = null
                myConnection = uri.openConnection() as HttpURLConnection

                try {
                    myConnection.readTimeout = 5000

                    val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                    val moviesDTO = Gson().fromJson(InternetUtils.getLines(reader), MoviesDTO::class.java)

                    callback.onResponse(ConvertDTOinMovies.returnList(moviesDTO) )

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