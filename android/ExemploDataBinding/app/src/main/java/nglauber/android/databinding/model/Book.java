package nglauber.android.databinding.model;

import org.parceler.Parcel;

/**
 * Created by nglauber on 4/6/16.
 */
@Parcel
public class Book
{
    private String title;
    private String subtitle;
    private String publisher;
    private String description;
    private String[] authors;
    private String publishedDate;
    private int pageCount;
    private Thumbnail imageLinks;

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishDate) {
        this.publishedDate = publishDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public Thumbnail getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(Thumbnail imageLinks) {
        this.imageLinks = imageLinks;
    }
}
