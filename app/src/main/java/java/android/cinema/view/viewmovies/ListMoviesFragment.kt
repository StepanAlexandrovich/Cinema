package java.android.cinema.view.viewmovies

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import java.android.cinema.databinding.FragmentListMoviesBinding
import java.android.cinema.viewmodel.AppState

class ListMoviesFragment: Fragment() {

    companion object{
        fun newInstance() = ListMoviesFragment()
    }

    lateinit var binding: FragmentListMoviesBinding
    lateinit var viewModel: SelectedMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListMoviesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //downLoad() с колбэком

        viewModel = ViewModelProvider(this).get(SelectedMoviesViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }
        viewModel.sentRequest()
    }

    private fun renderData(appState: AppState){

        when(appState){
            is AppState.Error -> {
                binding.textView.text = "Фильм не загружен, попробуйте ещё раз"
            }
            AppState.Loading -> {
                Toast.makeText(requireContext(),"идёт загрузка",Toast.LENGTH_LONG).show()
            }
            is AppState.Success -> {
                val result = appState.movieData
                binding.textView.text = "${result.name} ${result.duration}min"
            }
        }

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


}


