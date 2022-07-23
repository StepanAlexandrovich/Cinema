package java.android.cinema.view.mainscreen

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
import java.android.cinema.storage.SharedPref
import java.android.cinema.view.utilsToView.Navigation
import java.android.cinema.view.utilsToView.UtilsToRecycler
import java.android.cinema.viewmodel.AppState
import java.android.cinema.viewmodel.ListMoviesViewModel

import java.android.cinema.view.details.MovieFragment


class ListMoviesFragment: Fragment() {

    private var _binding: FragmentListMoviesBinding? = null
    val binding: FragmentListMoviesBinding get() { return _binding!! }

    private val movies = MainActivity.localMovies

    companion object{
        fun newInstance() = ListMoviesFragment()
    }

    private lateinit var viewModel: ListMoviesViewModel

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
        viewModel.liveData.observe(viewLifecycleOwner) { renderData(it) }
        changeColor()
    }

    // СКРОЛЛИНГ ПЕРЕНЕСТИ В RECYCLE
    private fun updateItemGenre(item: View, title:String, movies: List<Movie>){
        item.findViewById<TextView>(R.id.textViewGenre).text = title

        val rv:RecyclerView = item.findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = UtilsToRecycler.createLayoutHorizontalManagerInversion(requireActivity() as AppCompatActivity)
        val adapter = rv.adapter as RecyclerAdapterMovies
        //adapter.setList(movies)

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

            is AppState.SuccessData -> {
                synchronized(movies){
                    movies.setGenreWithMovies(appState.index, PublicSettings.mode!!.strings[appState.index],appState.movies)
                }
            }
        }

        rvGenres.adapter?.notifyDataSetChanged()
    }

    private fun changeColor(){
        if(PublicSettings.isAdult){ binding.buttonAdult.setBackgroundColor(ContextCompat.getColor(MainActivity.activityApp,R.color.purple_200)) }
        else                      { binding.buttonAdult.setBackgroundColor(ContextCompat.getColor(MainActivity.activityApp,R.color.purple_700)) }
    }

    private fun update(){
        movies.resetAll()
        viewModel.sentRequest()
    }

    ///////// FUN TO BUTTONS ///////
    fun fromTest(){
        PublicSettings.mode = PublicSettings.modeTest
        update()
    }

    fun fromDataBase(){
        PublicSettings.mode = PublicSettings.modeDataBase
        update()
    }

    fun fromOkHttp(){
        PublicSettings.mode = PublicSettings.modeOkHttp
        update()
    }

    fun fromRetrofit(){
        PublicSettings.mode = PublicSettings.modeRetrofit
        update()
    }

    fun snackBarMenu(){ ListMoviesSnackBar.snackBarMenu(this) }

    fun switchAdult(){
        PublicSettings.switchAdult()
        changeColor()

        SharedPref.writeAdult()

        update()
    }
    ///////////////////////////////////

}




