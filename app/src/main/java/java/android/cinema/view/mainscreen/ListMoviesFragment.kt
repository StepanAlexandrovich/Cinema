package java.android.cinema.view.mainscreen

import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import java.android.cinema.BuildConfig
import java.android.cinema.R

import java.android.cinema.databinding.FragmentListMoviesBinding

import java.android.cinema.domen.Movie
import java.android.cinema.domen.Movies
import java.android.cinema.internet.download.*
import java.android.cinema.model.dto.MoviesDTO
import java.android.cinema.view.utilsToView.Navigation
import java.android.cinema.view.utilsToView.UtilsToRecycler
import java.android.cinema.viewmodel.AppState
import java.android.cinema.viewmodel.ListMoviesViewModel

import java.android.cinema.model.dto.Result
import java.android.cinema.utils.SimpleNotifications
import java.android.cinema.view.details.*
import java.net.URL

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

        rvGenres = binding.rvGenres
        rvGenres.layoutManager = LinearLayoutManager(requireContext())
        rvGenres.adapter = RecyclerAdapterGenres(OnItemClick {
            Navigation.createFragmentWithBackStack(requireActivity() as AppCompatActivity, R.id.container,
                MovieFragment.newInstance(it)
            )
        })

        ////////////
        viewModel = ViewModelProvider(this).get(ListMoviesViewModel::class.java)

        viewModel.getLiveDataComedy().observe(viewLifecycleOwner) { t -> renderDataLocal(t) }
        viewModel.getLiveDataFantasy().observe(viewLifecycleOwner) { t -> renderDataLocal(t) }
        viewModel.getLiveDataFavorites().observe(viewLifecycleOwner) { t -> renderDataLocal(t) }

        viewModel.getLiveDataFromInternet().observe(viewLifecycleOwner) { t -> renderDataLocal(t) } // пока в холостую

        //viewModel.sentRequest()   - > восстановить

        //downLoad()   //загрузка фильмов из интернета - >  ПЕРЕДЕЛАТЬ

        downloadOkhttp()
    }

    private fun downloadOkhttp(){
        val client = OkHttpClient()
        val builder = Request.Builder()

        //builder.url("https://imdb-api.com/en/API/SearchMovie/${BuildConfig.API_KEY}/${genreFromInternet}") // ещё не дают доступ по старому api_key
        val newKey = "k_71rwtkzg"; builder.url("https://imdb-api.com/en/API/SearchMovie/${newKey}/${genreFromInternet}")

        val request:Request = builder.build()
        val cal: Call = client.newCall(request)
        Thread{
            val response = cal.execute()
            if(response.isSuccessful){}
            if(response.code in 200..299){
                response.body?.let {
                    val responseString = it.string()
                    val moviesDTO = Gson().fromJson(responseString,MoviesDTO::class.java)
                    val strings = mutableListOf<String>()

                    requireActivity().runOnUiThread{
                        if(moviesDTO.results==null){
                            SimpleNotifications.printShort("Возможно вы исчерпали лимит")
                        }else{
                            moviesDTO.results.forEach(){
                                strings.add(it.title)
                                moviesFromInternet.add(Movie(it.title))
                            }
                        }
                    }
                }
            }
        }.start()

    }

    // ПЕРЕНЕСУ В РЕПОЗИТОРИЙ
    @RequiresApi(Build.VERSION_CODES.N)
    fun downLoad(){
        //MoviesLoader.stringGenre = "inception 2010"
        MoviesLoader.stringGenre = genreFromInternet

        val strings = mutableListOf<String>()

        MoviesLoader.request{ moviesDTO ->
            requireActivity().runOnUiThread{

                if(moviesDTO.results==null){
                    SimpleNotifications.printShort("Возможно вы исчерпали лимит")
                }else{
                    moviesDTO.results.forEach(){
                        strings.add(it.title)
                        moviesFromInternet.add(Movie(it.title))
                    }
                }

            }
        }
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
                //updateItemGenre(listItemsGenres[3],"FROM INTERNET",appState.movieList)
            }
        }

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




