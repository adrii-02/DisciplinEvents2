package cat.copernic.disciplinevents.fragments

import cat.copernic.disciplinevents.model.Event
import cat.copernic.disciplinevents.model.Time
import java.time.LocalDate
import java.time.LocalTime
import java.util.ArrayList
import java.util.Date

class RHorariosProvider {
    companion object {
        val listTime = listOf<Time>(
            Time(
                Date(2002, 4, 3)
            ),
            Time(Date(2004, 4, 3)),
                    Time(
                    Date(2052, 4, 3)
                    ),Time(
                    Date(2042, 4, 3)
                    ),Time(
                    Date(2062, 4, 3)
                    )


        )
    }
}