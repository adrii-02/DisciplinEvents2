package cat.copernic.disciplinevents.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.disciplinevents.databinding.ItemEventBinding
import cat.copernic.disciplinevents.databinding.ItemTimeBinding
import cat.copernic.disciplinevents.model.Time

class RHorariosViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var binding: ItemTimeBinding

    init {
        binding = ItemTimeBinding.bind(view)
    }

    fun render(time: Time, onClickListener: (Time) -> Unit) {
        binding.Horario.text = time.time.toString()

        binding.btnEdit.setOnClickListener {
            onClickListener(time)
        }

    }
}