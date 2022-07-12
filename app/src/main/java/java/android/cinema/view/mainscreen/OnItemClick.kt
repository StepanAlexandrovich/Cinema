package java.android.cinema.view.mainscreen

import java.android.cinema.domen.Movie

fun interface OnItemClick {
    fun onItemClick(movie: Movie)
}