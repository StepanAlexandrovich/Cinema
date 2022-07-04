package java.android.cinema.view.helpers

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object HelperToFragment {

    fun createFragment(activity: AppCompatActivity,fragment_container: Int,fragment: Fragment){
        activity.supportFragmentManager.beginTransaction().replace(fragment_container,fragment).commit()
    }

    fun createFragmentWithBackStack(activity: AppCompatActivity,fragment_container: Int,fragment: Fragment){
        activity.supportFragmentManager.beginTransaction().addToBackStack("").replace(fragment_container,fragment).commit()
    }

}


