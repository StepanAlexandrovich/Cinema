package java.android.cinema

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.android.cinema.databinding.ActivityMainBinding
import java.android.cinema.view.viewmovies.ListMoviesFragment

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCreateFragment.setOnClickListener(this)

        //if(savedInstanceState == null){
            //supportFragmentManager.beginTransaction().replace(R.id.container,
                //SelectedMoviesFragment.newInstance()).commit()
        //}

    }

    override fun onClick(p0: View?) {
        supportFragmentManager.beginTransaction().replace(R.id.container,ListMoviesFragment.newInstance()).commit()
    }


}