package java.android.cinema.model.remote.dto

import java.android.cinema.domen.Movie
import java.android.cinema.utils.PrintVisible

object ConvertDTOinMovies {

    fun returnList(moviesDTO: MoviesDTO):MutableList<Movie>{
        val moviesFromInternet = mutableListOf<Movie>()

        if(moviesDTO.results==null){
            PrintVisible.printShort("Возможно вы исчерпали лимит")
        }else{
            moviesDTO.results.forEach(){
                val movie: Movie = Movie(it.title)
                movie.setImage(it.image)
                moviesFromInternet.add(movie)
            }
        }

        return moviesFromInternet
    }

}