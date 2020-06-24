package hf.app.bookview.model;

import com.j256.ormlite.field.DatabaseField;

public class LibraryClient {
    @DatabaseField(columnName = "id", generatedId = true) // AutoIncrement
    private int id;

    // Foreign key generer directement dans la table Book
    @DatabaseField(foreign = true, canBeNull = false, foreignAutoCreate = true,
            foreignAutoRefresh = true)
    private Client client;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoCreate = true,
            foreignAutoRefresh = true)
    private BookLangue livre;

    @DatabaseField()
    boolean statue;

    public LibraryClient() {

    }

    public LibraryClient(Client client, BookLangue livre, boolean statue) {
        this.client = client;
        this.livre = livre;
        this.statue = statue;
    }

    public LibraryClient(int id, Client client, BookLangue livre, boolean statue) {
        this.id = id;
        this.client = client;
        this.livre = livre;
        this.statue = statue;
    }






    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BookLangue getLivre() {
        return livre;
    }

    public void setLivre(BookLangue livre) {
        this.livre = livre;
    }

    public boolean isStatue() {
        return statue;
    }

    public void setStatue(boolean statue) {
        this.statue = statue;
    }

    @Override
    public String toString() {
        return "LibraryClient{" +
                "id=" + id +
                ", client=" + client +
                ", livre=" + livre +
                ", statue=" + statue +
                '}';
    }
}
