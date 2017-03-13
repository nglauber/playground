package br.com.nglauber.starwarsjavarx.model.api;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.nglauber.starwarsjavarx.model.Character;
import br.com.nglauber.starwarsjavarx.model.Movie;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class StarWarsApi {

    StarWarsApiDef service;
    HashMap<String, Person> peopleCache = new HashMap<>();

    public StarWarsApi() {
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
                .flatMap(new Func1<FilmResult, Observable<Film>>() {
                    @Override
                    public Observable<Film> call(FilmResult filmResult) {
                        return Observable.from(filmResult.results);
                    }
                })
                .map(new Func1<Film, Movie>() {
                    @Override
                    public Movie call(Film film) {
                        return new Movie(film.title, film.episodeId, new ArrayList<Character>());
                    }
                });
    }


    public Observable<Movie> loadMoviesFull() {
        return service.listMovies()
                .flatMap(new Func1<FilmResult, Observable<Film>>() {
                    @Override
                    public Observable<Film> call(FilmResult filmResult) {
                        return Observable.from(filmResult.results);
                    }
                })
                .flatMap(new Func1<Film, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> call(Film film) {
                        final Movie movieObj = new Movie(film.title, film.episodeId, new ArrayList<Character>());
                        return Observable.zip(
                                Observable.just(movieObj),
                                Observable.from(film.personUrls)
                                        .flatMap(new Func1<String, Observable<Person>>() {
                                            @Override
                                            public Observable<Person> call(final String personUrl) {
                                                return Observable.concat(
                                                        getCache(personUrl),
                                                        service.loadPerson(Uri.parse(personUrl).getLastPathSegment()).doOnNext(new Action1<Person>() {
                                                            @Override
                                                            public void call(Person person) {
                                                                peopleCache.put(personUrl, person);
                                                            }
                                                        })
                                                ).first();
                                            }
                                        })
                                        .map(new Func1<Person, Character>() {
                                            @Override
                                            public Character call(Person person) {
                                                return new Character(person.name, person.gender);
                                            }
                                        })
                                        .toList(), new Func2<Movie, List<Character>, Movie>() {
                                    @Override
                                    public Movie call(Movie movie, List<Character> characters) {
                                        movie.characters.addAll(characters);
                                        return movie;
                                    }
                                }
                        );
                    }
                });
    }

    private Observable<Person> getCache(final String personUrl) {
        return Observable.from(peopleCache.keySet())
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return personUrl.equals(s);
                    }
                })
                .map(new Func1<String, Person>() {
                    @Override
                    public Person call(String s) {
                        return peopleCache.get(s);
                    }
                });
    }
}