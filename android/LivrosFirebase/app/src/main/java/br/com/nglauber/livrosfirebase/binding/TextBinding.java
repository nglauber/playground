package br.com.nglauber.livrosfirebase.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.TextView;

import br.com.nglauber.livrosfirebase.R;
import br.com.nglauber.livrosfirebase.model.MediaType;

public class TextBinding {
    @BindingAdapter({"android:text"})
    public static void setMediaTypeText(TextView textView, MediaType mediaType){
        if (mediaType == null){
            textView.setText(null);
            return;
        }

        Context context = textView.getContext();
        switch (mediaType) {
            case EBOOK:
                textView.setText(context.getString(R.string.text_book_media_ebook));
                break;
            case PAPER:
                textView.setText(context.getString(R.string.text_book_media_paper));
                break;
            default:
                textView.setText(null);
        }
    }
}
