package cat.copernic.disciplinevents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cat.copernic.disciplinevents.R
import cat.copernic.disciplinevents.databinding.FragmentProfileUserBinding
import cat.copernic.disciplinevents.model.Event
import cat.copernic.disciplinevents.model.User


class profileUser : Fragment() {

    private lateinit var binding: FragmentProfileUserBinding
    //Obj User temporal
    private val user = User("juanito", "mercedes", "juanito.mercedes@gmail.com", "masculino", ArrayList<Event>())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
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

            //Add values of object in Layout
            binding.nombre.text = user.name
            binding.apellido.text = user.lastName
            binding.genero.text = user.gender

            return binding.root
    }

}