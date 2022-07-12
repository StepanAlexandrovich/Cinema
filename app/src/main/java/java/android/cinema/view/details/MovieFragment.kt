package java.android.cinema.view.details

import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

import java.android.cinema.databinding.FragmentMovieBinding
import java.android.cinema.domen.Movie

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

        if(movie!=null){ renderData(movie) }
    }

    private fun renderData(movie: Movie){
        binding.textViewTitle.text = movie.title
        binding.textViewDescription.text = movie.getDescription()
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


