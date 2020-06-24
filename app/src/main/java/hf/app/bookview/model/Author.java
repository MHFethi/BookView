package hf.app.bookview.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable(tableName = "Author")

public class Author {
    @DatabaseField(columnName = "id", generatedId = true) // AutoIncrement
    private int id;
    @DatabaseField(canBeNull = false)
    private String nom_prenom;

    // Ici on joint la class Author à Book pour la bdd (ENVOI VERS)
    @ForeignCollectionField(eager = false)
    Collection<Book> books = new ArrayList<Book>();


    public Author() {
    }


    public Author(int id, String nom_prenom) {
        this.id = id;
        this.nom_prenom = nom_prenom;
    }


    public Author(String nom_prenom) {
        this.nom_prenom = nom_prenom;
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

    public String getNom_prenom() {
        return nom_prenom;
    }

    public void setNom_prenom(String nom_prenom) {
        this.nom_prenom = nom_prenom;
    }

    @Override
    public String toString() {
        return nom_prenom;
    }
}
