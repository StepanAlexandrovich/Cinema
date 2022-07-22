package java.android.cinema.view.mainscreen

import android.os.Build
import android.view.View
import android.view.View.OnClickListener
import android.widget.Switch
import androidx.annotation.RequiresApi
import java.android.cinema.databinding.FragmentListMoviesBinding
import java.android.cinema.utils.PrintVisible

class ListMoviesButtons(private val binding: FragmentListMoviesBinding, private val listMoviesFragment: ListMoviesFragment): OnClickListener{

    init {
        binding.buttonFromDataBase.setOnClickListener(this)
        binding.buttonFromOkhttp.setOnClickListener(this)
        binding.buttonFromRetrofit.setOnClickListener(this)

        binding.buttonSnackBarMenu.setOnClickListener(this)
        binding.buttonAdult.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(p0: View?) {
        when (p0?.getId()) {
            binding.buttonFromDataBase.id     -> { listMoviesFragment.fromDataBase()  }
            binding.buttonFromOkhttp.id       -> { listMoviesFragment.fromOkHttp() }
            binding.buttonFromRetrofit.id     -> { listMoviesFragment.fromRetrofit() }

            binding.buttonSnackBarMenu.id     -> { listMoviesFragment.snackBarMenu()     }
            binding.buttonAdult.id            -> { listMoviesFragment.switchAdult()      }
        }
    }
}




