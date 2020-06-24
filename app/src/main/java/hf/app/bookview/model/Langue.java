package hf.app.bookview.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable(tableName = "Langue")

public class Langue {
    @DatabaseField(columnName = "id", generatedId = true) // AutoIncrement
    private int id;
    @DatabaseField(canBeNull = false)
    private String nLangue;

    // Collection pour table intermédiaire (LangueLivre)
    @ForeignCollectionField(eager = false)
    Collection<BookLangue> mBooklangues = new ArrayList<BookLangue>();

    public Langue() {
    }


    public Langue(int id, String nLangue) {
        this.id = id;
        this.nLangue = nLangue;
    }


    public Langue(String nLangue) {
        this.nLangue = nLangue;
    }


    public Collection<BookLangue> getBooks() {
        return mBooklangues;
    }

    public void setBooks(Collection<BookLangue> books) {
        this.mBooklangues = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getnLangue() {
        return nLangue;
    }

    public void setnLangue(String nLangue) {
        this.nLangue = nLangue;
    }

    @Override
    public String toString() {
        return nLangue;
    }
}