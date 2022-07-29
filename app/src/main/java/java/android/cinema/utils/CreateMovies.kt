package java.android.cinema.utils

import java.android.cinema.domen.Movie
import java.android.cinema.domen.Movies

object CreateMovies {
    fun create(movies: Movies){

            movies.addGenre("comedy")
        movies.addMovie(Movie("Тупой и ещё тупее"))
        movies.addMovie(Movie("Джентельмены удачи"))
        movies.addMovie(Movie("В джазе только девушки"))
        movies.addMovie(Movie("Папаши"))
        movies.addMovie(Movie("Пушки, деньги, два ствола"))

            movies.addGenre("fantasy")
        movies.addMovie(Movie("День сурка"))
        movies.addMovie(Movie("Солярис"))

            movies.addGenre("animated")
        movies.addMovie(Movie("Ну погоди"))
        movies.addMovie(Movie("Том и Джери"))

    }

}