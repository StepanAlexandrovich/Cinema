package java.android.cinema.view.view_movies

import java.android.cinema.domen.Movie

fun interface OnItemClick {
    fun onItemClick(movie: Movie)
}