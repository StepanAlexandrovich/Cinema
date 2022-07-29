package java.android.cinema.domen

class Movies {
    var genres = mutableListOf<Genre>()
    var genre:Genre = Genre("default")

    class Genre(val title:String){
        var list = mutableListOf<Movie>()

        fun addMovie(movie: Movie){
            list.add(movie)
        }
    }

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

    fun resetAll(){
        genres = mutableListOf<Genre>()
        repeat(6){
            addGenre("default")
        }
    }


    fun addGenre(name:String){
        genre = Genre(name)
        genres.add(genre)
    }

    fun addMovie(movie: Movie){
        genre.addMovie(movie)
    }

}