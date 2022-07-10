package java.android.cinema.view.core

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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
import java.android.cinema.view.utilsToView.Navigation
import java.android.cinema.view.utilsToView.UtilsToRecycler
import java.android.cinema.viewmodel.AppState
import java.android.cinema.viewmodel.ListMoviesViewModel

class ListMoviesFragment: Fragment() {

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

        viewModel.getLiveDataComedy().observe(viewLifecycleOwner) { t -> renderData(t) }
        viewModel.getLiveDataFantasy().observe(viewLifecycleOwner) { t -> renderData(t) }
        viewModel.getLiveDataFavorites().observe(viewLifecycleOwner) { t -> renderData(t) }

        //viewModel.sentRequest()
    }

    fun updateLocalData(){
        Toast.makeText(requireContext(),"local",Toast.LENGTH_SHORT).show()

        listItemsGenres = (rvGenres.adapter as RecyclerAdapterGenres).listItems
        viewModel.sentRequest()
    }

    fun updateRemoteData(){
        Toast.makeText(requireContext(),"remote",Toast.LENGTH_SHORT).show()
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

            //snackBar.showMessage() // extension function
            snackBar.showMessage("extension function")
        })

        snackBarLayout.addView(custom)
        snackBar.show()
    }

    private fun Snackbar.showMessage(){
        Toast.makeText(requireContext(),"showMessage",Toast.LENGTH_SHORT).show()
    }

    private fun Snackbar.showMessage(text:String){
        Toast.makeText(requireContext(),text,Toast.LENGTH_SHORT).show()
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
                updateItemGenre(listItemsGenres[0],"COMEDY",appState.movieList)
            }
            is AppState.SuccessFantasy -> {
                updateItemGenre(listItemsGenres[1],"FANTASY",appState.movieList)
            }
            is AppState.SuccessFavorites -> {
                updateItemGenre(listItemsGenres[2],"ANIMATED",appState.movieList)
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




