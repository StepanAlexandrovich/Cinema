package java.android.cinema.view.helpers

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object HelperToRecycler {

    fun createLayoutHorizontalManager(activity: AppCompatActivity):LinearLayoutManager{
        return LinearLayoutManager(activity, RecyclerView.HORIZONTAL,false)
    }

    fun createLayoutHorizontalManagerInversion(activity: AppCompatActivity):LinearLayoutManager {
        val llm = LinearLayoutManager(activity, RecyclerView.HORIZONTAL,false)
        llm.reverseLayout = true;
        llm.stackFromEnd = true;
        return llm
    }

}