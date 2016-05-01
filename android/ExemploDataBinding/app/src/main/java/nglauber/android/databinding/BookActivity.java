package nglauber.android.databinding;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.parceler.Parcels;

import nglauber.android.databinding.databinding.ActivityBookBinding;
import nglauber.android.databinding.model.Book;

public class BookActivity extends AppCompatActivity
    implements BookClickListener {

    ActivityBookBinding mBinding;
    BookListFragment mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_book);
        mBinding.setHandler(this);

        mListFragment = (BookListFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_list);
    }

    public void onSearchClick(View view){
        mListFragment.search(mBinding.editSearch.getText().toString());
    }

    @Override
    public void onBookClick(Book book) {
        if (getResources().getBoolean(R.bool.tablet)) {
            BookDetailFragment dlf = BookDetailFragment.newInstance(book);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detalhe, dlf, "detalhe")
                    .commit();
        } else {
            Intent it = new Intent(this, BookDetailActivity.class);
            Parcelable p = Parcels.wrap(book);
            it.putExtra(BookDetailActivity.EXTRA_BOOK, p);
            startActivity(it);
        }
    }
}
