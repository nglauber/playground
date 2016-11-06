package br.edu.unibratec.model;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("Title")
    public String title;
    @SerializedName("Year")
    public String year;
    @SerializedName("imdbID")
    public String imdbId;
    @SerializedName("Poster")
    public String poster;
}
