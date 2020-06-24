package hf.app.bookview.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable(tableName = "Category")

public class Category {
    @DatabaseField(columnName = "id", generatedId = true) // AutoIncrement
    private int id;
    @DatabaseField(canBeNull = false)
    private String nCategory;

    // Ici on joint la class Author à Book pour la bdd
    @ForeignCollectionField(eager = false)
    Collection<Book> books = new ArrayList<Book>();

    public Category() {
    }


    public Category(int id, String nCategory, Collection<Book> books) {
        this.id = id;
        this.nCategory = nCategory;
        this.books = books;

    }
    public Category(String nCategory) {
        this.nCategory = nCategory;
    }


    public Category(String nCategory,Collection<Category> mCategories) {
        this.nCategory = nCategory;
        this.books = books;
    }

    public Collection<Book> getBooks() {
        return books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getnCategory() {
        return nCategory;
    }

    public void setnCategory(String nCategory) {
        this.nCategory = nCategory;
    }

    @Override
    public String toString() {
        return nCategory;
    }
}