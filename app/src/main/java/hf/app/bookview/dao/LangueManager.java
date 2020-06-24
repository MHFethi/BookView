package hf.app.bookview.dao;

import android.content.Context;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;
import hf.app.bookview.database.BddManager;
import hf.app.bookview.model.Book;
import hf.app.bookview.model.Category;
import hf.app.bookview.model.Langue;

public class LangueManager implements IDao<Langue> {
    private static final String TAG = "LangueManager";
    BddManager database;
    // On créer le dao generique de BdManager pour recuperer les methode crud general
    Dao<Langue, Integer> lDao;

    public LangueManager(Context context) {
        database = new BddManager(context);
        try {
            lDao = database.getDao(Langue.class);
        } catch (SQLException e) {
            Log.i(TAG, "LangueManager: " + e.getMessage());
        }
    }


    @Override
    public List<Langue> getAll() {
        // queryForAll permet de recuperer une liste de donnée
        try {
            return lDao.queryForAll();
        } catch (SQLException e) {
            Log.i(TAG, "LangueManager: " + e.getMessage());
        }
        return null;
    }


    @Override
    public Langue find(int id) {
        try {
            return lDao.queryForId(id);
        } catch (SQLException e) {
            Log.i(TAG, "LangueManager: " + e.getMessage());
        }
        return null;
    }


    @Override
    public boolean add(Langue item) {
        // create permet d'aajouter une donnée
            try {
                lDao.create(item);
                return true;
            } catch (SQLException e) {
                Log.i(TAG, "LangueManager: " + e.getMessage());
            }
        return false;
    }


    @Override
    public boolean update(Langue item) {
        // update permet de modifier une donnée
        try {
            return lDao.update(item) > 0;
        } catch (SQLException e) {
            Log.i(TAG, "LangueManager: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean delete(Langue item) {
        // delete permet de modifier une donnée
        try {
            return lDao.delete(item) > 0;
        } catch (SQLException e) {
            Log.i(TAG, "LangueManager: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean delete(int id) {
        try {
            Langue l = find(id);
            if (l != null) {
                return lDao.delete(l) > 0;
            }
        } catch (SQLException e) {
            Log.i(TAG, "LangueManager: " + e.getMessage());
        }
        return false;
    }

    public Langue find(String nom) {
        QueryBuilder<Langue, Integer> qb = lDao.queryBuilder();
        Langue langue = null;
        try {
            qb.where().eq("nLangue", nom);
            langue = qb.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return langue;
    }


    public int findId(String nLangue) {
        QueryBuilder<Langue, Integer> qb = lDao.queryBuilder();
        Langue langue = null;
        try {
            qb.where().eq("nLangue", nLangue);
            langue = qb.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return langue.getId();
    }

}
