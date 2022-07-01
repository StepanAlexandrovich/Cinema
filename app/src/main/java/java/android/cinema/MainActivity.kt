package java.android.cinema

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.android.cinema.databinding.ActivityMainBinding
import java.android.cinema.view.viewmovies.ListMoviesFragment

class MainActivity : AppCompatActivity(){

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.container,ListMoviesFragment.newInstance()).commit()
        }

    }

}