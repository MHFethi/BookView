package hf.app.bookview.dao;


import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import hf.app.bookview.database.BddManager;
import hf.app.bookview.model.Client;


public class ClientManager implements IDao<Client> {
    private static final String TAG = "ClientManager";
    // Il nous faut une reference a la bdd sur laquel il peut travailler
    BddManager database;
    // On créer le dao generique de BdManager pour recuperer les methode crud general
    Dao<Client,Integer>cDao;

    public ClientManager(Context context){
        database = new BddManager(context);
        try {
            cDao = database.getDao(Client.class);
        } catch (SQLException e) {
            Log.i(TAG, "ClientManager: " + e.getMessage());
        }
    }


    @Override
    public List<Client> getAll() {
        // queryForAll permet de recuperer une liste de donnée
        try {
            return cDao.queryForAll();
        } catch (SQLException e) {
            Log.i(TAG, "ClientManager: " + e.getMessage());
        }
        return null;
    }


    @Override
    public Client find(int id) {
        // queryForId permet recuperer une donnée par ID
        try {
            return cDao.queryForId(id);
        } catch (SQLException e) {
            Log.i(TAG, "ClientManager: " + e.getMessage());
        }
        return null;
    }


    @Override
    public boolean add(Client item) {
        // create permet d'aajouter une donnée
        try {
            return cDao.create(item)>0;
        } catch (SQLException e) {
            Log.i(TAG, "ClientManager: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean update(Client item) {
        // update permet de modifier une donnée
        try {
            return cDao.update(item)>0;
        } catch (SQLException e) {
            Log.i(TAG, "ClientManager: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean delete(Client item) {
        // delete permet de modifier une donnée
        try {
            return cDao.delete(item)>0;
        } catch (SQLException e) {
            Log.i(TAG, "ClientManager: " + e.getMessage());
        }
        return false;
    }


    @Override
    public boolean delete(int id) {
        try {
            Client c = find(id);
            if (c!=null){
                return cDao.delete(c)>0;
            }
        } catch (SQLException e) {
            Log.i(TAG, "ClientManager: " + e.getMessage());
        }
        return false;
    }


    /**
     * custom queries
     */


    public Client find(String login) {
        // On prepare une requete
        // En precisant le type qu'on veux (ici User)
        QueryBuilder<Client, Integer> qb = cDao.queryBuilder();
        Client client=null;

        try {
            //eq() = egal à
            // and() = AND ..
            qb.where().eq("login", login);
            client = qb.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }


    public Client find(String login, String password) {
        QueryBuilder<Client, Integer> qb = cDao.queryBuilder();
        Client client=null;

        try {
            qb.where().eq("login", login).and().eq("password", password);
            client = qb.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }



}
