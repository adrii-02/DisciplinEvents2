package cat.copernic.disciplinevents.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val name: String?,
    val lastName: String?,
    val gender: String?,
    val events: ArrayList<Event>?

): Parcelable
