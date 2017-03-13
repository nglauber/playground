package br.com.nglauber.starwarsjavarx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.nglauber.starwarsjavarx.model.Movie;
import br.com.nglauber.starwarsjavarx.model.api.StarWarsApiLambda;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> movieAdapter;
    ArrayList<String> movies = new ArrayList<>();

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        setContentView(listView);
        movieAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movies);
        listView.setAdapter(movieAdapter);

        StarWarsApiLambda api = new StarWarsApiLambda();
        api.loadMoviesFull()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Movie>() {
                    @Override
                    public void call(Movie movie) {
                        movies.add(movie.title + " - " + movie.episodeId + "\n " + movie.characters.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        movieAdapter.notifyDataSetChanged();
                    }
                });
    }
}
