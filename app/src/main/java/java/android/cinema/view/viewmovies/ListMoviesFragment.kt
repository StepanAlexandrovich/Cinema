package java.android.cinema.view.viewmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import java.android.cinema.databinding.FragmentListMoviesBinding
import java.android.cinema.storage.Movie
import java.android.cinema.viewmodel.AppState
import java.android.cinema.viewmodel.ListMoviesViewModel

class ListMoviesFragment: Fragment() {

    companion object{
        fun newInstance() = ListMoviesFragment()
    }

    lateinit var binding: FragmentListMoviesBinding
    lateinit var viewModel: ListMoviesViewModel


    //////////
    private var adapterComedy: RecyclerAdapter? = null
    private var adapterFantasy: RecyclerAdapter? = null
    private var adapterFavorites: RecyclerAdapter? = null

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

        viewModel.getLiveDataComedy().observe(viewLifecycleOwner) { t -> renderData(t) }
        viewModel.getLiveDataFantasy().observe(viewLifecycleOwner) { t -> renderData(t) }
        viewModel.getLiveDataFavorites().observe(viewLifecycleOwner) { t -> renderData(t) }

        viewModel.sentRequest()
    }

    private fun createList(list:List<Movie>,recyclerView: RecyclerView):RecyclerAdapter{
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        recyclerView.adapter = RecyclerAdapter(list)
        return ( recyclerView.adapter as RecyclerAdapter )
    }

    private fun renderData(appState: AppState){

        when(appState){
            is AppState.Error -> {
                //.......
            }
            AppState.Loading -> {
                //........
            }

            is AppState.SuccessComedy -> {
                adapterComedy = createList(appState.movieList,binding.rvComedy)
            }
            is AppState.SuccessFantasy -> {
                adapterFantasy = createList(appState.movieList,binding.rvFantasy)
            }
            is AppState.SuccessFavorites -> {
                adapterFavorites = createList(appState.movieList,binding.rvFavorites)
            }
        }

    }

    // подключить с колбэком
    //fun downLoad(){
        //val progressBar = binding.progressBar

        //object : CountDownTimer(10000,500){
          //  override fun onTick(p0: Long) {
                //if(progressBar.progress < progressBar.max){
                    //progressBar.progress += 10
                //}
            //}

            //override fun onFinish() { }

        //}.start()
    //}


}


