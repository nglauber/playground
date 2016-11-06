package br.edu.unibratec.http;

import br.edu.unibratec.model.MovieSearchResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface MoviesRetrofit {
    @GET("./")
    Observable<MovieSearchResult> search(@Query("s") String q, @Query("r") String format);
}
