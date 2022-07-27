package java.android.cinema.view.details


import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

import java.android.cinema.databinding.FragmentMovieBinding
import java.android.cinema.domen.Movie
import java.android.cinema.view.CustomDialogFragmentWithView
import java.android.cinema.view.CustomDialogListener

import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.squareup.picasso.Picasso
import java.android.cinema.R
import java.android.cinema.model.room.RoomUtils
import java.android.cinema.view.mainscreen.MenuMainScreen

class MovieFragment: Fragment(),OnClickListener {

    companion object {
        var currentMovie:Movie? = null
        fun newInstance(movie: Movie): MovieFragment {
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

        binding.buttonReview.setOnClickListener(this)
        renderData()
    }

    private fun renderData(){
        binding.textViewTitle.text = currentMovie!!.title
        binding.textViewDescription.text = currentMovie!!.getDescription()

        if(currentMovie!!.urlImage!=null){
            Picasso.get().load(currentMovie!!.urlImage).into(binding.imageViewDetails)
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
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_movie, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return ListenerMenuMovie(requireActivity()).switchItems(item)
    }

}


