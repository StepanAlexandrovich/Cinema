package java.android.cinema.view.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import java.android.cinema.databinding.ItemMovieBinding
import java.android.cinema.domen.Movie

class RecyclerAdapterMovies(private val onItemClick: OnItemClick): RecyclerView.Adapter<RecyclerAdapterMovies.ViewHolder>() {

    private var list:List<Movie> = emptyList()

    fun setList(list: List<Movie>){
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(position:Int){
            val binding = ItemMovieBinding.bind(itemView)
            binding.textViewItem.text = list[position].title

            binding.root.setOnClickListener(View.OnClickListener {
                onItemClick.onItemClick(list[position])
            })
        }
    }

}