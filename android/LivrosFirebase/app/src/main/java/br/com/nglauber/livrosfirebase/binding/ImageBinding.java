package br.com.nglauber.livrosfirebase.binding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageBinding {

    @BindingAdapter({"android:src"})
    public static void setImageUrl(ImageView imageView, String url){
        Glide.with(imageView.getContext())
             .load(url)
             .into(imageView);
    }

    @BindingAdapter({"android:src", "placeHolder"})
    public static void setImageUrl(ImageView imageView, String url,
                                   Drawable placeholder){
        Glide.with(imageView.getContext())
             .load(url)
             .placeholder(placeholder)
             .into(imageView);
    }
}