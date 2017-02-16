package br.com.nglauber.exemplolivro.view.binding.adapter

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageAdapter {
    @BindingAdapter("android:src")
    @JvmStatic
    fun setImageUrl(imageView: ImageView, url: String?) {
        Glide.with(imageView.context).load(url).into(imageView)
    }

    @BindingAdapter("android:src", "placeHolder")
    @JvmStatic
    fun setImageUrl(imageView: ImageView, url: String?, thumbnail: Drawable) {
        Glide.with(imageView.context).load(url)
                .placeholder(thumbnail)
                .into(imageView)
    }
}
