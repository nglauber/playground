package br.com.nglauber.livrosfirebase.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import br.com.nglauber.livrosfirebase.R;
import br.com.nglauber.livrosfirebase.model.Book;

/**
 * Created by nglauber on 6/15/16.
 */
public class BoookAdapter extends FirebaseRecyclerAdapter<Book, BookViewHolder> {

    private BookClickListener mListener;

    public BoookAdapter(Query ref, BookClickListener listener) {
        super(Book.class, R.layout.item_book, BookViewHolder.class, ref);
        mListener = listener;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BookViewHolder holder = super.onCreateViewHolder(parent, viewType);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    int position = holder.getAdapterPosition();
                    Book book = getItem(position);
                    book.setId(getRef(position).getKey());
                    mListener.onBookClicked(book);
                }
            }
        });

        return holder;
    }

    @Override
    protected void populateViewHolder(BookViewHolder viewHolder, Book model, int position) {
        viewHolder.setLivro(model);
    }

    public interface BookClickListener {
        void onBookClicked(Book book);
    }
}
