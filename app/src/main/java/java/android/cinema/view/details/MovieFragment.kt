package java.android.cinema.view.details


import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

import java.android.cinema.databinding.FragmentMovieBinding
import java.android.cinema.domen.Movie
import java.android.cinema.internet.WebViewDownloader
import java.android.cinema.utils.PrintVisible
import java.android.cinema.view.CustomDialogFragmentWithView
import java.android.cinema.view.CustomDialogListener

import android.view.View.OnClickListener
import java.android.cinema.MyApp
import java.android.cinema.model.room.RoomUtils

class MovieFragment: Fragment(),OnClickListener {

    companion object {
        var currentMovie:Movie? = null
        fun newInstance(movie: Movie): MovieFragment {
            val fr = MovieFragment()
            return fr
        }
    }

    lateinit var binding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.buttonReview.setOnClickListener(View.OnClickListener {
            //createDialog(view)
        //})

        binding.buttonReview.setOnClickListener(this)
        binding.buttonAddMovie.setOnClickListener(this)

        renderData()
    }

    private fun renderData(){
        binding.textViewTitle.text = currentMovie!!.title
        binding.textViewDescription.text = currentMovie!!.getDescription()

        if(currentMovie!!.urlImage!=null){
            WebViewDownloader.download(currentMovie!!.urlImage!!,binding.webViewImage)
        }

    }

    private val dialog = CustomDialogFragmentWithView();
    private fun createDialog(view:View){

        CustomDialogFragmentWithView.changeRating( currentMovie!!.rating )

        dialog.setterListener( object : CustomDialogListener {
            override fun onOk() {
                currentMovie!!.changeRating(CustomDialogFragmentWithView.rating.toDouble())
            }

            override fun onNo() {}

        })

        dialog.show(requireActivity().supportFragmentManager,CustomDialogFragmentWithView.TAG)
    }

    override fun onClick(p0: View?) {

        when (p0?.getId()) {
            binding.buttonReview.id     -> { createDialog(p0)  }
            binding.buttonAddMovie.id   -> { RoomUtils.addMovie(currentMovie!!)
                //Thread{
                    //MyApp.getMovieDatabase().movieDao().insertRoom( RoomUtils.convertMovieToEntity( currentMovie!! ))
                //}.start()
            }
        }
    }

}


