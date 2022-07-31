package java.android.cinema.view.view_movie.extra

import android.widget.EditText
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import androidx.fragment.app.DialogFragment
import java.android.cinema.R

class CustomDialogFragmentWithView : DialogFragment() {
    companion object {
        const val TAG = "CustomDialogFragmentWithViewTag"

        var rating:Float = 10f

        fun changeRating(double:Double){
            rating = double.toFloat()
        }
    }

    private var listener: CustomDialogListener? = null
    var editText: EditText? = null
    fun setterListener(listener: CustomDialogListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val customView = inflater.inflate(R.layout.dialog_custom, null)

        val ratingBar: RatingBar = customView.findViewById(R.id.ratingBar)
        ratingBar.rating = rating.toFloat()
        ratingBar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            rating = fl
        }

        customView.findViewById<View>(R.id.buttonCustomView).setOnClickListener {
            listener!!.onOk()
            dismiss()
        }
        return customView
    }


}