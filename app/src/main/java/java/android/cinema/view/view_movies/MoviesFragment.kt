package java.android.cinema.view.view_movies

import android.os.Bundle
import android.view.*

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.android.cinema.PublicSettings
import java.android.cinema.R
import java.android.cinema.activity.MainActivity

import java.android.cinema.databinding.FragmentMoviesBinding
import java.android.cinema.domen.Movie

import java.android.cinema.utils.CountDownTimerProgressBar
import java.android.cinema.view.utilsToView.Navigation
import java.android.cinema.viewmodel.AppState
import java.android.cinema.viewmodel.ListMoviesViewModel

import java.android.cinema.view.view_movie.MovieFragment
import java.android.cinema.view.view_movies.listeners.ListenerButtonsMovies
import java.android.cinema.view.view_movies.listeners.ListenerMenuMovies

class MoviesFragment: Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    val binding: FragmentMoviesBinding get() { return _binding!! }

    private val movies = MainActivity.movies

    private lateinit var viewModel: ListMoviesViewModel
    private lateinit var rvGenres:RecyclerView

    private var imageDirectionId = R.drawable.ic_limits
    private lateinit var countDownTimerProgressBar:CountDownTimerProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_movies, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return ListenerMenuMovies(requireActivity(),this).switchItems(item)
    }



    private fun initialization(){
        // menu
        val myToolbar = view?.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(myToolbar)
        setHasOptionsMenu(true)

        // buttons
        ListenerButtonsMovies(binding,this)

        // recycle
        rvGenres = binding.rvGenres
        rvGenres.layoutManager = LinearLayoutManager(requireContext())
        rvGenres.adapter = RecyclerAdapterGenres(OnItemClick {
            Navigation.createFragmentWithBackStack(requireActivity(), R.id.container,MovieFragment.newInstance(it))
        })

        // view model
            // to download
        viewModel = ViewModelProvider(this).get(ListMoviesViewModel::class.java)
        viewModel.liveDataLoad.observe(viewLifecycleOwner) { renderData(it) }

            // to success
        viewModel.liveDates.forEach(){
            it.observe(viewLifecycleOwner) { renderData(it) }
        }

        countDownTimerProgressBar = CountDownTimerProgressBar(binding.progressBarMovies)

        indicatorAdultBorder()
    }

    private fun renderData(appState: AppState){
        when(appState){
            is AppState.Error -> {
                if(appState.index<=PublicSettings.mode.strings.size - 1){
                    movies.setGenreWithMovies(appState.index, "ЧТО ТО ПОШЛО НЕ ТАК", mutableListOf<Movie>())
                    if(appState.index == PublicSettings.mode.strings.size - 1){ countDownTimerProgressBar.finish() }
                }
                rvGenres.adapter?.notifyDataSetChanged()
            }

            AppState.Loading -> {
                countDownTimerProgressBar.start()
            }

            is AppState.SuccessData -> {
                synchronized(movies){
                    if(appState.index == PublicSettings.mode.strings.size - 1){
                        countDownTimerProgressBar.finish()
                    }

                    if(appState.index<=PublicSettings.mode.strings.size - 1){
                        movies.setGenreWithMovies(appState.index, PublicSettings.mode.strings[appState.index],appState.movies)
                    }

                    rvGenres.adapter?.notifyDataSetChanged()
                }
            }
        }

    }




    private fun update(){
        movies.resetAll()
        viewModel.sentRequest()
    }

    ///////////////////////// MOVIES //////////////////////////////////////
    fun fromDataBase(){
        //PublicSettings.mode = PublicSettings.modeTest
        PublicSettings.mode = PublicSettings.modeDataBase
        update()
    }

    fun fromInternet(){
        //PublicSettings.mode = PublicSettings.modeInternetStandard
        //PublicSettings.mode = PublicSettings.modeOkHttp
        PublicSettings.mode = PublicSettings.modeRetrofit
        update()
    }

    ////////////////// ADULT /////////////////////////////
    private fun indicatorAdultBorder(){
        if(PublicSettings.isAdult){
            imageDirectionId = R.drawable.ic_no_limits
            binding.imageViewAdult.setImageResource(imageDirectionId)
        }
        else {
            imageDirectionId = R.drawable.ic_limits
            binding.imageViewAdult.setImageResource(imageDirectionId)
        }
    }

    fun switchAdult(){
        PublicSettings.switchAdult()
        indicatorAdultBorder()
        MainActivity.saveBoolean.write(PublicSettings.isAdult)
        update()
    }

}




