package hf.app.bookview.dao;

import android.content.Context;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;
import hf.app.bookview.database.BddManager;
import hf.app.bookview.model.Author;
import hf.app.bookview.model.Category;

public class CategoryManager implements IDao<Category> {
    private static final String TAG = "CategoryManager";
    BddManager database;
    Dao<Category, Integer> catDao;

    public CategoryManager(Context context) {
        database = new BddManager(context);
        try {
            catDao = database.getDao(Category.class);
        } catch (SQLException e) {
            Log.i(TAG, "CategoryManager: " + e.getMessage());
        }
    }


    @Override
    public List<Category> getAll() {
        // queryForAll permet de recuperer une liste de donnée
        try {
            return catDao.queryForAll();
        } catch (SQLException e) {
            Log.i(TAG, "CategoryManager: " + e.getMessage());
        }
        return null;
    }


    @Override
    public Category find(int id) {
        try {
            return catDao.queryForId(id);
        } catch (SQLException e) {
            Log.i(TAG, "CategoryManager: " + e.getMessage());
        }
        return null;
    }


    @Override
    public boolean add(Category item) {
        // create permet d'aajouter une donnée
        if (this.find(item.getnCategory()) == null) {
            try {
                catDao.create(item);
                return true;
            } catch (SQLException e) {
                Log.i(TAG, "CategoryManager: " + e.getMessage());
            }
        }
        return false;
    }


    @Override
    public boolean update(Category item) {
        // update permet de modifier une donnée
        try {
            return catDao.update(item) > 0;
        } catch (SQLException e) {
            Log.i(TAG, "CategoryManager: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean delete(Category item) {
        // delete permet de modifier une donnée
        try {
            return catDao.delete(item) > 0;
        } catch (SQLException e) {
            Log.i(TAG, "CategoryManager: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean delete(int id) {
        try {
            Category c = find(id);
            if (c != null) {
                return catDao.delete(c) > 0;
            }
        } catch (SQLException e) {
            Log.i(TAG, "CategoryManager: " + e.getMessage());
        }
        return false;
    }

    public Category find(String nom) {
        QueryBuilder<Category, Integer> qb = catDao.queryBuilder();
        Category category = null;

        try {
            qb.where().eq("nCategory", nom);
            category = qb.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }




}
