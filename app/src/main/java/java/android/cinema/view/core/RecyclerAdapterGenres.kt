package java.android.cinema.view.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.android.cinema.databinding.ItemGenreBinding

import java.android.cinema.listeners.OnItemClick

class RecyclerAdapterGenres(val onItemClick: OnItemClick) : RecyclerView.Adapter<RecyclerAdapterGenres.ViewHolder>() {

    private val listGenres = listOf<String>("ANY GENRE","ANY GENRE","ANY GENRE","ANY GENRE","ANY GENRE")

    var listItems:MutableList<View> = mutableListOf()

    fun resetListItems(){
        listItems = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return listGenres.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(position:Int){
            val binding = ItemGenreBinding.bind(itemView)
            binding.textViewGenre.text = listGenres[position]

            listItems.add(itemView)

            val recyclerView = binding.rv
            recyclerView.layoutManager = LinearLayoutManager(itemView.context,RecyclerView.HORIZONTAL,false)
            recyclerView.adapter = RecyclerAdapterMovies(onItemClick)
        }
    }

}