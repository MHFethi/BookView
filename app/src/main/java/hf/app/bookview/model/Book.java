package hf.app.bookview.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable(tableName = "Book")

public class Book {
    @DatabaseField(columnName = "id", generatedId = true) // AutoIncrement
    private int id;
    @DatabaseField(canBeNull = false)
    private String mTitle;
    @DatabaseField()
    private String mPublishedDate;
    @DatabaseField()
    private String mDescription;
    @DatabaseField()
    private String mThumbnail;
    @DatabaseField()
    private int pageCount;
    @DatabaseField()
    private String isbn;

    // Ici on joint la class Book à BookLangue pour la bdd (ENVOI VERS)
    @ForeignCollectionField(eager = false)
    Collection<BookLangue> mBooklangues = new ArrayList<BookLangue>();

    // Foreign key generer directement dans la table Book (RECOIS DE)
    @DatabaseField(foreign = true, canBeNull = false, foreignAutoCreate = true,
            foreignAutoRefresh = true)
    private Category mCategories;

    // Foreign key generer directement dans la table Book (RECOIS DE)
    @DatabaseField(foreign = true, canBeNull = false, foreignAutoCreate = true,
            foreignAutoRefresh = true)
    private Author mAuthors;


    public Book() {
    }

    public Book(String mTitle, Author mAuthors, String mDescription, String mPublishedDate, Category mCategories, String mThumbnail,
                int pageCount, String isbn) {
        this.mTitle = mTitle;
        this.mAuthors = mAuthors;
        this.mPublishedDate = mPublishedDate;
        this.mDescription = mDescription;
        this.mCategories = mCategories;
        this.mThumbnail = mThumbnail;
        this.pageCount = pageCount;
        this.isbn = isbn;
    }


    public Book(int id, String mTitle, Author mAuthors, String mDescription, String mPublishedDate, Category mCategories, String mThumbnail,
                int pageCount, String isbn) {
        this.id = id;
        this.mTitle = mTitle;
        this.mAuthors = mAuthors;
        this.mPublishedDate = mPublishedDate;
        this.mDescription = mDescription;
        this.mCategories = mCategories;
        this.mThumbnail = mThumbnail;
        this.pageCount = pageCount;
        this.isbn = isbn;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Author getmAuthors() {
        return mAuthors;
    }

    public void setmAuthors(Author mAuthors) {
        this.mAuthors = mAuthors;
    }

    public Category getmCategories() {
        return mCategories;
    }

    public void setmCategories(Category mCategories) {
        this.mCategories = mCategories;
    }

    public Collection<BookLangue> getMlangues() {
        return mBooklangues;
    }

    public void setMlangues(Collection<BookLangue> mlangues) {
        this.mBooklangues = mlangues;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getThumbnail() {
        return mThumbnail;
    }



    @Override
    public String toString() {
        return mTitle + " - " + mAuthors + " - " + mDescription + " - " +mPublishedDate  + " - " +mCategories  + " - " + mThumbnail  + " - " +pageCount + " - " +isbn ;
    }
}

