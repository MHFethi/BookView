package hf.app.bookview.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import hf.app.bookview.database.BddManager;
import hf.app.bookview.model.Author;
import hf.app.bookview.model.Book;
import hf.app.bookview.model.BookLangue;
import hf.app.bookview.model.Category;
import hf.app.bookview.model.Client;

public class BookLangueManager implements IDao<BookLangue> {
    private static final String TAG = "BookLangueManager";
    BddManager database;
    Dao<BookLangue, Integer> blDao;

    public BookLangueManager(Context context) {
        database = new BddManager(context);
        try {
            blDao = database.getDao(BookLangue.class);
        } catch (SQLException e) {
            Log.i(TAG, "BookLangueManager: " + e.getMessage());
        }
    }


    @Override
    public List<BookLangue> getAll() {
        // queryForAll permet de recuperer une liste de donnée
        try {
            return blDao.queryForAll();
        } catch (SQLException e) {
            Log.i(TAG, "BookLangueManager: " + e.getMessage());
        }
        return null;
    }


    @Override
    public BookLangue find(int id) {
        try {
            return blDao.queryForId(id);
        } catch (SQLException e) {
            Log.i(TAG, "BookLangueManager: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean add(BookLangue item) {
        try {
            blDao.create(item);
            return true;
        } catch (SQLException e) {
            Log.i(TAG, "BookLangueManager: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean update(BookLangue item) {
        // update permet de modifier une donnée
        try {
            return blDao.update(item) > 0;
        } catch (SQLException e) {
            Log.i(TAG, "BookLangueManager: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(BookLangue item) {
        // delete permet de modifier une donnée
        try {
            return blDao.delete(item) > 0;
        } catch (SQLException e) {
            Log.i(TAG, "BookLangueManager: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            BookLangue bl = find(id);
            if (bl != null) {
                return blDao.delete(bl) > 0;
            }
        } catch (SQLException e) {
            Log.i(TAG, "BookLangueManager: " + e.getMessage());
        }
        return false;
    }


    public BookLangue findbyIdBookIdLangue(int idBook, int idLangue) {
        QueryBuilder<BookLangue, Integer> qb = blDao.queryBuilder();
        BookLangue bookLangue = null;
        try {
            //SELECT * FROM booklangue WHERE book_id=1 AND langue_id=1
            qb.where().eq("book_id", idBook).and().eq("langue_id", idLangue);
            bookLangue = qb.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookLangue;
    }



    public int findId(int idBook, int idLangue) {
        QueryBuilder<BookLangue, Integer> qb = blDao.queryBuilder();
        BookLangue bookLangue = null;
        try {
            qb.where().eq("book_id", idBook).and().eq("langue_id", idLangue);
            bookLangue = qb.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookLangue.getId();
    }





}
