package id.yanuar.moovis.presentation.moviedetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import id.yanuar.moovis.R
import id.yanuar.moovis.data.local.entity.Cast
import kotlinx.android.synthetic.main.item_cast.view.*

class CastAdapter(val listener: OnCastClickListener) : RecyclerView.Adapter<CastAdapter.CastHolder>() {

    var data: MutableList<Cast> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastHolder {
        val vh = CastHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false))
        vh.itemView.setOnClickListener {
            listener.onCastClick(data[vh.adapterPosition])
        }
        return vh
    }

    override fun onBindViewHolder(holder: CastHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount() = data.size

    interface OnCastClickListener {
        fun onCastClick(item: Cast)
    }

    class CastHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Cast) = with(itemView) {
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/w500/${item.profilePath}")
                    .into(imageProfile)

            tvCastName.text = item.name
            tvCastCharacter.text = item.character
        }
    }
}