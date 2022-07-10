package java.android.cinema.view.core

import android.graphics.Color
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
import com.google.android.material.snackbar.Snackbar
import java.android.cinema.R

import java.android.cinema.databinding.FragmentListMoviesBinding

import java.android.cinema.domen.Movie
import java.android.cinema.listeners.ButtonsListMovies
import java.android.cinema.listeners.OnItemClick
import java.android.cinema.utils.MoviesLoader
import java.android.cinema.view.utilsToView.Navigation
import java.android.cinema.view.utilsToView.UtilsToRecycler
import java.android.cinema.viewmodel.AppState
import java.android.cinema.viewmodel.ListMoviesViewModel

import java.android.cinema.model.dto.Result

class ListMoviesFragment: Fragment() {

    val moviesFromInternet = mutableListOf<Movie>()  // временно

    companion object{
        fun newInstance() = ListMoviesFragment()
    }

    lateinit var binding: FragmentListMoviesBinding
    lateinit var viewModel: ListMoviesViewModel

    //////////

    private lateinit var listItemsGenres:List<View>
    private lateinit var rvGenres:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListMoviesBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // buttons
        ButtonsListMovies(binding,this)

        //downLoad() с колбэком

        rvGenres = binding.rvGenres
        rvGenres.layoutManager = LinearLayoutManager(requireContext())
        rvGenres.adapter = RecyclerAdapterGenres(OnItemClick {
            Navigation.createFragmentWithBackStack(requireActivity() as AppCompatActivity, R.id.container,MovieFragment.newInstance(it))
        })

        ////////////
        viewModel = ViewModelProvider(this).get(ListMoviesViewModel::class.java)

        viewModel.getLiveDataComedy().observe(viewLifecycleOwner) { t -> renderDataLocal(t) }
        viewModel.getLiveDataFantasy().observe(viewLifecycleOwner) { t -> renderDataLocal(t) }
        viewModel.getLiveDataFavorites().observe(viewLifecycleOwner) { t -> renderDataLocal(t) }

        viewModel.getLiveDataFromInternet().observe(viewLifecycleOwner) { t -> renderDataLocal(t) }

        //viewModel.sentRequest()   // восстановить


        // загрузка фильмов из интернета   ПЕРЕДЕЛАТЬ
        var results:List<Result>
        var strings = mutableListOf<String>()

        MoviesLoader.request{ moviesDTO ->
            requireActivity().runOnUiThread{

                results = moviesDTO.results

                results.forEach(){
                    strings.add(it.title)
                    moviesFromInternet .add(Movie(it.title))
                }

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun updateLocalData(){
        listItemsGenres = (rvGenres.adapter as RecyclerAdapterGenres).listItems
        viewModel.sentRequest()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun updateRemoteData(){
        listItemsGenres = (rvGenres.adapter as RecyclerAdapterGenres).listItems
        updateItemGenre(listItemsGenres[2],"FROM INTERNET",moviesFromInternet)
        //viewModel.sentRequest() // реализовать
    }

    fun snackBarMenu(){
        val linearLayout = binding.listGenres
        val snackBar:Snackbar = Snackbar.make(linearLayout,"",Snackbar.LENGTH_INDEFINITE)
        val custom:View = layoutInflater.inflate(R.layout.snackbar_custom,null)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarLayout: Snackbar.SnackbarLayout = snackBar.view as Snackbar.SnackbarLayout
        snackBarLayout.setPadding(0,0,0,0)

        custom.findViewById<View>(R.id.buttonDismissSnackBarMenu).setOnClickListener(View.OnClickListener {
            snackBar.dismiss()
        })

        snackBarLayout.addView(custom)
        snackBar.show()
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
                updateItemGenre(listItemsGenres[0],"COMEDY",appState.movieList)
            }
            is AppState.SuccessFantasy -> {
                updateItemGenre(listItemsGenres[1],"FANTASY",appState.movieList)
            }
            is AppState.SuccessFavorites -> {
                //updateItemGenre(listItemsGenres[2],"ANIMATED",appState.movieList)
            }
            is AppState.SuccessFromInternet -> {
                //updateItemGenre(listItemsGenres[2],"FROM INTERNET",appState.movieList)
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




