package java.android.cinema.view.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.android.cinema.activity.MainActivity
import java.android.cinema.databinding.ItemGenreBinding
import java.android.cinema.utils.CountDownTimerProgressBar

class RecyclerAdapterGenres(val onItemClick: OnItemClick) : RecyclerView.Adapter<RecyclerAdapterGenres.ViewHolder>() {

    private val movies = MainActivity.localMovies

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return movies.genres.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(position:Int){
            val binding = ItemGenreBinding.bind(itemView)
            binding.textViewGenre.text = movies.genres[position].title

            val recyclerView = binding.rv
            recyclerView.layoutManager = LinearLayoutManager(itemView.context,RecyclerView.HORIZONTAL,false)
            recyclerView.adapter = RecyclerAdapterMovies(position,onItemClick)

            binding.progressBarGenre.progress = 0
            CountDownTimerProgressBar.downLoad(binding.progressBarGenre)
        }
    }

}