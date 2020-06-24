package hf.app.bookview.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;


// Pour mapper avec la BDD il faut ajouter les annotation
@DatabaseTable(tableName = "Client")

public class Client {
    @DatabaseField(columnName = "id", generatedId = true) // AutoIncrement
    private int id;
    @DatabaseField(canBeNull = false)
    // Pas besoin de preciser le columnName par default il prendra la meme valeur que l'attribut -- CanBeNull = false oblige une saisie
    private String login;
    @DatabaseField()
    private String password;
    @DatabaseField()
    private String email;
    @DatabaseField()
    private String photo;

    // Ici on joint la Table Client à la table  LibraryClient
    @ForeignCollectionField(eager = false)
    Collection<LibraryClient> mLibraryClient = new ArrayList<LibraryClient>();


    public Client() {
    }

    public Client(int id, String login, String password, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;

    }

    public Client(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return login + " " + password + " " + photo;

    }
}
