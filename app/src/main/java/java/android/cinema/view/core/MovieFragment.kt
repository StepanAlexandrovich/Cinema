package java.android.cinema.view.core

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import java.android.cinema.databinding.FragmentMovieBinding
import java.android.cinema.domen.Movie

import java.android.cinema.viewmodel.ListMoviesViewModel

class MovieFragment: Fragment() {



    lateinit var binding: FragmentMovieBinding
    lateinit var viewModel: ListMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //downLoad() с колбэком

        val movie = arguments?.getParcelable<Movie>(BUNDLE_MOVIE_EXTRA)
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


