package cat.copernic.disciplinevents.DAO

import android.util.Log
import cat.copernic.disciplinevents.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserDAO {

    // Fun getCurrentUser
    fun getCurrentUser(): FirebaseAuth {
        return Firebase.auth
    }

    //Fun getCurrentDB
    fun getCurrentDB(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    //Fun insertUser
    fun setUser(bd: FirebaseFirestore, userObj: User) {

        //Users
        bd.collection("usuarios").document(userObj.email.toString()).set(
            hashMapOf(
                "email" to userObj.email,
                "nombre" to userObj.name,
                "apellidos" to userObj.lastName,
                "genero" to userObj.gender
            )
        ).addOnSuccessListener {
            Log.d("TAG", "Usuario registrado correctamente")
        }.addOnFailureListener { e ->
            Log.e("TAG", "Error al registrar el usuario", e)
        }

    }

    //Fun getEmail
    fun getUserId(): String {
        val currentUser = Firebase.auth.currentUser
        return currentUser?.email.toString()
    }
}