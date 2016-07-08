package br.com.nglauber.livrosfirebase.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

import br.com.nglauber.livrosfirebase.BR;

public class Book extends BaseObservable implements Serializable {

    private String id;
    private String title;
    private String author;
    private String coverUrl;
    private int pages;
    private int year;
    private Publisher publisher;
    private boolean available;
    private MediaType mediaType;
    private float rating;

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        notifyPropertyChanged(BR.author);
    }

    @Bindable
    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
        notifyPropertyChanged(BR.coverUrl);
    }

    @Bindable
    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
        notifyPropertyChanged(BR.pages);
    }

    @Bindable
    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
        notifyPropertyChanged(BR.publisher);
    }

    @Bindable
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
        notifyPropertyChanged(BR.available);
    }

    @Exclude
    @Bindable
    public MediaType getMediaTypeValue() {
        return mediaType;
    }

    public void setMediaTypeValue(MediaType mediaType) {
        this.mediaType = mediaType;
        notifyPropertyChanged(BR.mediaTypeValue);
    }

    public String getMediaType() {
        return mediaType != null ? mediaType.toString() : null;
    }

    public void setMediaType(String mediaType) {
        if (mediaType != null){
            setMediaTypeValue(MediaType.valueOf(mediaType));
        }
    }

    @Bindable
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        notifyPropertyChanged(BR.year);
    }

    @Bindable
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
        notifyPropertyChanged(BR.rating);
    }
}
