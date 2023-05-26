package cat.copernic.disciplinevents2.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.disciplinevents2.DAO.EventDAO
import cat.copernic.disciplinevents2.DAO.UserDAO
import cat.copernic.disciplinevents2.R
import cat.copernic.disciplinevents2.Utils.Utils
import cat.copernic.disciplinevents2.adapters.REventosAdapter
import cat.copernic.disciplinevents2.databinding.FragmentREventosBinding
import cat.copernic.disciplinevents2.model.Event
import cat.copernic.disciplinevents2.model.Time
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hotchemi.android.rate.AppRate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class REventos : Fragment() {

    private lateinit var binding: FragmentREventosBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var bd: FirebaseFirestore
    private lateinit var listEvents: ArrayList<Event>
    private lateinit var recyclerView: RecyclerView
    private lateinit var rEventosAdapter: REventosAdapter

    // Definir las constantes para el canal de notificación
    private val CHANNEL_ID = "my_channel"
    private val CHANNEL_NAME = "My Notification Channel"
    private val CHANNEL_DESCRIPTION = "This is my notification channel"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflate the layout for this fragment init binding
        binding = FragmentREventosBinding.bind(view)

        //Call fun initRecyclerView
        initRecyclerView()

        // Google Play
        AppRate.with(requireActivity()).setInstallDays(0).setLaunchTimes(2).setRemindInterval(1).monitor()
        AppRate.showRateDialogIfMeetsConditions(requireActivity())

        //Nav to CreateEvent
        binding.btnSuma.setOnClickListener{
            //Nav to CreateEvent
            findNavController().navigate(R.id.createEvent)
        }
    }

    private fun initRecyclerView(){

        getEvents().addOnSuccessListener { events ->

            //listEvents
            listEvents = events

            // Use the events list here to initialize the RecyclerView adapter
            recyclerView = binding.recyclerEvents
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            rEventosAdapter = REventosAdapter(listEvents, { onItemEditSelected(it) }, { onItemSelected(it) }, { onItemDeleteSelected(it) })
            recyclerView.adapter = rEventosAdapter

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

    private fun onItemDeleteSelected(event: Event){

        //Delete Item
        deleteEvent(event)

        //Recharge RecyclerView
        rEventosAdapter.deleteEvent(event)

        showNotification(requireContext(), "El Evento: " + event.name, "Ha sido eliminado")

    }


    fun showNotification(context: Context, title: String, message: String) {
        val notificationManager = getSystemService(context, NotificationManager::class.java)

        // Create the notification channel (required on Android 8.0 and above)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = CHANNEL_DESCRIPTION
                enableLights(true)
                lightColor = Color.GREEN
            }
            notificationManager?.createNotificationChannel(channel)
        }

        // Build the notification
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_disciplin_events)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        // Show the notification
        notificationManager?.notify(1, notification)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment init binding
        binding = FragmentREventosBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getEvents(): Task<ArrayList<Event>> {
        val events = ArrayList<Event>()

        auth = Utils.getCurrentUser()
        bd = Utils.getCurrentDB()
        val query = bd.collection("eventos").whereEqualTo("usuarioId", Utils.getUserId())
        return query.get().addOnSuccessListener { result ->
            for (document in result) {
                val data = document.data
                val idEvent = document.id
                val name = data["nombre"] as String
                val description = data["descripcion"] as String
                val email = data["usuarioId"] as String
                val listTimes = ArrayList<Time>()

                val event = Event(idEvent, name, description, email, listTimes)
                events.add(event)
            }
        }.continueWith { task ->
            events
        }
    }

    private fun deleteEvent(event: Event) {
        bd = Utils.getCurrentDB()
        val eventRef = bd.collection("eventos").document(event.idEvent)
        val horariosRef = eventRef.collection("horarios")

        horariosRef.get()
            .addOnSuccessListener { horariosSnapshot ->
                bd.runTransaction { transaction ->
                    for (horarioDoc in horariosSnapshot) {
                        transaction.delete(horarioDoc.reference)
                    }

                    transaction.delete(eventRef)
                }
                    .addOnSuccessListener {
                        Log.d("TAG", "Evento y horarios eliminados correctamente")
                    }
                    .addOnFailureListener { e ->
                        Log.e("TAG", "Error al eliminar el Evento y horarios", e)
                    }
            }
            .addOnFailureListener { e ->
                Log.e("TAG", "Error al obtener la lista de horarios", e)
            }
    }
}
