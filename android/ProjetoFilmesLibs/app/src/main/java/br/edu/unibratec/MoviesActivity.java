package br.edu.unibratec;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import br.edu.unibratec.databinding.ActivityMoviesBinding;
import br.edu.unibratec.http.MoviesParser;
import br.edu.unibratec.model.MovieSearchResult;
import br.edu.unibratec.ui.adapter.MoviesAdapter;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MoviesActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener {

    ActivityMoviesBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movies);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            Observable<MovieSearchResult> result = MoviesParser.searchByTitle(query);
            Subscription subscription = result
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            movieSearchResult -> EventBus.getDefault().postSticky(movieSearchResult),
                            Throwable::printStackTrace,
                            () -> Log.d("NGVL", "Complete!")
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MovieSearchResult event) {
        updateListView(event);
    }

    private void updateListView(MovieSearchResult movieSearchResult) {
        mBinding.listMovies.setAdapter(new MoviesAdapter(this, movieSearchResult.movies));
    }
}
