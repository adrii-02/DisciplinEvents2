package cat.copernic.disciplinevents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import cat.copernic.disciplinevents.R
import cat.copernic.disciplinevents.adapters.REventosAdapter
import cat.copernic.disciplinevents.adapters.RHorariosAdapter
import cat.copernic.disciplinevents.databinding.FragmentEditInfoEventBinding
import cat.copernic.disciplinevents.databinding.FragmentInfoEventBinding
import cat.copernic.disciplinevents.databinding.FragmentProfileUserBinding
import cat.copernic.disciplinevents.databinding.FragmentREventosBinding
import cat.copernic.disciplinevents.model.Event
import cat.copernic.disciplinevents.model.Time

class EditInfoEvent : Fragment() {

    private lateinit var binding: FragmentEditInfoEventBinding
    private val args by navArgs<EditInfoEventArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflate the layout for this fragment init binding
        binding = FragmentEditInfoEventBinding.bind(view)

        //Call fun initRecyclerView
        initRecyclerView()

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

    private fun initRecyclerView(){
        val recyclerView = binding.recyclerTimes
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RHorariosAdapter(args.currentEvent.times, {onItemSelected(it)})
    }

    private fun onItemSelected(time: Time){
        val action = EditInfoEventDirections.actionEditInfoEventToEditInfoTime(time)
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        // Inflate the layout for this fragment init binding
        binding = FragmentEditInfoEventBinding.inflate(inflater, container, false)

        //Add values of object in Layout
        binding.nombreEvento.text = args.currentEvent.name
        binding.descripcionEvento.text = args.currentEvent.description

        return binding.root
    }
}