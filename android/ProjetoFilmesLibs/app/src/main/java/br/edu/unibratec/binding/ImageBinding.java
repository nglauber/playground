package br.edu.unibratec.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageBinding {
    @BindingAdapter({"android:src"})
    public static void setImage(ImageView imageView, String url){
        Picasso.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }
}
