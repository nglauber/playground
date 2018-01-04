package br.com.nglauber.starwarskoltinrx.model.api

import android.net.Uri
import br.com.nglauber.starwarskoltinrx.model.Character
import br.com.nglauber.starwarskoltinrx.model.Movie
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class StarWarsApi {

    val service: StarWarsApiDef
    val peopleCache = mutableMapOf<String, Person>()

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://swapi.co/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()
        service = retrofit.create<StarWarsApiDef>(StarWarsApiDef::class.java)
    }

    fun loadMovies(): Observable<Movie> {
        return service.listMovies()
                .flatMap { filmResults -> Observable.fromIterable(filmResults.results) }
                .map { film ->
                    Movie(film.title, film.episodeId, ArrayList<Character>())
                }
    }


    fun loadMoviesFull(): Observable<Movie> {
        return service.listMovies()
                .flatMap { filmResults -> Observable.fromIterable(filmResults.results) }
                .flatMap { film ->
                    val movieObj = Movie(film.title, film.episodeId, ArrayList<Character>())
                    Observable.zip(
                            Observable
                                    .just(movieObj),
                            Observable
                                    .fromIterable(film.personUrls)
                                    .flatMap { personUrl ->
                                        Observable.concat(
                                                getCache(personUrl),
                                                service.loadPerson(Uri.parse(personUrl).lastPathSegment)
                                                        .doOnNext { person ->
                                                            peopleCache.put(personUrl, person!!)
                                                        })
                                                .take(1)
                                                .map { person ->
                                                    Character(person.name, person.gender)
                                                }

                                    }
                                    .toList()
                                    .toObservable()
                            ,
                            BiFunction<Movie, List<Character>, Movie> { movie, characters ->
                                movie.characters.addAll(characters)
                                movie
                            })
                }
    }

    private fun getCache(personUrl: String): Observable<Person?> {
        return Observable.fromIterable(peopleCache.keys)
                .filter { key ->
                    key == personUrl
                }
                .map { key ->
                    peopleCache[key]
                }
    }
}