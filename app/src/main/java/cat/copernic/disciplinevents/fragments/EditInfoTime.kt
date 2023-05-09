package cat.copernic.disciplinevents.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.disciplinevents.R
import cat.copernic.disciplinevents.databinding.FragmentEditInfoEventBinding
import cat.copernic.disciplinevents.databinding.FragmentEditInfoTimeBinding


class editInfoTime : Fragment() {

    private lateinit var binding: FragmentEditInfoTimeBinding
    private val args by navArgs<editInfoTimeArgs>()
    private val argsEvent by navArgs<EditInfoEventArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflate the layout for this fragment init binding
        binding = FragmentEditInfoTimeBinding.bind(view)

        //OnClick btn save
        binding.btnSave.setOnClickListener {


            //Nav to EditInfoEvent
            val action = editInfoTimeDirections.actionEditInfoTimeToInfoEvent(args.currentEvent)
            findNavController().navigate(action)
        }

        //OnClick btn cancel
        binding.btnCancelarr.setOnClickListener{
            //Nav to EditInfoEvent
            val action = editInfoTimeDirections.actionEditInfoTimeToInfoEvent(args.currentEvent)
            findNavController().navigate(action)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment init binding
        binding = FragmentEditInfoTimeBinding.inflate(inflater, container, false)

        //Add values of object in Layout
        //binding.editTextDate.text = args.currentTime.time.toString()

        return binding.root
    }
}