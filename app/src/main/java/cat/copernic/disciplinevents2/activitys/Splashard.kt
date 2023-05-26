package cat.copernic.disciplinevents2.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cat.copernic.disciplinevents2.DAO.UserDAO
import cat.copernic.disciplinevents2.R
import cat.copernic.disciplinevents2.Utils.Utils
import cat.copernic.disciplinevents2.model.Event
import cat.copernic.disciplinevents2.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.concurrent.schedule


class Splashard : AppCompatActivity() {
    private lateinit var userDAO: UserDAO
    var timer = Timer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //INIT DAO
        userDAO = UserDAO()

        //HIDE APP BAR
        if (supportActionBar != null)
            supportActionBar!!.hide()

        setContentView(R.layout.activity_splashard)



        timer.schedule(500) {

            val user = Firebase.auth.currentUser

            if (user != null) {

                getUser().addOnSuccessListener { task ->

                    if(task == null){
                        startActivity(
                            Intent(this@Splashard, Register::class.java))
                        finish()

                        return@addOnSuccessListener
                    }

                    var currentUser = task!!

                    if (currentUser.admin!!) {
                        startActivity(
                            Intent(this@Splashard, MainActivityAdmin::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this@Splashard, MainActivity::class.java))
                        finish()
                    }
                }.addOnFailureListener{ e ->
                    Log.e("Error en inicio sesión", e.message.toString())
                }

            } else {
                var intent = Intent(this@Splashard, Login::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun getUser(): Task<User?> {

        val db = Utils.getCurrentDB()
        val userId = Utils.getUserId()
        val documentRef = db.collection("usuarios").document(userId)

        return documentRef.get().continueWith { task ->
            val document = task.result
            if (document != null && document.exists()) {
                val data = document.data
                val name = data?.get("nombre") as String?
                val lastName = data?.get("apellidos") as String?
                val gender = data?.get("genero") as String?
                val email = data?.get("email") as String?
                val admin = data?.get("admin") as Boolean?
                val listEvents = ArrayList<Event>()

                val user = User(name, lastName, email, gender, admin, listEvents)
                //user.id = document.id

                user
            } else {
                Log.d("TAG", "No se encontró el usuario con ID: $userId")
                null
            }
        }.addOnFailureListener { e ->
            Log.e("TAG", "Error al obtener el usuario con ID: $userId", e)

            startActivity(
                Intent(this@Splashard, Register::class.java))
            finish()
        }
    }
}