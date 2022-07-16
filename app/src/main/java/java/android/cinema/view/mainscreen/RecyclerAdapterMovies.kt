package java.android.cinema.view.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.android.cinema.PublicSettings

import java.android.cinema.databinding.ItemMovieBinding
import java.android.cinema.domen.Movie
import java.android.cinema.internet.WebViewDownloader

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

            if(PublicSettings.isAdult){
                openItem(binding,position)
            }else{
                if(!list[position].adultBorder){ openItem(binding,position) }
                else           { binding.textViewItem.text = "не рек до 18" }
            }

            if(list[position].urlImage!=null){
                WebViewDownloader.download2(list[position].urlImage!!,binding.imageView)
            }

        }

        private fun openItem(binding:ItemMovieBinding,position:Int){
            binding.textViewItem.text = list[position].title
            binding.root.setOnClickListener(View.OnClickListener {
                onItemClick.onItemClick(list[position])
            })
        }
    }



}