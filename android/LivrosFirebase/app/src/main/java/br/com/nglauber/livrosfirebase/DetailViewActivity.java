package br.com.nglauber.livrosfirebase;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.Menu;
import android.view.MenuItem;

import br.com.nglauber.livrosfirebase.databinding.ActivityDetailViewBinding;
import br.com.nglauber.livrosfirebase.model.Book;

public class DetailViewActivity extends BaseActivity {

    public static final String EXTRA_BOOK = "livro";

    private Book book;

    @Override
    protected void init() {
        book = (Book)getIntent().getSerializableExtra(EXTRA_BOOK);
        ActivityDetailViewBinding mBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_detail_view);
        mBinding.setBook(book);

        setSupportActionBar(mBinding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_book, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_edit){
            Intent it = new Intent(this, DetailEditActivity.class);
            it.putExtra(DetailEditActivity.EXTRA_BOOK, book);
            startActivity(it);
            finish();
        } else if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
