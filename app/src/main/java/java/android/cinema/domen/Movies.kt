package java.android.cinema.domen

class Movies {
    val genres = mutableListOf<Genre>()

    init{
        var genre:Genre? = null

            genre = Genre("comedy")
        genre.addMovie(Movie("Тупой и ещё тупее"))
        genre.addMovie(Movie("Джентельмены удачи"))
        genre.addMovie(Movie("В джазе только девушки"))
        genre.addMovie(Movie("Папаши"))
        genre.addMovie(Movie("Пушки, деньги, два ствола"))
        genres.add(genre)

            genre = Genre("fantasy")
        genre.addMovie(Movie("День сурка"))
        genre.addMovie(Movie("Солярис"))
        genres.add(genre)

            genre = Genre("animated")
        genre.addMovie(Movie("Ну погоди"))
        genre.addMovie(Movie("Тои и Джери"))
        genres.add(genre)
    }

    inner class Genre(val title:String){
        val list = mutableListOf<Movie>()

        fun addMovie(movie: Movie){
            list.add(movie)
        }
    }

    fun addMovie(indexGenre:Int,movie: Movie){
        genres[indexGenre].addMovie(movie)
    }

}