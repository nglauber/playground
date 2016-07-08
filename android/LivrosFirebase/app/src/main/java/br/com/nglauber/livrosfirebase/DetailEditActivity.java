package br.com.nglauber.livrosfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;

import br.com.nglauber.livrosfirebase.databinding.ActivityDetailEditBinding;
import br.com.nglauber.livrosfirebase.model.Book;
import br.com.nglauber.livrosfirebase.model.MediaType;
import br.com.nglauber.livrosfirebase.model.Publisher;
import br.com.nglauber.livrosfirebase.utils.Constants;
import br.com.nglauber.livrosfirebase.utils.Permission;

public class DetailEditActivity extends BaseActivity {

    public static final String EXTRA_BOOK = "livro";
    private static final int RC_CAMERA = 0;

    private DatabaseReference mBooksNodeRef;
    private DatabaseReference mPublishersNodeRef;
    private StorageReference mStorageRef;

    private ActivityDetailEditBinding mBinding;
    private boolean mIsNewBook;
    private File mTempImagePath;
    private ProgressDialog mProgress;

    @Override
    protected void init() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_edit);

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Book book = (Book)getIntent().getSerializableExtra(EXTRA_BOOK);
        mIsNewBook = (book == null);
        if (mIsNewBook) {
            mBinding.setBook(new Book());
        } else {
            mBinding.setBook(book);
        }

        mBinding.setPresenter(this);
        mBinding.content.setPublishers(new ObservableArrayList<Publisher>());

        initFirebase();
        loadPublishers();
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(
                requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 0) {
            mBinding.getBook().setCoverUrl("file://"+ mTempImagePath.getAbsolutePath());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickSaveBook(View view) {
        mProgress = ProgressDialog.show(this, null, getString(R.string.text_message_saving_book));
        if (mTempImagePath != null && mTempImagePath.exists()) {
            uploadCoverAndSaveBook();

        } else {
            saveBook();
        }
    }

    private void uploadCoverAndSaveBook() {
        StorageReference capaRef = mStorageRef.child(mTempImagePath.getName());

        UploadTask uploadTask = capaRef.putFile(Uri.fromFile(mTempImagePath));
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                mProgress.hide();
                Toast.makeText(DetailEditActivity.this, R.string.text_message_saving_book_error, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (mTempImagePath != null && mTempImagePath.exists()) mTempImagePath.delete();
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                mBinding.getBook().setCoverUrl(downloadUrl.toString());
                saveBook();
            }
        });
    }

    private void saveBook(){
        mProgress.dismiss();

        Book book = mBinding.getBook();
        if (mIsNewBook) {
            mBooksNodeRef.push().setValue(book);
        } else {
            mBooksNodeRef.child(book.getId()).setValue(book);
        }
        finish();
    }

    public void clickTakePhoto(View view) {
        if (Permission.hasStoragePermission(this)) {

            String fileName = DateFormat.format(Constants.TEMP_FILE_FORMAT, new Date()).toString();

            mTempImagePath = new File(
                    Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES),
                    fileName +".jpg");

            Intent it = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
            it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTempImagePath));
            startActivityForResult(it, RC_CAMERA);

        } else {
            Permission.requestStoragePermission(this, 0);

        }
    }

    public void onMediaTypeChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView == mBinding.content.radioMediaEbook) {
                mBinding.getBook().setMediaTypeValue(MediaType.EBOOK);
            } else if (buttonView == mBinding.content.radioMediaPaper) {
                mBinding.getBook().setMediaTypeValue(MediaType.PAPER);
            }
        }
    }

    private void initFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mBooksNodeRef = database.getReference(Constants.BOOK_REFERENCE).child(getAuth().getCurrentUser().getUid());
        mPublishersNodeRef = database.getReference(Constants.PUBLISHERS_REFERENCE);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReferenceFromUrl(Constants.STORAGE_REFERENCE_URL ).child("images");
    }

    private void loadPublishers() {
        mPublishersNodeRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Publisher publisher = dataSnapshot.getValue(Publisher.class);
                publisher.setId(dataSnapshot.getKey());
                mBinding.content.getPublishers().add(publisher);

                mBinding.content.spinnerPublisher.setSelection(
                        mBinding.content.getPublishers().indexOf(
                                mBinding.getBook().getPublisher()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Publisher publisher = dataSnapshot.getValue(Publisher.class);
                publisher.setId(dataSnapshot.getKey());
                for (Publisher p : mBinding.content.getPublishers()) {
                    if (p.getId().equals(publisher.getId())) {
                        p.setName(publisher.getName());
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Publisher publisher = dataSnapshot.getValue(Publisher.class);
                publisher.setId(dataSnapshot.getKey());
                for (int i = 0; i < mBinding.content.getPublishers().size(); i++) {
                    if (mBinding.content.getPublishers().get(i).getId().equals(publisher.getId())) {
                        mBinding.content.getPublishers().remove(i);
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                // Ignore...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Ignore
            }
        });
    }
}
