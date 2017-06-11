package br.com.nglauber.animationdemo.transition.enghaw

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.nglauber.animationdemo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_disco.view.*


class DiscoAdapter(
        private val mContext: Context,
        private val mAlba: Array<Album>,
        private val mCallback: (v: View, position: Int, album: Album) -> Unit)
            : RecyclerView.Adapter<DiscoAdapter.DiscoViewHolder>() {

    override fun getItemCount(): Int {
        return mAlba.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.item_disco, parent, false)
        val vh = DiscoViewHolder(v)
        v.setOnClickListener({ view ->
            val position = vh.adapterPosition
            mCallback(view, position, mAlba[position])
        })
        return vh
    }

    override fun onBindViewHolder(holder: DiscoViewHolder, position: Int) {
        val disco = mAlba[position]
        Picasso.with(mContext).load("file:///android_asset/enghaw/${disco.cover}").into(holder.itemView.imgCapa)
        holder.itemView.txtTitulo.text = disco.title
        holder.itemView.txtAno.text = disco.year.toString()
    }

    class DiscoViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        init {
            ViewCompat.setTransitionName(itemView.imgCapa, "cover")
            ViewCompat.setTransitionName(itemView.txtTitulo, "title")
            ViewCompat.setTransitionName(itemView.txtAno, "year")
        }
    }
}
