package java.android.cinema.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.android.cinema.R
import java.android.cinema.databinding.ActivityMainBinding
import java.android.cinema.domen.Movie
import java.android.cinema.view.utilsToView.Navigation
import java.android.cinema.view.core.MovieFragment
import java.android.cinema.view.core.WebViewFragment

class MainActivity : AppCompatActivity(){

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            //HelperToFragment.createFragmentWithBackStack(this,R.id.container,ListMoviesFragment.newInstance())
            Navigation.createFragmentWithBackStack(this,R.id.container,WebViewFragment())
            Navigation.createFragment(this,R.id.container,MovieFragment.newInstance(Movie("фильм")))
        }

    }

}