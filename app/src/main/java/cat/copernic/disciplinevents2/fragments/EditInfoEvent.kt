package cat.copernic.disciplinevents2.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import cat.copernic.disciplinevents2.R
import cat.copernic.disciplinevents2.databinding.FragmentEditInfoEventBinding
import cat.copernic.disciplinevents2.Utils.Utils
import cat.copernic.disciplinevents2.adapters.REventosAdapter
import cat.copernic.disciplinevents2.adapters.RHorariosAdapter
import cat.copernic.disciplinevents2.model.Event
import cat.copernic.disciplinevents2.model.Time
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditInfoEvent : Fragment() {

    private lateinit var binding: FragmentEditInfoEventBinding
    private lateinit var builder: android.app.AlertDialog.Builder
    private lateinit var auth: FirebaseAuth
    private lateinit var bd: FirebaseFirestore
    private lateinit var rHorariosAdapter: RHorariosAdapter
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

            //Changes values
            args.currentEvent.name = binding.textInputLayoutNombreContent.text.toString()
            args.currentEvent.description = binding.textInputLayoutDescriptionContent.text.toString()

            if(binding.textInputLayoutNombreContent.text.toString().isNotEmpty()&&binding.textInputLayoutDescriptionContent.text.toString().isNotEmpty())
            {
                //Call fun editEvent
                editEvent(args.currentEvent)

                //Nav to rEvents
                findNavController().navigate(R.id.action_editInfoEvent_to_REventos)
            } else {
                builder = android.app.AlertDialog.Builder(requireContext())
                builder.setTitle("Error")
                builder.setMessage("Debes rellenar todos los campos.")
                builder.setPositiveButton("Aceptar", null)
                val dialog = builder.create()
                dialog.show()
            }

        }

        //OnClick btn cancel
        binding.btnCancelar.setOnClickListener{
            //Nav to rEvents
            findNavController().navigate(R.id.action_editInfoEvent_to_REventos)
        }

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
        val action = EditInfoEventDirections.actionEditInfoEventToEditInfoTime(time, args.currentEvent)
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
        binding = FragmentEditInfoEventBinding.inflate(inflater, container, false)

        //Add values of object in Layout
        val editName = binding.textInputLayoutNombreContent.findViewById<EditText>(R.id.textInputLayoutNombreContent)
        editName.text = Editable.Factory.getInstance().newEditable(args.currentEvent.name)

        val editDescription = binding.textInputLayoutDescriptionContent.findViewById<EditText>(R.id.textInputLayoutDescriptionContent)
        editDescription.text = Editable.Factory.getInstance().newEditable(args.currentEvent.description)

        return binding.root
    }

    private fun editEvent(event: Event) {
        auth = Utils.getCurrentUser()
        bd = Utils.getCurrentDB()

        // Users
        bd.collection("eventos").document(event.idEvent).update(
            hashMapOf(
                "nombre" to event.name,
                "descripcion" to event.description,
            ) as Map<String, Any>
        ).addOnSuccessListener {

            Log.d("TAG", "Evento actualizado correctamente")
        }.addOnFailureListener { e ->
            Log.e("TAG", "Error al actualizar el Evento", e)
        }
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