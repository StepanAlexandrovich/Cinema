package java.android.cinema.domen

import java.android.cinema.PublicSettings

class Movies {
    var genres = mutableListOf<Genre>()
    var genre:Genre = Genre("default")

    init {
        resetAll()
    }

    fun resetAll(){
        genres = mutableListOf<Genre>()
        repeat(PublicSettings.maxNumberOfGenres){
            addGenre("default")
        }
    }

    class Genre(val title:String){
        var list = mutableListOf<Movie>()

        fun addMovie(movie: Movie){
            list.add(movie)
        }
    }

    //// TOOLS ////

    fun addGenreWithMovies(nameGenre:String, movies: MutableList<Movie>){
        val genre = Genre(nameGenre)
        genre.list = movies
        genres.add(genre)
    }

    fun setGenreWithMovies(indexGenre:Int, nameGenre:String, movies: MutableList<Movie>){
        val genre = Genre(nameGenre)
        genre.list = movies
        genres[indexGenre] = genre
    }

    fun addGenre(name:String){
        genre = Genre(name)
        genres.add(genre)
    }

    fun addMovie(movie: Movie){
        genre.addMovie(movie)
    }

}