package br.com.nglauber.livrosfirebase.binding;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.widget.EditText;

public class EditTextBinding {

    @BindingAdapter({"android:text"})
    public static void setTextFromInt(EditText editText, int value){
        if (getTextAsInt(editText) != value) {
            editText.setText(String.valueOf(value));
        }
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static int getTextAsInt(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString());
        } catch (Exception e){
            return 0;
        }
    }
}