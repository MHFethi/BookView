package hf.app.bookview.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import hf.app.bookview.database.BddManager;
import hf.app.bookview.model.Book;
import hf.app.bookview.model.Langue;

public class BookManager implements IDao<Book> {
    private static final String TAG = "BookManager";

    // Il nous faut une reference a la bdd sur laquel il peut travailler
    BddManager database;
    // On créer le dao generique de BdManager pour recuperer les methode crud general
    Dao<Book,Integer> bDao;


    public BookManager(Context context){
        database = new BddManager(context);
        try {
            bDao = database.getDao(Book.class);
        } catch (SQLException e) {
            Log.i(TAG, "BookManager: " + e.getMessage());
        }
    }



    @Override
    public List<Book> getAll() {
        // queryForAll permet de recuperer une liste de donnée
        try {
            return bDao.queryForAll();
        } catch (SQLException e) {
            Log.i(TAG, "BookManager: " + e.getMessage());
        }
        return null;
    }


    @Override
    public Book find(int id) {
        try {
            return bDao.queryForId(id);
        } catch (SQLException e) {
            Log.i(TAG, "BookManager: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean add(Book item) {
        // create permet d'aajouter une donnée
        if (this.find(item.getIsbn()) == null) {
            try {
                bDao.create(item);
                return true;
            } catch (SQLException e) {
                Log.i(TAG, "BookManager: " + e.getMessage());
            }
        }
        return false;
    }


    @Override
    public boolean update(Book item) {
        // update permet de modifier une donnée
        try {
            return bDao.update(item) > 0;
        } catch (SQLException e) {
            Log.i(TAG, "BookManager: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Book item) {
        // delete permet de modifier une donnée
        try {
            return bDao.delete(item) > 0;
        } catch (SQLException e) {
            Log.i(TAG, "BookManager: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            Book b = find(id);
            if (b != null) {
                return bDao.delete(b) > 0;
            }
        } catch (SQLException e) {
            Log.i(TAG, "BookManager: " + e.getMessage());
        }
        return false;
    }



    public Book find(String isbn) {
        QueryBuilder<Book, Integer> qb = bDao.queryBuilder();
        Book book = null;
        try {
            qb.where().eq("isbn", isbn);
            book = qb.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }


    public int findId(String isbn) {
        QueryBuilder<Book, Integer> qb = bDao.queryBuilder();
        Book book = null;
        try {
            qb.where().eq("isbn", isbn);
            book = qb.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book.getId();
    }




}
