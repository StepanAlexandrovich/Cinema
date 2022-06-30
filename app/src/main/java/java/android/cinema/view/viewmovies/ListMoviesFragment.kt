package java.android.cinema.view.viewmovies

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import java.android.cinema.R
import java.android.cinema.databinding.FragmentListMoviesBinding
import java.android.cinema.viewmodel.AppState

class ListMoviesFragment: Fragment() {

    companion object{
        fun newInstance() = ListMoviesFragment()
    }

    lateinit var binding: FragmentListMoviesBinding
    lateinit var viewModel: ListMoviesViewModel

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

        viewModel = ViewModelProvider(this).get(ListMoviesViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }
        viewModel.sentRequest()

    }

    private fun renderData(appState: AppState){

        when(appState){
            is AppState.Error -> {

            }
            AppState.Loading -> {

            }
            is AppState.Success -> {

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


