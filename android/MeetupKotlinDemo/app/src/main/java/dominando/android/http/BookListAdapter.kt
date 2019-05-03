package dominando.android.http

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import dominando.android.http.model.Volume
import kotlinx.android.synthetic.main.item_book.view.*

class BookListAdapter(context: Context, books: List<Volume>)
    : ArrayAdapter<Volume>(context, 0, books) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val book = getItem(position)
        val holder: ViewHolder
        val view: View
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(
                R.layout.item_book, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }
        book?.volumeInfo?.let {
            Glide.with(context).load(it.imageLinks?.smallThumbnail).into(holder.imgCapa)
            holder.txtTitulo.text = it.title
            holder.txtAutores.text = it.authors?.joinToString(",") ?: ""
            holder.txtAno.text = it.publishedDate
            holder.txtPaginas.text = context.getString(R.string.n_paginas, it.pageCount)
        }
        return view
    }
    internal class ViewHolder(view: View) {
        var imgCapa: ImageView = view.imgCover
        var txtTitulo: TextView = view.txtTitle
        var txtAutores: TextView = view.txtAuthors
        var txtPaginas: TextView = view.txtPages
        var txtAno: TextView = view.txtYear
    }
}
