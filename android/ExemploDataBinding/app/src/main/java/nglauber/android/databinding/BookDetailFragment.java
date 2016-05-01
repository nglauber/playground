package nglauber.android.databinding;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import nglauber.android.databinding.databinding.FragmentDetailBookBinding;
import nglauber.android.databinding.model.Book;


public class BookDetailFragment extends Fragment {
    private static final String EXTRA_BOOK = "livro";

    public static BookDetailFragment newInstance(Book book) {
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        Parcelable p = Parcels.wrap(book);
        args.putParcelable(EXTRA_BOOK, p);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Book book = null;
        if (getArguments() != null) {
            Parcelable p = getArguments().getParcelable(EXTRA_BOOK);
            book = Parcels.unwrap(p);
        }
        View view = inflater.inflate(R.layout.fragment_detail_book, container, false);

        FragmentDetailBookBinding fdlb = FragmentDetailBookBinding.bind(view);
        fdlb.setBook(book);

        return view;
    }
}
