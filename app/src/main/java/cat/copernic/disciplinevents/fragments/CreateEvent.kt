package cat.copernic.disciplinevents.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cat.copernic.disciplinevents.R
import cat.copernic.disciplinevents.databinding.FragmentCreateEventBinding
import cat.copernic.disciplinevents.databinding.FragmentREventosBinding


class CreateEvent : Fragment() {

    private lateinit var binding: FragmentCreateEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflate the layout for this fragment init binding
        binding = FragmentCreateEventBinding.bind(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_event, container, false)
    }


}