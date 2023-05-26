package cat.copernic.disciplinevents2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.disciplinevents2.Utils.Utils
import cat.copernic.disciplinevents2.databinding.FragmentProfileUserBinding
import cat.copernic.disciplinevents2.databinding.FragmentProfileUserForAdminBinding
import kotlinx.coroutines.launch


class profileUserForAdmin : Fragment() {

    private lateinit var binding: FragmentProfileUserForAdminBinding
    private val args by navArgs<profileUserForAdminArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //OnClick btn edit
        binding.btnEditar.setOnClickListener{

            //Nav to editProfileUser with obj parameter
            val action = profileUserForAdminDirections.actionProfileUserForAdminToEditProfileUserForAdmin(args.currentUser)
            findNavController().navigate(action)
        }

        //OnClick btn edit
        binding.btnVolver.setOnClickListener{

            //Nav to editProfileUser with obj parameter
            val action = profileUserForAdminDirections.actionProfileUserForAdminToRUsers()
            findNavController().navigate(action)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment init binding
        binding = FragmentProfileUserForAdminBinding.inflate(inflater, container, false)

            //Add values of object in Layout
            binding.nombre.text = args.currentUser.name
            binding.apellido.text = args.currentUser.lastName
            binding.genero.text = args.currentUser.gender
            binding.email.text = args.currentUser.email

        lifecycleScope.launch {
            Utils.cargarImagenDesdeUrl(requireContext(), binding.imgUsuario, args.currentUser.email.toString())
        }

        return binding.root
    }
}