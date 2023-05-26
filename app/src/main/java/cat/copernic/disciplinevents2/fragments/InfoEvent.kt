package cat.copernic.disciplinevents2.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import cat.copernic.disciplinevents2.R
import cat.copernic.disciplinevents2.databinding.FragmentInfoEventBinding
import cat.copernic.disciplinevents2.databinding.ItemInfoEventBinding
import cat.copernic.disciplinevents2.DAO.TimeDAO
import cat.copernic.disciplinevents2.DAO.UserDAO
import cat.copernic.disciplinevents2.Utils.Utils
import cat.copernic.disciplinevents2.adapters.REventosAdapter
import cat.copernic.disciplinevents2.adapters.RHorariosAdapter
import cat.copernic.disciplinevents2.model.Event
import cat.copernic.disciplinevents2.model.Time
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore


class InfoEvent : Fragment() {

    private lateinit var binding: FragmentInfoEventBinding
    //private lateinit var bindingItem: ItemInfoEventBinding
    private lateinit var bd: FirebaseFirestore
    private lateinit var rHorariosAdapter: RHorariosAdapter
    private val args by navArgs<InfoEventArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //OnClick btn turn
        binding.btnVolver.setOnClickListener{
            //Nav to rEvents
            findNavController().navigate(R.id.action_infoEvent_to_REventos)
        }

        binding.btnSuma.setOnClickListener {
            val action = InfoEventDirections.actionInfoEventToCreateTime(args.currentEvent)
            findNavController().navigate(action)
        }

        //Call fun initRecyclerView
        initRecyclerView()

    }

    private fun initRecyclerView(){
        getHorarios(args.currentEvent.idEvent).addOnSuccessListener { times ->
            args.currentEvent.times = times

            val recyclerView = binding.recyclerTimes
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            rHorariosAdapter = RHorariosAdapter(args.currentEvent.times, { onItemSelected(it) }, { onItemDeletedSelected(it) })
            recyclerView.adapter = rHorariosAdapter

        }.addOnFailureListener { exception ->
            Log.println( Log.ERROR,"","No se han cargado los horarios :(")
        }

    }

    private fun onItemSelected(time: Time){
        val action = InfoEventDirections.actionInfoEventToEditInfoTime(time, args.currentEvent)
        findNavController().navigate(action)
    }

    private fun onItemDeletedSelected(time: Time){
        //Delete Item
        deleteTime(args.currentEvent, time)

        //Recharge RecyclerView
        rHorariosAdapter.deleteTime(time)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        // Inflate the layout for this fragment init binding
        binding = FragmentInfoEventBinding.inflate(inflater, container, false)

        //Add values of object in Layout
        binding.nombreEvento.text = args.currentEvent.name
        binding.descripcionEvento.text = args.currentEvent.description

        return binding.root
    }

    private fun getHorarios(idEvento: String): Task<ArrayList<Time>> {
        val horarios = ArrayList<Time>()
        bd = Utils.getCurrentDB()

        // Definir una consulta que apunta a la subcolección "horarios" de un documento de evento específico
        val query = bd.collection("eventos").document(idEvento).collection("horarios")

        // Llamar al método get en la consulta definida anteriormente y agregar un escuchador para manejar la respuesta
        return query.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Si la tarea se completa con éxito, recorrer el resultado y agregar los horarios a la lista de horarios
                val result = task.result
                if (result != null) {
                    for (document in result) {
                        val data = document.data
                        val idTime = document.id
                        val horario = data["horario"] as String
                        val fecha = data["fecha"] as String
                        val time = Time(idTime, fecha, horario)
                        horarios.add(time)
                    }
                }
            } else {
                // Si la tarea no se completa con éxito, registrar un error en los registros
                val exception = task.exception
                Log.e("TAG", "Error al descargar documentos", exception)
            }
        }.continueWith { task ->
            // Devolver una tarea con la lista de horarios
            horarios
        }
    }

    private fun deleteTime(event: Event, time: Time) {
        bd = Utils.getCurrentDB()

        val documentRef = bd.collection("eventos").document(event.idEvent).collection("horarios").document(time.idTime)
        documentRef.delete().addOnSuccessListener {
            Log.d("TAG", "Horario eliminado correctamente")
        }.addOnFailureListener { e ->
            Log.e("TAG", "Error al eliminar el Horario", e)
        }
    }
}