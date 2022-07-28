package java.android.cinema.a_take_away_out_proect

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.android.cinema.R
import java.android.cinema.view.view_movies.ListMoviesFragment

object ListMoviesSnackBar {

    fun snackBarMenu(fragment: ListMoviesFragment){
        val linearLayout = fragment.binding.listGenres
        val snackBar: Snackbar = Snackbar.make(linearLayout,"", Snackbar.LENGTH_INDEFINITE)
        val custom: View = fragment.layoutInflater.inflate(R.layout.snackbar_custom,null)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarLayout: Snackbar.SnackbarLayout = snackBar.view as Snackbar.SnackbarLayout
        snackBarLayout.setPadding(0,0,0,0)

        custom.findViewById<View>(R.id.buttonDismissSnackBarMenu).setOnClickListener(View.OnClickListener {
            snackBar.dismiss()
        })

        snackBarLayout.addView(custom)
        snackBar.show()
    }

}