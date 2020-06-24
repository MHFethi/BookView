package hf.app.bookview.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.ArrayList;
import java.util.Collection;

public class BookLangue {
    @DatabaseField(columnName = "id", generatedId = true) // AutoIncrement
    private int id;

    // Foreign key generer directement dans la table Book
    @DatabaseField(foreign = true, canBeNull = false, foreignAutoCreate = true,
            foreignAutoRefresh = true)
    private Book book;


    // Foreign key generer directement dans la table Book
    @DatabaseField(foreign = true, canBeNull = false, foreignAutoCreate = true,
            foreignAutoRefresh = true)
    private Langue langue;

    @ForeignCollectionField(eager = false)
    Collection<LibraryClient> mLibraryClient = new ArrayList<LibraryClient>();

    public BookLangue() {
    }


    public BookLangue(int id, Book book, Langue langue) {
        this.id = id;
        this.book = book;
        this.langue = langue;
    }

    public BookLangue(Book book, Langue langue) {
        this.id = id;
        this.book = book;
        this.langue = langue;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Langue getLangue() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    @Override
    public String toString() {
        return "BookLangue{" +
                "id=" + id +
                ", book=" + book +
                ", mLibraryClient=" + mLibraryClient +
                ", langue=" + langue +
                '}';
    }
}
