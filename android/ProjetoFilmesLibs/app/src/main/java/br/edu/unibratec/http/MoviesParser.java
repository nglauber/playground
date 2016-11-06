package br.edu.unibratec.http;

import java.io.IOException;

import br.edu.unibratec.model.MovieSearchResult;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

public class MoviesParser {

    public static Observable<MovieSearchResult> searchByTitle(String q) throws IOException {
        RxJavaCallAdapterFactory rxAdapter =
                RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();

        MoviesRetrofit api = retrofit.create(MoviesRetrofit.class);

        return api.search(q, "json");
    }
}
