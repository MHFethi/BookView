package hf.app.bookview.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import hf.app.bookview.database.BddManager;
import hf.app.bookview.model.BookLangue;
import hf.app.bookview.model.LibraryClient;

public class LibraryClientManager implements IDao<LibraryClient> {
    private static final String TAG = "LibraryClientManager";
    BddManager database;
    Dao<LibraryClient,Integer> lbDao;
    Context context;

    public LibraryClientManager(Context context){
        database = new BddManager(context);
        try {
            lbDao = database.getDao(LibraryClient.class);
        } catch (SQLException e) {
            Log.i(TAG, "LibraryClientManager: " + e.getMessage());
        }
    }


    @Override
    public List<LibraryClient> getAll() {
        // queryForAll permet de recuperer une liste de donnée
        try {
            return lbDao.queryForAll();
        } catch (SQLException e) {
            Log.i(TAG, "LibraryClientManager: " + e.getMessage());
        }
        return null;
    }

    @Override
    public LibraryClient find(int id) {
        try {
            return lbDao.queryForId(id);
        } catch (SQLException e) {
            Log.i(TAG, "LibraryClientManager: " + e.getMessage());
        }
        return null;
    }


    @Override
    public boolean add(LibraryClient item) {

            try {
                lbDao.create(item);
                return true;
            } catch (SQLException e) {
                Log.i(TAG, "LibraryClientManager: " + e.getMessage());
            }

        return false;
    }

    @Override
    public boolean update(LibraryClient item) {
        try {
            return lbDao.update(item)>0;
        } catch (SQLException e) {
            Log.i(TAG, "LibraryClientManager: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(LibraryClient item) {
        // delete permet de modifier une donnée
        try {
            return lbDao.delete(item)>0;
        } catch (SQLException e) {
            Log.i(TAG, "LibraryClientManager: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            LibraryClient lc = find(id);
            if (lc!=null){
                return lbDao.delete(lc)>0;
            }
        } catch (SQLException e) {
            Log.i(TAG, "LibraryClientManager: " + e.getMessage());
        }
        return false;
    }


    public boolean ifExist(int idBookLangue) {
        QueryBuilder<LibraryClient, Integer> qb = lbDao.queryBuilder();
        LibraryClient livreClient = null;
        try {
            qb.where().eq("livre_id", idBookLangue);
            livreClient = qb.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(livreClient !=null){
            return true;
        }else{
            return false;
        }

    }

    public List<LibraryClient> getAllByClient(int idClient) {
        try {
            return lbDao.queryForEq("client_id", idClient);
        } catch (SQLException e) {
            Log.i(TAG, "LibraryClientManager: " + e.getMessage());
        }
        return null;
    }











        public int findId(int idClient, int idBookLangue) {
            QueryBuilder<LibraryClient, Integer> qb = lbDao.queryBuilder();
            LibraryClient librayClient = null;
            try {
                qb.where().eq("client_id", idClient).and().eq("livre_id", idBookLangue);
                librayClient = qb.queryForFirst();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return librayClient.getId();
        }

















}
