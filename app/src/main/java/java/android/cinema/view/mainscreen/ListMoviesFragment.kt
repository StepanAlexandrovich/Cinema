package java.android.cinema.view.mainscreen

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.android.cinema.R

import java.android.cinema.databinding.FragmentListMoviesBinding

import java.android.cinema.domen.Movie
import java.android.cinema.model.dto.MoviesDTO
import java.android.cinema.view.utilsToView.Navigation
import java.android.cinema.view.utilsToView.UtilsToRecycler
import java.android.cinema.viewmodel.AppState
import java.android.cinema.viewmodel.ListMoviesViewModel

import java.android.cinema.utils.SimpleNotifications
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

    private val moviesFromInternet = mutableListOf<Movie>()
    private var genreFromInternet = "fringe"

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
        })

        // view model
        viewModel = ViewModelProvider(this).get(ListMoviesViewModel::class.java)

        viewModel.getLiveDataComedy().observe(viewLifecycleOwner) { t -> renderDataLocal(t) }
        viewModel.getLiveDataFantasy().observe(viewLifecycleOwner) { t -> renderDataLocal(t) }
        viewModel.getLiveDataFavorites().observe(viewLifecycleOwner) { t -> renderDataLocal(t) }

        viewModel.getLiveDataFromInternetOkHttp().observe(viewLifecycleOwner) { t -> renderDataLocal(t) }
        viewModel.getLiveDataFromInternetRetrofit().observe(viewLifecycleOwner) { t -> renderDataLocal(t) }

        //viewModel.sentRequest() // восстановить
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

    private fun renderDataLocal(appState: AppState){

        when(appState){
            is AppState.Error -> {
                //.......
            }
            AppState.Loading -> {
                //........
            }

            is AppState.SuccessComedy -> {
                updateItemGenre(listItemsGenres[0],"LOCAL REPOSITORY",appState.movieList)
            }
            is AppState.SuccessFantasy -> {
                //updateItemGenre(listItemsGenres[1],"FANTASY",appState.movieList)
            }
            is AppState.SuccessFavorites -> {
                //updateItemGenre(listItemsGenres[2],"ANIMATED",appState.movieList)
            }
            is AppState.SuccessFromInternetOkHttp -> {
                updateItemGenre(listItemsGenres[1],"OkHTTP",moviesDTOinListMovies( appState.movieList ))
            }
            is AppState.SuccessFromInternetRetrofit -> {
                updateItemGenre(listItemsGenres[2],"RETROFIT",moviesDTOinListMovies( appState.movieList ))
            }
        }

    }

    private fun moviesDTOinListMovies(moviesDTO: MoviesDTO):MutableList<Movie>{
        var strings = mutableListOf<String>()
        val moviesFromInternet = mutableListOf<Movie>()

        if(moviesDTO.results==null){
            SimpleNotifications.printShort("Возможно вы исчерпали лимит")
        }else{
            moviesDTO.results.forEach(){
                strings.add(it.title)
                moviesFromInternet.add(Movie(it.title))
            }
        }

        return moviesFromInternet
    }

    ///////// FUN TO BUTTONS ///////
    @RequiresApi(Build.VERSION_CODES.N)
    fun updateLocalData(){
        listItemsGenres = (rvGenres.adapter as RecyclerAdapterGenres).listItems
        viewModel.sentRequest()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun updateRemoteData(){
        listItemsGenres = (rvGenres.adapter as RecyclerAdapterGenres).listItems

        if(moviesFromInternet.size>0){ updateItemGenre(listItemsGenres[2],genreFromInternet,moviesFromInternet) } // временно
        //viewModel.sentRequest() // реализовать
    }

    fun snackBarMenu(){
        ListMoviesSnackBar.snackBarMenu(this)
    }
    ///////////////////////////////////

}




