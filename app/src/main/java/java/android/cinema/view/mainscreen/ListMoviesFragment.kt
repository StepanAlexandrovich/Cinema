package java.android.cinema.view.mainscreen

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.android.cinema.PublicSettings
import java.android.cinema.R
import java.android.cinema.activity.MainActivity

import java.android.cinema.databinding.FragmentListMoviesBinding

import java.android.cinema.domen.Movie
import java.android.cinema.model.dto.MoviesDTO
import java.android.cinema.storage.SharedPref
import java.android.cinema.view.utilsToView.Navigation
import java.android.cinema.view.utilsToView.UtilsToRecycler
import java.android.cinema.viewmodel.AppState
import java.android.cinema.viewmodel.ListMoviesViewModel

import java.android.cinema.utils.PrintVisible
import java.android.cinema.view.details.MovieFragment


class ListMoviesFragment: Fragment() {

    private var _binding: FragmentListMoviesBinding? = null
    val binding: FragmentListMoviesBinding get() { return _binding!! }

    companion object{
        fun newInstance() = ListMoviesFragment()
    }

    private lateinit var viewModel: ListMoviesViewModel

    //////////
    private lateinit var listItemsGenres:List<View>
    private lateinit var rvGenres:RecyclerView

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListMoviesBinding.inflate(inflater)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // buttons
        ListMoviesButtons(binding,this)

        // recycle
        rvGenres = binding.rvGenres
        rvGenres.layoutManager = LinearLayoutManager(requireContext())
        rvGenres.adapter = RecyclerAdapterGenres(OnItemClick {
            Navigation.createFragmentWithBackStack(requireActivity() as AppCompatActivity, R.id.container,
                MovieFragment.newInstance(it)
            )
            MovieFragment.currentMovie = it
        })

        // view model
        viewModel = ViewModelProvider(this).get(ListMoviesViewModel::class.java)

        viewModel.liveDataComedy.observe(viewLifecycleOwner) { t -> renderData(t) }
        viewModel.liveDataFantasy.observe(viewLifecycleOwner) { t -> renderData(t) }
        viewModel.liveDataFavorites.observe(viewLifecycleOwner) { t -> renderData(t) }

        viewModel.liveDataFromInternetRetrofit1.observe(viewLifecycleOwner) { t -> renderData(t) }
        viewModel.liveDataFromInternetRetrofit2.observe(viewLifecycleOwner) { t -> renderData(t) }

        //viewModel.sentRequest() // восстановить
        //binding.switchAdult.isEnabled = PublicSettings.isAdult
        changeColor()
    }

    private fun updateItemGenre(item: View, title:String, movies: List<Movie>){
        item.findViewById<TextView>(R.id.textViewGenre).text = title

        val rv:RecyclerView = item.findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = UtilsToRecycler.createLayoutHorizontalManagerInversion(requireActivity() as AppCompatActivity)
        val adapter = rv.adapter as RecyclerAdapterMovies
        adapter.setList(movies)

        rv.smoothScrollToPosition(movies.size - 1)
        adapter.notifyDataSetChanged()
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
                updateItemGenre(listItemsGenres[0],appState.genre.title,appState.genre.list)
            }
            is AppState.SuccessFantasy -> {
                updateItemGenre(listItemsGenres[1],appState.genre.title,appState.genre.list)
            }
            is AppState.SuccessFavorites -> {
                updateItemGenre(listItemsGenres[2],appState.genre.title,appState.genre.list)
            }

            is AppState.SuccessFromInternetRetrofit1 -> {
                //updateItemGenre(listItemsGenres[0],PublicSettings.strings[0],moviesDTOinListMovies( appState.movieList ))
            }

            is AppState.SuccessFromInternetRetrofit2 -> {
                //updateItemGenre(listItemsGenres[1],PublicSettings.strings[1],moviesDTOinListMovies( appState.movieList ))
            }
        }

    }

    private fun moviesDTOinListMovies(moviesDTO: MoviesDTO):MutableList<Movie>{
        val moviesFromInternet = mutableListOf<Movie>()

        if(moviesDTO.results==null){
            PrintVisible.printShort("Возможно вы исчерпали лимит")
        }else{
            moviesDTO.results.forEach(){
                val movie:Movie = Movie(it.title)
                movie.setImage(it.image)
                moviesFromInternet.add(movie)
            }
        }

        return moviesFromInternet
    }

    private fun changeColor(){
        if(PublicSettings.isAdult){ binding.buttonAdult.setBackgroundColor(ContextCompat.getColor(MainActivity.activityApp,R.color.purple_200)) }
        else                      { binding.buttonAdult.setBackgroundColor(ContextCompat.getColor(MainActivity.activityApp,R.color.purple_700)) }
    }

    ///////// FUN TO BUTTONS ///////
    @RequiresApi(Build.VERSION_CODES.N)
    fun updateLocalData(){
        listItemsGenres = (rvGenres.adapter as RecyclerAdapterGenres).listItems
        viewModel.sentRequest()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun updateRemoteData(){}

    fun snackBarMenu(){ ListMoviesSnackBar.snackBarMenu(this) }

    @RequiresApi(Build.VERSION_CODES.N)
    fun switchAdult(){
        PublicSettings.switchAdult()
        changeColor()

        SharedPref.writeAdult()
        updateLocalData()
    }
    ///////////////////////////////////

}




