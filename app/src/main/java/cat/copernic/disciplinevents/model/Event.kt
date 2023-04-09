package cat.copernic.disciplinevents.model


data class Event(
    val name: String?,
    val description: String?,
    val times: ArrayList<Time>?
)
