package id.yanuar.moovis.presentation.moviedetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.yanuar.moovis.R
import id.yanuar.moovis.data.local.entity.Crew
import kotlinx.android.synthetic.main.item_crew.view.*

class CrewAdapter(val listener: OnCrewClickListener) : RecyclerView.Adapter<CrewAdapter.CrewHolder>() {

    var data: MutableList<Crew> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewHolder {
        val vh = CrewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_crew, parent, false))
        vh.itemView.setOnClickListener {
            listener.onCrewClick(data[vh.adapterPosition])
        }
        return vh
    }

    override fun onBindViewHolder(holder: CrewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount() = data.size

    interface OnCrewClickListener {
        fun onCrewClick(item: Crew)
    }

    class CrewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Crew) = with(itemView) {
            tvCrewName.text = item.name
            tvCrewJob.text = item.job
        }
    }
}