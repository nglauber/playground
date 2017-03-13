package br.com.nglauber.starwarsjavarx.model.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Film {
    public String title;
    @SerializedName("episode_id") public int episodeId;
    @SerializedName("characters") public List<String> personUrls;
}
