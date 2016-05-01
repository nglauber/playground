package nglauber.android.databinding;


import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nglauber.android.databinding.databinding.FragmentListBookBinding;
import nglauber.android.databinding.http.BookTask;
import nglauber.android.databinding.model.Book;


public class BookListFragment extends Fragment {

    List<Book> mBooks;
    BookAdapter mAdapter;
    BookTask mTask;
    FragmentListBookBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mBooks = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_list_book, container, false);

        mAdapter = new BookAdapter(mBooks, mListener);
        mBinding.listLivro.setAdapter(mAdapter);
        return mBinding.getRoot();
    }

    BookClickListener mListener = new BookClickListener() {
        @Override
        public void onBookClick(Book book) {
            if (getActivity() instanceof BookClickListener){
                BookClickListener listener = (BookClickListener)getActivity();
                listener.onBookClick(book);
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING) {
            mTask.setFragment(this);
        }
    }

    public void setLivros(List<Book> books){
        if (books != null) {
            mBooks.clear();
            mBooks.addAll(books);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void search(String term){
        mTask = new BookTask(this);
        mTask.execute(term);
    }

    public void showError() {
        Toast.makeText(getActivity(), R.string.msg_error_search_books, Toast.LENGTH_SHORT).show();
    }
}
