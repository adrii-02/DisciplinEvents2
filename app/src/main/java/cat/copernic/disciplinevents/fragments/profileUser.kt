package cat.copernic.disciplinevents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cat.copernic.disciplinevents.R
import cat.copernic.disciplinevents.databinding.FragmentProfileUserBinding


class profileUser : Fragment() {

    private lateinit var binding: FragmentProfileUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //OnClick btn edit
        binding.btnEditar.setOnClickListener{
            //Nav to editProfileUser
            findNavController().navigate(R.id.action_profileUser_to_editProfileUser)
        }

    }

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment init binding
            binding = FragmentProfileUserBinding.inflate(inflater, container, false)
            return binding.root
    }

}