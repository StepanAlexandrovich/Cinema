package java.android.cinema.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result(
    val description: String,
    val id: String,
    val image: String,
    val resultType: String,
    val title: String
): Parcelable