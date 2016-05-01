package nglauber.android.databinding;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nglauber.android.databinding.databinding.ItemBookBinding;
import nglauber.android.databinding.model.Book;


/**
 * Created by nglauber on 4/14/16.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    List<Book> mBooks;
    BookClickListener mListener;

    public BookAdapter(List<Book> books,
                       BookClickListener listener) {
        mBooks = books;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBookBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_book,
                parent,
                false);

        final ViewHolder vh = new ViewHolder(binding);
        vh.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            int pos = vh.getAdapterPosition();
                            Book book = mBooks.get(pos);
                            mListener.onBookClick(book);
                        }
                    }
                });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        Book book = mBooks.get(pos);
        holder.binding.setBook(book);
    }

    @Override
    public int getItemCount() {
        return mBooks != null ? mBooks.size() : 0;
    }

    public static class ViewHolder extends
            RecyclerView.ViewHolder {

        ItemBookBinding binding;

        public ViewHolder(ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
