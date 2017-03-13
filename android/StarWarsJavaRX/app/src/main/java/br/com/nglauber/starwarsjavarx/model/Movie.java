package br.com.nglauber.starwarsjavarx.model;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    public String title;
    public int episodeId;
    public List<Character> characters;

    public Movie(String title, int episodeId, ArrayList<Character> characters) {
        this.title = title;
        this.episodeId = episodeId;
        this.characters = characters;
    }


}
