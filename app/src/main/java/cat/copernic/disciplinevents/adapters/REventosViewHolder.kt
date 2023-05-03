package cat.copernic.disciplinevents.adapters

import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cat.copernic.disciplinevents.R
import cat.copernic.disciplinevents.databinding.ActivityMainBinding
import cat.copernic.disciplinevents.databinding.ItemEventBinding
import cat.copernic.disciplinevents.model.Event

class REventosViewHolder(view: View) : RecyclerView.ViewHolder(view)
{

    private var binding: ItemEventBinding // Binding ItemEvent

    // Init View
    init {
        binding = ItemEventBinding.bind(view)
    }

    // Fun Render
    // Save event values in layout item
    fun render(event: Event, onClickListener: (Event) -> Unit) {
        binding.nombreEvento.text = event.name
        binding.descripcion.text = event.description

        binding.btnEdit.setOnClickListener {
            onClickListener(event)
        }

    }
}