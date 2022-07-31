package java.android.cinema.view.view_movie

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

import java.android.cinema.databinding.FragmentMovieBinding
import java.android.cinema.domen.Movie
import java.android.cinema.view.view_movie.extra.CustomDialogFragmentWithView
import java.android.cinema.view.view_movie.extra.CustomDialogListener

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.squareup.picasso.Picasso
import java.android.cinema.PublicSettings
import java.android.cinema.R

class MovieFragment: Fragment(){

    companion object {
        var currentMovie:Movie? = null
        fun newInstance(movie: Movie): MovieFragment {
            currentMovie = movie
            return MovieFragment()
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

        // menu
        val myToolbar = view.findViewById<Toolbar>(R.id.toolbarMovie)
        (requireActivity() as AppCompatActivity).setSupportActionBar(myToolbar)
        setHasOptionsMenu(true)

        renderData()
    }

    private fun renderData(){
        binding.textViewTitle.text = currentMovie!!.title
        binding.textViewDescription.text = currentMovie!!.getDescription()

        if(currentMovie!!.urlImage!=null){
            Picasso.get().load(currentMovie!!.urlImage).into(binding.imageViewDetails)
        }

    }

    fun createDialog(){
        val dialog = CustomDialogFragmentWithView();

        CustomDialogFragmentWithView.changeRating( currentMovie!!.userRating )

        dialog.setterListener( object : CustomDialogListener {
            override fun onOk() {
                currentMovie!!.changeRating(CustomDialogFragmentWithView.rating.toDouble())
            }

            override fun onNo() {}
        })

        dialog.show(requireActivity().supportFragmentManager, CustomDialogFragmentWithView.TAG)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(PublicSettings.mode != PublicSettings.modeDataBase){
            inflater.inflate(R.menu.menu_movie_add, menu)
        }
        inflater.inflate(R.menu.menu_movie_review, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return ListenerMenuMovie(this).switchItems(item)
    }

}


