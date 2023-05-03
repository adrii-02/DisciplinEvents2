package cat.copernic.disciplinevents.fragments

import cat.copernic.disciplinevents.model.Event
import cat.copernic.disciplinevents.model.Time
import java.util.ArrayList

class REventosProvider{
    companion object {
        val listEvents = listOf<Event>(
            Event(
                "pepito1",
                "pepitoGrillo1",
                ArrayList<Time>(RHorariosProvider.listTime)
            ),
            Event(
                "pepito2",
                "pepitoGrillo2",
                ArrayList<Time>(RHorariosProvider.listTime)
            ),
            Event(
                "pepito3",
                "pepitoGrillo3",
                ArrayList<Time>(RHorariosProvider.listTime)
            ),
            Event(
                "pepito4",
                "pepitoGrillo4",
                ArrayList<Time>(RHorariosProvider.listTime)
            ),
            Event(
                "pepito5",
                "pepitoGrillo1",
                ArrayList<Time>(RHorariosProvider.listTime)
            ),
            Event(
                "pepito6",
                "pepitoGrillo2",
                ArrayList<Time>(RHorariosProvider.listTime)
            ),
            Event(
                "pepito7",
                "pepitoGrillo3",
                ArrayList<Time>(RHorariosProvider.listTime)
            ),
            Event(
                "pepito8",
                "pepitoGrillo4",
                ArrayList<Time>(RHorariosProvider.listTime)
            )

        )
    }

}