package hf.app.bookview.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import hf.app.bookview.database.BddManager;
import hf.app.bookview.model.Author;


public class AuthorManager implements IDao<Author> {

    private static final String TAG = "AuthorManager";
    BddManager database;
    Dao<Author, Integer> aDao;

    public AuthorManager(Context context) {
        database = new BddManager(context);
        try {
            aDao = database.getDao(Author.class);
        } catch (SQLException e) {
            Log.i(TAG, "AuthorManager: " + e.getMessage());
        }
    }


    @Override
    public List<Author> getAll() {
        // queryForAll permet de recuperer une liste de donnée
        try {
            return aDao.queryForAll();
        } catch (SQLException e) {
            Log.i(TAG, "AuthorManager: " + e.getMessage());
        }
        return null;
    }


    @Override
    public Author find(int id) {
        // queryForId permet recuperer une donnée par ID
        try {
            return aDao.queryForId(id);
        } catch (SQLException e) {
            Log.i(TAG, "AuthorManager: " + e.getMessage());
        }
        return null;
    }


    @Override
    public boolean add(Author item) {
        if (this.find(item.getNom_prenom()) == null) {
            try {
                aDao.create(item);
                return true;
            } catch (SQLException e) {
                Log.i(TAG, "AuthorManager: " + e.getMessage());
            }
        }
            return false;
    }


    @Override
    public boolean update(Author item) {
        // update permet de modifier une donnée
        try {
            return aDao.update(item) > 0;
        } catch (SQLException e) {
            Log.i(TAG, "AuthorManager: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean delete(Author item) {
        // delete permet de modifier une donnée
        try {
            return aDao.delete(item) > 0;
        } catch (SQLException e) {
            Log.i(TAG, "AuthorManager: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean delete(int id) {
        try {
            Author a = find(id);
            if (a != null) {
                return aDao.delete(a) > 0;
            }
        } catch (SQLException e) {
            Log.i(TAG, "AuthorManager: " + e.getMessage());
        }
        return false;
    }

    public Author find(String nom) {
        QueryBuilder<Author, Integer> qb = aDao.queryBuilder();
        Author author = null;

        try {
            qb.where().eq("nom_prenom", nom);
            author = qb.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return author;
    }


}
