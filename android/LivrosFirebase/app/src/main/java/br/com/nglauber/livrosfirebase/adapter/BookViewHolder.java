package br.com.nglauber.livrosfirebase.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import br.com.nglauber.livrosfirebase.databinding.ItemBookBinding;
import br.com.nglauber.livrosfirebase.model.Book;

public class BookViewHolder extends RecyclerView.ViewHolder {
    ItemBookBinding binding;

    public BookViewHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);
    }

    public void setLivro(Book book) {
        binding.setBook(book);
    }
}