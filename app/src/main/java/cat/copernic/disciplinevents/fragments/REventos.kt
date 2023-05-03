package cat.copernic.disciplinevents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cat.copernic.disciplinevents.R
import cat.copernic.disciplinevents.adapters.REventosAdapter
import cat.copernic.disciplinevents.databinding.FragmentREventosBinding
import cat.copernic.disciplinevents.model.Event

class REventos : Fragment() {

    private lateinit var binding: FragmentREventosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflate the layout for this fragment init binding
        binding = FragmentREventosBinding.bind(view)

        //Call fun initRecyclerView
        initRecyclerView()

        //Nav to CreateEvent
        binding.btnSuma.setOnClickListener{
            //Nav to CreateEvent
            findNavController().navigate(R.id.createEvent)
        }
    }

    private fun initRecyclerView(){
        // Save recyclerEvents in recyclerView
        val recyclerView = binding.recyclerEvents
        // Layout Manager
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Select Adapter and pass parameters (listEvent and function onItemSelect with iterator)
        recyclerView.adapter = REventosAdapter(REventosProvider.listEvents, {onItemSelected(it)})
    }

    private fun onItemSelected(event: Event){

        //NavDirections with obj parameter
        val action = REventosDirections.actionREventosToEditInfoEvent(event)
        findNavController().navigate(action)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_r_eventos, container, false)
    }
}
