package cat.copernic.disciplinevents2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.disciplinevents2.databinding.FragmentEditInfoTimeBinding
import cat.copernic.disciplinevents2.DAO.TimeDAO
import cat.copernic.disciplinevents2.DAO.UserDAO
import cat.copernic.disciplinevents2.Utils.Utils
import cat.copernic.disciplinevents2.model.Event
import cat.copernic.disciplinevents2.model.Time
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class editInfoTime : Fragment() {

    private lateinit var binding: FragmentEditInfoTimeBinding
    private lateinit var calendar: Calendar
    private lateinit var bd: FirebaseFirestore
    private val args by navArgs<editInfoTimeArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}

        //Init getInstance Calendar
        calendar = Calendar.getInstance()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflate the layout for this fragment init binding
        binding = FragmentEditInfoTimeBinding.bind(view)

        var date = ""
        var time = ""

        binding.inputDate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)) { view, year, monthOfYear, dayOfMonth ->
            date = "${dayOfMonth}/${monthOfYear+1}/${year}"
            binding.textDate.setText(date)
        }

        // Agregar un evento OnTimeChangedListener al TimePicker para obtener la hora seleccionada
        binding.inputTime.setOnTimeChangedListener { view, hourOfDay, minute ->
            time = "${hourOfDay}:${minute}"
            binding.textTime.setText(time)
        }

        //OnClick btn save
        binding.btnSave.setOnClickListener {

            //Changes values
            args.currentTime.date = binding.textDate.text.toString()
            args.currentTime.time = binding.textTime.text.toString()

            //Edit Time
            editTime(args.currentEvent, args.currentTime)

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
        binding.textDate.text = args.currentTime.date
        binding.textTime.text = args.currentTime.time

        return binding.root
    }

    private fun editTime(event: Event, time: Time) {
        bd = Utils.getCurrentDB()

        // Users
        bd.collection("eventos").document(event.idEvent).collection("horarios").document(time.idTime).update(
            hashMapOf(
                "fecha" to time.date,
                "horario" to time.time
            ) as Map<String, Any>
        ).addOnSuccessListener {

            Log.d("TAG", "Horario actualizado correctamente")
        }.addOnFailureListener { e ->
            Log.e("TAG", "Error al actualizar el Horario", e)
        }
    }
}