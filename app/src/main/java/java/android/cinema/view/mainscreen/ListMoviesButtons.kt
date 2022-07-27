package java.android.cinema.view.mainscreen

import android.os.Build
import android.view.View
import android.view.View.OnClickListener
import androidx.annotation.RequiresApi
import java.android.cinema.databinding.FragmentListMoviesBinding

class ListMoviesButtons(private val binding: FragmentListMoviesBinding, private val listMoviesFragment: ListMoviesFragment): OnClickListener{

    init {
        binding.buttonFromDataBase.setOnClickListener(this)
        binding.buttonFromOkhttp.setOnClickListener(this)
        binding.buttonFromRetrofit.setOnClickListener(this)
        binding.buttonTest.setOnClickListener(this)

        binding.buttonSnackBarMenu.setOnClickListener(this)
        binding.imageViewAdult.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(p0: View?) {
        when (p0?.getId()) {
            binding.buttonFromDataBase.id     -> { listMoviesFragment.fromDataBase()  }
            binding.buttonFromOkhttp.id       -> { listMoviesFragment.fromOkHttp() }
            binding.buttonFromRetrofit.id     -> { listMoviesFragment.fromRetrofit() }
            binding.buttonTest.id             -> { listMoviesFragment.fromTest() }

            binding.buttonSnackBarMenu.id     -> { listMoviesFragment.snackBarMenu()     }
            binding.imageViewAdult.id            -> { listMoviesFragment.switchAdult()      }
        }
    }
}




