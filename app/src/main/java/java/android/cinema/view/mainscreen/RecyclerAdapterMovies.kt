package java.android.cinema.view.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.android.cinema.PublicSettings
import java.android.cinema.activity.MainActivity
import java.android.cinema.databinding.ItemMovieBinding

class RecyclerAdapterMovies(private val index:Int, private val onItemClick: OnItemClick): RecyclerView.Adapter<RecyclerAdapterMovies.ViewHolder>() {

    private val movies = MainActivity.localMovies.genres[index].list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(position:Int){
            val binding = ItemMovieBinding.bind(itemView)

            if(PublicSettings.isAdult){
                openItem(binding,position)
            }else{
                if(!movies[position].adultBorder){ openItem(binding,position) }
                else           { binding.textViewItem.text = "не рек до 18" }
            }

            if(movies[position].urlImage!=null){
                Picasso.get().load(movies[position].urlImage!!).into(binding.imageView)
            }

        }

        private fun openItem(binding:ItemMovieBinding,position:Int){
            binding.textViewItem.text = movies[position].title

            binding.root.setOnClickListener(View.OnClickListener {
                onItemClick.onItemClick(movies[position])
            })
        }
    }


}