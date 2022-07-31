package java.android.cinema.view.view_movies.listeners

import android.view.View
import android.view.View.OnClickListener
import java.android.cinema.databinding.FragmentMoviesBinding
import java.android.cinema.view.view_movies.MoviesFragment

class ListenerButtonsMovies(private val binding: FragmentMoviesBinding, private val listMoviesFragment: MoviesFragment): OnClickListener{

    init {
        binding.imageViewAdult.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.getId()) {
            binding.imageViewAdult.id  -> { listMoviesFragment.switchAdult()      }
        }
    }
}




