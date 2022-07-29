package java.android.cinema.view.view_movies.listeners

import android.view.View
import android.view.View.OnClickListener
import java.android.cinema.databinding.FragmentListMoviesBinding
import java.android.cinema.view.view_movies.ListMoviesFragment

class ListenerButtonsMovies(private val binding: FragmentListMoviesBinding, private val listMoviesFragment: ListMoviesFragment): OnClickListener{

    init {
        binding.imageViewAdult.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.getId()) {
            binding.imageViewAdult.id            -> { listMoviesFragment.switchAdult()      }
        }
    }
}




