package java.android.cinema.view.view_movies.listeners

import android.view.MenuItem
import androidx.fragment.app.FragmentActivity
import java.android.cinema.R
import java.android.cinema.extra.geolocation.GeolocationFragment
import java.android.cinema.extra.phone_numbers.ContentProviderFragment
import java.android.cinema.extra.push.PushFragment
import java.android.cinema.view.view_movies.MoviesFragment
import java.android.cinema.view.utilsToView.Navigation

class ListenerMenuMovies(private val activity: FragmentActivity, private val listMoviesFragment:MoviesFragment) {
    fun switchItems(item:MenuItem):Boolean{
        return when (item.itemId) {
            R.id.menuPhones -> {
                Navigation.createFragmentWithBackStack(activity, R.id.container, ContentProviderFragment())
                true
            }
            R.id.menuGeolocation -> {
                Navigation.createFragmentWithBackStack(activity,R.id.container, GeolocationFragment())
                true
            }
            R.id.menuPush -> {
                Navigation.createFragmentWithBackStack(activity,R.id.container, PushFragment())
                true
            }

            R.id.menuSelected -> {
                listMoviesFragment.fromDataBase()
                true
            }

            R.id.menuInternet -> {
                listMoviesFragment.fromInternet()
                true
            }
            else -> activity.onOptionsItemSelected(item)
        }
    }
}