package nglauber.android.databinding.http;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import nglauber.android.databinding.BookListFragment;
import nglauber.android.databinding.model.Book;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BookTask extends AsyncTask<String, Void, List<Book>> {

    public static final String API_KEY = "AIzaSyDXADjahAioclU-RoLfVG21fKz77J7gYtc";
    public static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=%s&key="+ API_KEY;

    private WeakReference<BookListFragment> fragment;

    public BookTask(BookListFragment fragment) {
        this.fragment = new WeakReference<>(fragment);
    }

    public void setFragment(BookListFragment fragment) {
        this.fragment = new WeakReference<>(fragment);
    }

    @Override
        protected List<Book> doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();

            final String args = params[0];
            final String url = String.format(BASE_URL, args);

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String jsonString = response.body().string();

                List<Book> books = BookHttp.getEditoraFromJson(jsonString);

                return books;

            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            super.onPostExecute(books);
            BookListFragment llf = fragment.get();
            if (llf == null) return;

            if (books != null){
                llf.setLivros(books);
            } else {
                llf.showError();
            }
        }
    }