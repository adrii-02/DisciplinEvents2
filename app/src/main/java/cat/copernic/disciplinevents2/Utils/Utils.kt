package cat.copernic.disciplinevents2.Utils

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class Utils {

    companion object{
        suspend fun cargarImagenDesdeUrl(context: Context, imageView: ImageView, text: String) {
            try {
                val storageRef = FirebaseStorage.getInstance().reference
                //val text = FirebaseAuth.getInstance().currentUser?.email ?: ""
                val imageRef = storageRef.child("imatges").child("usuaris").child(text)

                val url = withContext(Dispatchers.IO) {
                    imageRef.downloadUrl.await()
                }

                withContext(Dispatchers.Main) {
                    Glide.with(context)
                        .load(url)
                        .into(imageView)
                }
            } catch (e: Exception) {
                Log.e("Error al cargar imagen", e.message.toString())
            }
        }

        // Fun getCurrentUser
        fun getCurrentUser(): FirebaseAuth {
            return Firebase.auth
        }

        //Fun getCurrentDB
        fun getCurrentDB(): FirebaseFirestore {
            return FirebaseFirestore.getInstance()
        }

        //Fun getEmail
        fun getUserId(): String {
            val currentUser = Firebase.auth.currentUser
            return currentUser?.email.toString()
        }
    }


}