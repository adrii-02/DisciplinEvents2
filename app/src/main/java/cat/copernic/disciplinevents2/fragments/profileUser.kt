package cat.copernic.disciplinevents2.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cat.copernic.disciplinevents2.DAO.UserDAO
import cat.copernic.disciplinevents2.Utils.Utils
import cat.copernic.disciplinevents2.databinding.FragmentProfileUserBinding
import cat.copernic.disciplinevents2.model.Event
import cat.copernic.disciplinevents2.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


class profileUser : Fragment() {

    private lateinit var binding: FragmentProfileUserBinding

    //Obj User temporal
    private var user = User("", "", "", "", false,  ArrayList<Event>())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //OnClick btn edit
        binding.btnEditar.setOnClickListener{

            //Nav to editProfileUser with obj parameter
            val action = profileUserDirections.actionProfileUserToEditProfileUser(user)
            findNavController().navigate(action)
        }

    }

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment init binding
            binding = FragmentProfileUserBinding.inflate(inflater, container, false)

            getUser().addOnSuccessListener { task ->

                user = task!!

                //Add values of object in Layout
                binding.nombre.text = user.name
                binding.apellido.text = user.lastName
                binding.genero.text = user.gender
                binding.email.text = user.email
            }

            lifecycleScope.launch {
                Utils.cargarImagenDesdeUrl(requireContext(), binding.imgUsuario, Utils.getUserId())
            }

            return binding.root
    }

    private fun getUser(): Task<User?> {

        val db = FirebaseFirestore.getInstance()
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
        }
    }

}