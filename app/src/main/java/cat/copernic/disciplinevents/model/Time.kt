package cat.copernic.disciplinevents.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalTime
import java.util.Date

@Parcelize
data class Time(
    val time: Date
): Parcelable
