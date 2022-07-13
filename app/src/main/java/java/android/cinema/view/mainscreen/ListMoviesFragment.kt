package java.android.cinema.view.mainscreen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.android.cinema.R

import java.android.cinema.databinding.FragmentListMoviesBinding

import java.android.cinema.domen.Movie
import java.android.cinema.internet.download.*
import java.android.cinema.model.dto.MoviesDTO
import java.android.cinema.view.utilsToView.Navigation
import java.android.cinema.view.utilsToView.UtilsToRecycler
import java.android.cinema.viewmodel.AppState
import java.android.cinema.viewmodel.ListMoviesViewModel

import java.android.cinema.model.dto.Result
import java.android.cinema.utils.SimpleNotifications
import java.android.cinema.view.details.*

class ListMoviesFragment: Fragment() {

    private var _binding: FragmentListMoviesBinding? = null
    val binding: FragmentListMoviesBinding get() { return _binding!! }

    private val moviesFromInternet = mutableListOf(mutableListOf<Movie>(),mutableListOf<Movie>()) // временно
    private var indexGenre = 0; // ненадолго

    private val receiver:BroadcastReceiver = createReceiver()

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
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
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

        //downLoad()   загрузка фильмов из интернета - >  ПЕРЕДЕЛАТЬ


        // временная дичь
        if(indexGenre==0){
            LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver as BroadcastReceiver, IntentFilter(WAVE) );  Thread.sleep(1000)

            sender("inception 2010")     ;Thread.sleep(2000)
            sender("lost 2004")
        }

    }

    private fun createReceiver():BroadcastReceiver{
        val receiver = object : BroadcastReceiver(){
            override fun onReceive(p0: Context?, intent: Intent?) {
                intent?.let {
                    it.getParcelableExtra<MoviesDTO>(BUNDLE_MOVIES_DTO_KEY)?.let {
                        moviesFromInternet.set(indexGenre++,moviesDTOinListMovies(it))
                    }
                }
            }
        }
        return receiver
    }

    private fun sender(typeMovies: String) {
        requireActivity().startService(Intent(requireContext(), IntentServiceMoviesDTO::class.java).apply {
            putExtra(BUNDLE_TYPE_MOVIES_KEY,typeMovies)
        })
    }

    private fun sender1(typeMovies: String) {

        val intent = Intent(requireContext(), IntentServiceMoviesDTO::class.java)

        requireActivity().startService(intent)

        intent.apply {
            putExtra(BUNDLE_TYPE_MOVIES_KEY,typeMovies)
        }
    }

    // ПЕРЕНЕСУ В РЕПОЗИТОРИЙ
    @RequiresApi(Build.VERSION_CODES.N)
    fun downLoad(){
        var results:List<Result>
        var strings = mutableListOf<String>()

        MoviesLoader.request{ moviesDTO ->
            requireActivity().runOnUiThread{

                results = moviesDTO.results

                results.forEach(){
                    strings.add(it.title)
                    //moviesFromInternet1 .add(Movie(it.title))
                }

            }
        }
    }

    // ВРЕМЕННО ПОД СЕРВЕР
    fun moviesDTOinListMovies(moviesDTO: MoviesDTO):MutableList<Movie>{
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
                //updateItemGenre(listItemsGenres[1],"FANTASY",appState.movieList)
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

        if(moviesFromInternet[0].size>0){ updateItemGenre(listItemsGenres[1],"inception 2010",moviesFromInternet[0]) } // временно
        if(moviesFromInternet[1].size>0){ updateItemGenre(listItemsGenres[2],"lost 2004",moviesFromInternet[1]) }      // временно
        //viewModel.sentRequest() // реализовать
    }

    fun snackBarMenu(){
        ListMoviesSnackBar.snackBarMenu(this)
    }
    ///////////////////////////////////

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




