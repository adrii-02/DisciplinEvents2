package cat.copernic.disciplinevents.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event (
    val name: String,
    val description: String,
    val times: ArrayList<Time>
): Parcelable
