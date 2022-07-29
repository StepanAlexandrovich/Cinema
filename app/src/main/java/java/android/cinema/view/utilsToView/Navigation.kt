package java.android.cinema.view.utilsToView

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object Navigation {

    fun createFragment(activity: AppCompatActivity,fragment_container: Int,fragment: Fragment){
        activity.supportFragmentManager.beginTransaction().replace(fragment_container,fragment).commit()
    }

    fun createFragment(activity: FragmentActivity,fragment_container: Int,fragment: Fragment){
        activity.supportFragmentManager.beginTransaction().replace(fragment_container,fragment).commit()
    }

    fun createFragmentWithBackStack(activity: AppCompatActivity,fragment_container: Int,fragment: Fragment){
        activity.supportFragmentManager.beginTransaction().addToBackStack("").replace(fragment_container,fragment).commit()
    }

    fun createFragmentWithBackStack(activity: FragmentActivity,fragment_container: Int,fragment: Fragment){
        activity.supportFragmentManager.beginTransaction().addToBackStack("").replace(fragment_container,fragment).commit()
    }

}


