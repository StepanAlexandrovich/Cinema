package java.android.cinema.view.view_movies

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.android.cinema.PublicSettings
import java.android.cinema.R
import java.android.cinema.a_take_away_out_proect.ListMoviesSnackBar
import java.android.cinema.activity.MainActivity

import java.android.cinema.databinding.FragmentListMoviesBinding

import java.android.cinema.save_settings.SharedPref
import java.android.cinema.utils.CountDownTimerProgressBar
import java.android.cinema.view.utilsToView.Navigation
import java.android.cinema.viewmodel.AppState
import java.android.cinema.viewmodel.ListMoviesViewModel

import java.android.cinema.view.view_movie.MovieFragment
import java.android.cinema.view.view_movies.listeners.ListenerButtonsMovies
import java.android.cinema.view.view_movies.listeners.ListenerMenuMovies


class ListMoviesFragment: Fragment() {

    private var _binding: FragmentListMoviesBinding? = null
    val binding: FragmentListMoviesBinding get() { return _binding!! }

    private val movies = MainActivity.localMovies

    private var imageDirectionId = R.drawable.nolimits

    private lateinit var countDownTimerProgressBar:CountDownTimerProgressBar

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

        // menu
        val myToolbar = view.findViewById<Toolbar>(R.id.toolbar)
            myToolbar.setTitle(" ") // разобраться
        (requireActivity() as AppCompatActivity).setSupportActionBar(myToolbar)
        setHasOptionsMenu(true)

        // buttons
        ListenerButtonsMovies(binding,this)

        // recycle
        rvGenres = binding.rvGenres
        rvGenres.layoutManager = LinearLayoutManager(requireContext())
        rvGenres.adapter = RecyclerAdapterGenres(OnItemClick {
            Navigation.createFragmentWithBackStack(requireActivity(), R.id.container,MovieFragment.newInstance(it))
            MovieFragment.currentMovie = it
        })

        // view model
        viewModel = ViewModelProvider(this).get(ListMoviesViewModel::class.java)
        //viewModel.liveData.observe(viewLifecycleOwner) { renderData(it) }  // не надёжно пока
        viewModel.liveDataLoad.observe(viewLifecycleOwner) { renderData(it) }  // не надёжно пока

        viewModel.liveDates.forEach(){
            it.observe(viewLifecycleOwner) { renderData(it) }
        }

        countDownTimerProgressBar = CountDownTimerProgressBar(binding.progressBarMovies)

        changeColor()
    }

    private fun renderData(appState: AppState){
        when(appState){
            is AppState.Error -> {
                //.......
            }

            AppState.Loading -> {
                loading()
            }

            is AppState.SuccessData -> {
                synchronized(movies){
                    if(appState.index == PublicSettings.mode.strings.size - 1){
                        //PrintVisible.printShort("finish")
                        countDownTimerProgressBar.finish()
                    }
                    //countDownTimerProgressBar.finish() //?????
                    movies.setGenreWithMovies(appState.index, PublicSettings.mode!!.strings[appState.index],appState.movies)
                }
            }
        }

        rvGenres.adapter?.notifyDataSetChanged()
    }

    private val myThread = Thread(){
        Handler(Looper.getMainLooper()).post{
            countDownTimerProgressBar.start()
        }
    }
    private fun loading(){
        //myThread.start()
        countDownTimerProgressBar.start()
    }

    private fun changeColor(){
        if(PublicSettings.isAdult){
            imageDirectionId = R.drawable.nolimits
            binding.imageViewAdult.setImageResource(imageDirectionId)
        }
        else {
            imageDirectionId = R.drawable.limits
            binding.imageViewAdult.setImageResource(imageDirectionId)
        }
    }

    private fun update(){
        movies.resetAll()
        viewModel.sentRequest()
    }

    fun fromDataBase(){
        //PublicSettings.mode = PublicSettings.modeTest
        PublicSettings.mode = PublicSettings.modeDataBase
        update()
    }

    fun fromInternet(){
        //PublicSettings.mode = PublicSettings.modeOkHttp
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_movies, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return ListenerMenuMovies(requireActivity(),this).switchItems(item)
    }

}




