package br.com.nglauber.starwarsjavarx.model;

public class Character {

    public String name;
    public String gender;

    public Character(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return name +"-"+ gender;
    }
}
