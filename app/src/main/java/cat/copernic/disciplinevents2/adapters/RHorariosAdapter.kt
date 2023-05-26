package cat.copernic.disciplinevents2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.disciplinevents2.R
import cat.copernic.disciplinevents2.model.Event
import cat.copernic.disciplinevents2.model.Time

class RHorariosAdapter(
    private val listTime: ArrayList<Time>,
    private val onClickListener: (Time) -> Unit,
    private val onClickListener2: (Time) -> Unit): RecyclerView.Adapter<RHorariosViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RHorariosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RHorariosViewHolder(layoutInflater.inflate(R.layout.item_time, parent, false))
    }

    override fun getItemCount(): Int {
        return listTime.size
    }

    override fun onBindViewHolder(holder: RHorariosViewHolder, position: Int) {
        val item = listTime[position]
        holder.render(item, onClickListener, onClickListener2)
    }

    fun deleteTime(time: Time){
        val item = listTime.indexOf(time)
        listTime.remove(time)
        notifyItemRemoved(item)
    }
}