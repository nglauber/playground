package br.edu.unibratec.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieSearchResult {
    @SerializedName("Search")
    public List<Movie> movies;
}
