package dominando.android.http

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import dominando.android.http.model.Volume
import kotlinx.android.synthetic.main.content_book_details.*

class BookDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        val book = intent.getParcelableExtra("book") as Volume
        Glide.with(this).load(book.volumeInfo.imageLinks?.thumbnail).into(imgCover)

        txtTitle.text = book.volumeInfo.title
        txtSubtitle.text = book.volumeInfo.subtitle
        txtPublisher.text = getString(R.string.publisher, book.volumeInfo.publisher ?: "")
        txtAuthor.text = getString(R.string.author, book.volumeInfo.authors?.joinToString(", ") ?: "-")
        txtPages.text = getString(R.string.n_paginas, book.volumeInfo.pageCount)
        txtDescription.text = book.volumeInfo.description
    }
}
