package br.com.nglauber.livrosfirebase;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import br.com.nglauber.livrosfirebase.adapter.BookViewHolder;
import br.com.nglauber.livrosfirebase.adapter.BoookAdapter;
import br.com.nglauber.livrosfirebase.databinding.ActivityMainBinding;
import br.com.nglauber.livrosfirebase.model.Book;
import br.com.nglauber.livrosfirebase.utils.Constants;
import br.com.nglauber.livrosfirebase.utils.SimpleItemTouch;

public class MainActivity extends BaseActivity {

    private DatabaseReference mBooksNodeRef;
    private StorageReference mStorage;

    private FirebaseRecyclerAdapter<Book, BookViewHolder> mAdapter;

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_signout){
            FirebaseAuth.getInstance().signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void init() {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        mBooksNodeRef = mDatabase
                .getReference(Constants.BOOK_REFERENCE)
                .child(getAuth().getCurrentUser().getUid());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        mStorage = storage.getReferenceFromUrl(Constants.STORAGE_REFERENCE_URL);

        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new BoookAdapter(mBooksNodeRef, new BoookAdapter.BookClickListener() {
            @Override
            public void onBookClicked(Book book) {
                Intent it = new Intent(MainActivity.this, DetailViewActivity.class);
                it.putExtra(DetailViewActivity.EXTRA_BOOK, book);
                startActivity(it);
            }
        });
        mBinding.recyclerView.setAdapter(mAdapter);
        attachSwipeToRecyclerView();
    }

    public void clickNewBook(View view) {
        startActivity(new Intent(this, DetailEditActivity.class));
    }

    private void attachSwipeToRecyclerView() {
        ItemTouchHelper.SimpleCallback swipe =
                new SimpleItemTouch(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public void onSwiped(
                            RecyclerView.ViewHolder viewHolder, int swipeDir) {

                        final int position = viewHolder.getAdapterPosition();
                        deleteBookFromPosition(position);
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipe);
        itemTouchHelper.attachToRecyclerView(mBinding.recyclerView);
    }

    private void deleteBookFromPosition(int position) {
        final String bookId = mAdapter.getRef(position).getKey();
        Book book = mAdapter.getItem(position);

        if (book.getCoverUrl() != null){
            Uri uri = Uri.parse(book.getCoverUrl());
            String fileName = uri.getLastPathSegment();

            StorageReference imageCapa = mStorage.child(fileName);
            imageCapa.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    mBooksNodeRef.child(bookId).removeValue();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Uh-oh, an error occurred!
                }
            });
        } else {
            mBooksNodeRef.child(bookId).removeValue();
        }
    }
}
