package br.com.nglauber.starwarsjavarx.model.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface StarWarsApiDef {
    @GET("films")
    Observable<FilmResult> listMovies();

    @GET("people/{personId}")
    Observable<Person> loadPerson(@Path("personId")String personId);
}
