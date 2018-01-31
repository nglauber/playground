package com.example.nglauber.archcompsample

import android.databinding.BindingAdapter
import android.widget.TextView

class TextViewAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter("android:text")
        fun intToText(textView: TextView, value: Int) {
            if (value.toString() != textView.text.toString()) {
                textView.text = value.toString()
            }
        }
    }
}