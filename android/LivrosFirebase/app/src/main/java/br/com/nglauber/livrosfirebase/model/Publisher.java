package br.com.nglauber.livrosfirebase.model;

import java.io.Serializable;

public class Publisher implements Serializable {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Publisher)){
            return false;
        } else {
            Publisher publisher  = (Publisher)o;
            return publisher.getId().equals(getId()) && publisher.getName().equals(getName());
        }
    }
}
