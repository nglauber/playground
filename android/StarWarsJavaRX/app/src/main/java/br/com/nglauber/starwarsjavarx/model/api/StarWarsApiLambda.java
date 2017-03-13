package br.com.nglauber.starwarsjavarx.model.api;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.nglauber.starwarsjavarx.model.Character;
import br.com.nglauber.starwarsjavarx.model.Movie;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class StarWarsApiLambda {

    StarWarsApiDef service;
    HashMap<String, Person> peopleCache = new HashMap<>();

    public StarWarsApiLambda() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://swapi.co/api/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        service = retrofit.create(StarWarsApiDef.class);
    }

    public Observable<Movie> loadMovies() {
        return service.listMovies()
                .flatMap( filmResult -> Observable.from(filmResult.results) )
                .map(film -> new Movie(film.title, film.episodeId, new ArrayList<>()));
    }


    public Observable<Movie> loadMoviesFull() {
        return service.listMovies()
                .flatMap(filmResult -> Observable.from(filmResult.results) )
                .flatMap((film) -> {
                    final Movie movieObj = new Movie(film.title, film.episodeId, new ArrayList<>());
                    return Observable.zip(
                            Observable.just(movieObj),
                            Observable.from(film.personUrls)
                                    .flatMap((personUrl) -> Observable.concat(
                                            getCache(personUrl),
                                            service.loadPerson(Uri.parse(personUrl).getLastPathSegment())
                                                    .doOnNext(person -> peopleCache.put(personUrl, person))
                                    ).first())
                                    .map(person -> new Character(person.name, person.gender))
                                    .toList(), (movie, characters) -> {
                                        movie.characters.addAll(characters);
                                        return movie;
                                    }
                    );
                });
    }

    private Observable<Person> getCache(final String personUrl) {
        return Observable.from(peopleCache.keySet())
                .filter(s -> personUrl.equals(s))
                .map(s -> peopleCache.get(s));
    }
}