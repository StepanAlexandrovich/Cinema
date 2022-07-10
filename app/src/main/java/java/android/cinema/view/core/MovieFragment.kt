package java.android.cinema.view.core

import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.gson.Gson

import java.android.cinema.databinding.FragmentMovieBinding
import java.android.cinema.domen.Movie
import java.android.cinema.model.dto.WeatherDTO
import java.android.cinema.utils.Internet

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

class MovieFragment: Fragment() {

    lateinit var binding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //downLoad() с колбэком

        val movie = arguments?.getParcelable<Movie>(BUNDLE_MOVIE_EXTRA)



        movie?.let {
            val uri = URL("https://api.weather.yandex.ru/v2/forecast?lat=${it.lat}&lon=${it.lon}")

            var myConnection: HttpURLConnection? = null

            myConnection = uri.openConnection() as HttpURLConnection
            myConnection.readTimeout = 5000
            myConnection.addRequestProperty("X-Yandex-API-Key","24a19ebb-e3cb-4fd7-bb54-dfd6884fdc54")

            //val handler = Handler(Looper.myLooper()!!)
            val handler = Handler(Looper.getMainLooper())

            Thread{

                val reader = BufferedReader(InputStreamReader(myConnection.inputStream))

                val weatherDTO = Gson().fromJson(Internet.getLines(reader), WeatherDTO::class.java)

                requireActivity().runOnUiThread{
                    renderData(it.apply {
                        feelsLike = weatherDTO.fact.feelsLike
                        temperature = weatherDTO.fact.temp

                        //Toast.makeText(requireContext(),"${it.feelsLike}",Toast.LENGTH_SHORT).show()
                    })
                }


            }.start()
        }

        if(movie!=null){ renderData(movie) }
    }

    private fun renderData(movie: Movie){
        binding.textViewTitle.text = movie.title
        binding.textViewDescription.text = movie.getDescription()
    }

    // подключить с колбэком
    fun downLoad(){
        val progressBar = binding.progressBar

        object : CountDownTimer(10000,500){
            override fun onTick(p0: Long) {
                if(progressBar.progress < progressBar.max){
                    progressBar.progress += 10
                }
            }

            override fun onFinish() { }

        }.start()
    }

    companion object {
        const val BUNDLE_MOVIE_EXTRA = "sgrrdfge"
        fun newInstance(movie: Movie): MovieFragment {
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_MOVIE_EXTRA,movie)
            val fr = MovieFragment()
            fr.arguments = bundle
            return fr
        }
    }


}


