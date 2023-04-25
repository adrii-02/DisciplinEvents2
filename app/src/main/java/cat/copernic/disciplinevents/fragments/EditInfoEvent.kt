package cat.copernic.disciplinevents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cat.copernic.disciplinevents.R
import cat.copernic.disciplinevents.databinding.FragmentEditInfoEventBinding
import cat.copernic.disciplinevents.databinding.FragmentInfoEventBinding
import cat.copernic.disciplinevents.databinding.FragmentProfileUserBinding

class EditInfoEvent : Fragment() {

    private lateinit var binding: FragmentEditInfoEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {



        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //OnClick btn save
        binding.btnGuardar.setOnClickListener{
            //Nav to rEvents
            findNavController().navigate(R.id.action_editInfoEvent_to_REventos)
        }

        //OnClick btn cancel
        binding.btnCancelar.setOnClickListener{
            //Nav to rEvents
            findNavController().navigate(R.id.action_editInfoEvent_to_REventos)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        // Inflate the layout for this fragment init binding
        binding = FragmentEditInfoEventBinding.inflate(inflater, container, false)
        return binding.root
    }
}