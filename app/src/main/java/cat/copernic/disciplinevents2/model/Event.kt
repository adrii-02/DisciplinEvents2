package cat.copernic.disciplinevents2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event (
    var idEvent: String,
    var name: String,
    var description: String,
    var currentUserEmail: String?,
    var times: ArrayList<Time>,
): Parcelable
