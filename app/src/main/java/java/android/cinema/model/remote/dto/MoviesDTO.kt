package java.android.cinema.model.remote.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesDTO(

    val results: List<Result>

):Parcelable