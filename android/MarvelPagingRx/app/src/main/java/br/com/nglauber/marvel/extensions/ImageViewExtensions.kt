package br.com.nglauber.marvel.extensions

import android.widget.ImageView
import br.com.nglauber.marvel.R
import com.bumptech.glide.Glide

fun ImageView.load(url: String) {
    Glide.with(context)
            .load(url)
            .into(this);
}