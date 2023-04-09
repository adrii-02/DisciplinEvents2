package cat.copernic.disciplinevents.model

data class User(
    val name: String?,
    val lastName: String?,
    val gender: String?,
    val events: ArrayList<Event>?

)
