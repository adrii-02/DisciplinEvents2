package cat.copernic.disciplinevents2.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cat.copernic.disciplinevents2.DAO.UserDAO
import cat.copernic.disciplinevents2.R
import cat.copernic.disciplinevents2.Utils.Utils
import cat.copernic.disciplinevents2.adapters.RUsersAdapter
import cat.copernic.disciplinevents2.databinding.FragmentRUsersBinding
import cat.copernic.disciplinevents2.model.Event
import cat.copernic.disciplinevents2.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hotchemi.android.rate.AppRate


class RUsers : Fragment() {

    private lateinit var binding: FragmentRUsersBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var bd: FirebaseFirestore
    private lateinit var rUsersAdapter: RUsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}

        initRecyclerView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflate the layout for this fragment init binding
        binding = FragmentRUsersBinding.bind(view)

        //Call fun initRecyclerView
        initRecyclerView()

        // Google Play
        AppRate.with(requireActivity()).setInstallDays(0).setLaunchTimes(2).setRemindInterval(1).monitor()
        AppRate.showRateDialogIfMeetsConditions(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_r_users, container, false)
    }

    private fun initRecyclerView(){

        getUsers().addOnSuccessListener { users ->

            // Use the events list here to initialize the RecyclerView adapter
            val recyclerView = binding.recyclerUsers
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            rUsersAdapter = RUsersAdapter(users, { onItemDeleteSelected(it) }, { onItemSelected(it) })
            recyclerView.adapter = rUsersAdapter

        }.addOnFailureListener { exception ->

            Log.println( Log.ERROR,"","No se han cargado los eventos :(")
        }
    }

    private fun onItemDeleteSelected(user: User){

        //Delete Item
        deleteUser(user)

        //Recharge RecyclerView
        rUsersAdapter.deleteUser(user)

    }

    private fun onItemSelected(user: User){
        val action = RUsersDirections.actionRUsersToProfileUserForAdmin(user)
        findNavController().navigate(action)
    }

    private fun getUsers(): Task<ArrayList<User>> {
        val listUsers = ArrayList<User>()
        auth = Utils.getCurrentUser()
        bd = Utils.getCurrentDB()
        val query = bd.collection("usuarios")
        return query.get().addOnSuccessListener { result ->
            for (document in result) {
                val data = document.data
                val name = data["nombre"] as String
                val lastname = data["apellidos"] as String
                val genero = data["genero"] as String
                val email = document.id
                val admin = data["admin"] as Boolean
                val listEvents = ArrayList<Event>()

                val user = User(name, lastname, email, genero, admin, listEvents)
                listUsers.add(user)
            }
        }.continueWith { task ->
            listUsers
        }
    }

    private fun deleteUser(user: User){
        bd = Utils.getCurrentDB()

        val documentRef = bd.collection("usuarios").document(user.email.toString())
        documentRef.delete().addOnSuccessListener {
            Log.d("TAG", "Usuario eliminado correctamente")
        }.addOnFailureListener { e ->
            Log.e("TAG", "Error al eliminar el Usuario", e)
        }
    }
}