package nglauber.android.databinding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.parceler.Parcels;

import nglauber.android.databinding.model.Book;

public class BookDetailActivity extends AppCompatActivity {

    public static final String EXTRA_BOOK = "book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);

        Book book = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_BOOK));

        BookDetailFragment dlf = BookDetailFragment.newInstance(book);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detalhe, dlf, "detalhe")
                .commit();
    }
}
