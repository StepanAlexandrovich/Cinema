package java.android.cinema.internet.download

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import java.android.cinema.BuildConfig
import java.android.cinema.internet.InternetUtils
import java.android.cinema.model.dto.MoviesDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

const val WAVE = "tyrh44"
const val BUNDLE_MOVIES_DTO_KEY = "GNJFJ"
const val BUNDLE_TYPE_MOVIES_KEY = "GGYHTHF"

class IntentServiceMoviesDTO: IntentService("")  {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {

        intent?.let {

            it.getStringExtra(BUNDLE_TYPE_MOVIES_KEY)?.let {

                val uri = URL("https://imdb-api.com/en/API/SearchMovie/${BuildConfig.API_KEY}/${it}")

                Thread{

                    var myConnection: HttpURLConnection? = null
                    myConnection = uri.openConnection() as HttpURLConnection
                    myConnection.readTimeout = 5000

                    val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                    val moviesDTO = Gson().fromJson(InternetUtils.getLines(reader), MoviesDTO::class.java)

                    LocalBroadcastManager.getInstance(this).sendBroadcast(Intent().apply {

                        putExtra(BUNDLE_MOVIES_DTO_KEY,moviesDTO)
                        action = WAVE
                    })

                }.start()

            }

        }
    }


}