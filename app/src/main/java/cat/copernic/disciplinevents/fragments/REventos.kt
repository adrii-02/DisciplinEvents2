package cat.copernic.disciplinevents.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cat.copernic.disciplinevents.DAO.EventDAO
import cat.copernic.disciplinevents.DAO.UserDAO
import cat.copernic.disciplinevents.R
import cat.copernic.disciplinevents.adapters.REventosAdapter
import cat.copernic.disciplinevents.databinding.FragmentREventosBinding
import cat.copernic.disciplinevents.model.Event

class REventos : Fragment() {

    private lateinit var binding: FragmentREventosBinding
    private lateinit var eventDAO: EventDAO
    private lateinit var userDAO: UserDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //INIT userDAO
        userDAO = UserDAO()
        eventDAO = EventDAO()
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

        val lisEvents = ArrayList<Event>()

        eventDAO.getEvents().addOnSuccessListener { events ->

            // Use the events list here to initialize the RecyclerView adapter
            val recyclerView = binding.recyclerEvents
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = REventosAdapter(events, { onItemEditSelected(it) }, {onItemSelected(it)})

        }.addOnFailureListener { exception ->

            Log.println( Log.ERROR,"","No se han cargado los eventos :(")
        }
    }

    private fun onItemSelected(event: Event){

        //NavDirections with obj parameter
        val action = REventosDirections.actionREventosToInfoEvent(event)
        findNavController().navigate(action)

    }

    private fun onItemEditSelected(event: Event){

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
