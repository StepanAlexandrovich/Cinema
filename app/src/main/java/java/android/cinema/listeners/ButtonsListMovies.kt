package java.android.cinema.listeners


import android.view.View
import android.view.View.OnClickListener
import java.android.cinema.databinding.FragmentListMoviesBinding
import java.android.cinema.view.core.ListMoviesFragment

class ButtonsListMovies(val binding: FragmentListMoviesBinding,val listMoviesFragment: ListMoviesFragment): OnClickListener{

    init {
        binding.buttonUpdateLocalData.setOnClickListener(this)
        binding.buttonUpdateRemoteData.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.getId()) {
            binding.buttonUpdateLocalData.id -> { listMoviesFragment.updateLocalData() }
            binding.buttonUpdateRemoteData.id -> { listMoviesFragment.updateRemoteData() }
        }
    }
}