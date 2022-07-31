package java.android.cinema.model.test

import java.android.cinema.domen.Movie
import java.android.cinema.model.MoviesCallback
import java.android.cinema.model.RepositoryMovies
import java.io.IOException

class RepositoryMoviesTestImpl: RepositoryMovies {
    override fun getListMovies(stringGenre: String, callback: MoviesCallback) {
        Thread{
            var currentMovie = Movie("")

            if(stringGenre == "comedy"){
                Thread.sleep(1000)

                val movies = mutableListOf<Movie>()

                currentMovie = Movie("Тупой и ещё тупее")
                currentMovie.setDescription(10,100)
                movies.add(currentMovie)
                movies.add(Movie("Джентельмены удачи"))
                movies.add(Movie("В Джазе только девушки"))
                movies.add(Movie("Папаши"))
                movies.add(Movie("Пушки, деньги, два ствола"))
                callback.onResponse(movies)
            }

            if(stringGenre == "fantasy"){
                Thread.sleep(2000)

                val movies = mutableListOf<Movie>()
                movies.add(Movie("День сурка"))
                movies.add(Movie("Солярис"))
                movies.add(Movie("Назад в будущее"))
                //callback.onResponse(movies)

                callback.onFailure(IOException())
            }

            if(stringGenre == "animation"){
                Thread.sleep(3000)

                val movies = mutableListOf<Movie>()
                movies.add(Movie("Ну погоди"))
                movies.add(Movie("Том и Джери"))
                movies.add(Movie("Симпсоны"))
                movies.add(Movie("Тимон и Пумба"))
                callback.onResponse(movies)
            }

        }.start()
    }
}