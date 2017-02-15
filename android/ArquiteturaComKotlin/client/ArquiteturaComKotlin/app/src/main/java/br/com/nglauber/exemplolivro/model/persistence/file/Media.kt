package br.com.nglauber.exemplolivro.model.persistence.file

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class Media {
    companion object {
        fun saveImageFromUri(context : Context, origin : Uri, destination : File) : Boolean {
            try {
                if (!destination.exists()){
                    destination.createNewFile()
                }
                val input: InputStream = context.contentResolver.openInputStream(origin)
                val bmp = BitmapFactory.decodeStream(input)

                val bytes = FileOutputStream(destination)
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                return true

            } catch (e : Exception){
                e.printStackTrace()
                return false
            }
        }
    }
}