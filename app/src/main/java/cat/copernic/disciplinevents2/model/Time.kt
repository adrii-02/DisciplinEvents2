package cat.copernic.disciplinevents2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Time(
    var idTime: String,
    var date: String,
    var time: String
): Parcelable
