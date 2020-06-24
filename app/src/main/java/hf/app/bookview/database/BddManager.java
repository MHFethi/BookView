package hf.app.bookview.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import hf.app.bookview.model.Author;
import hf.app.bookview.model.Book;
import hf.app.bookview.model.Category;
import hf.app.bookview.model.Client;
import hf.app.bookview.model.Langue;
import hf.app.bookview.model.BookLangue;
import hf.app.bookview.model.LibraryClient;


public class BddManager extends OrmLiteSqliteOpenHelper {
    private static String databaseName = "Book_View_bdd";
    private static SQLiteDatabase.CursorFactory factory = null;
    private static int databaseVersion = 1;
    private static final String TAG = "BddManager";


    public BddManager(Context context) {
        //Création de la bdd
        // Context -> Contexte d'execution en regle generalement l'Activity
        super(context, databaseName, factory, databaseVersion);
        Log.i(TAG, "BddManager: database create ! ");

    }



    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            // Ici on créer toutes les tables qu'on souhaite dans la bdd à partir des class model créer
            // Ici par exemple on créer la class User
            // Bien placer les model dans le meme package
            TableUtils.createTable(connectionSource, Client.class);
            TableUtils.createTable(connectionSource, Author.class);
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Book.class);
            TableUtils.createTable(connectionSource, Langue.class);
            TableUtils.createTable(connectionSource, BookLangue.class);
            TableUtils.createTable(connectionSource, LibraryClient.class);

            Log.i(TAG, "onCreate: Table for 'Client.class' created ! ");
            Log.i(TAG, "onCreate: Table for 'Book.class' created ! ");
            Log.i(TAG, "onCreate: Table for 'Author.class' created ! ");
            Log.i(TAG, "onCreate: Table for 'Langue.class' created ! ");
            Log.i(TAG, "onCreate: Table for 'Category.class' created ! ");
            Log.i(TAG, "onCreate: Table for 'BookLangue.class' created ! ");
            Log.i(TAG, "onCreate: Table for 'LibraryClient.class' created ! ");

        } catch (SQLException e) {
            Log.i(TAG, "onCreate: " + e.getMessage(), e);

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            // 1 - on supprime d'abord toutes les tables qu'on souhaite dans la bdd à partir des class model créer
            TableUtils.dropTable(connectionSource, Client.class, true);
            TableUtils.dropTable(connectionSource, Author.class, true);
            TableUtils.dropTable(connectionSource, Category.class,true);
            TableUtils.dropTable(connectionSource, Book.class, true);
            TableUtils.dropTable(connectionSource, Langue.class, true);
            TableUtils.dropTable(connectionSource, BookLangue.class, true);
            TableUtils.dropTable(connectionSource, LibraryClient.class,true);

            Log.i(TAG, "onUpgrade: Table for 'Client.class' created ! ");
            Log.i(TAG, "onUpgrade: Table for 'Book.class' created ! ");
            Log.i(TAG, "onUpgrade: Table for 'Author.class' created ! ");
            Log.i(TAG, "onUpgrade: Table for 'Langue.class' created ! ");
            Log.i(TAG, "onUpgrade: Table for 'Category.class' created ! ");
            Log.i(TAG, "onUpgrade: Table for 'LivreFormat.class' created ! ");
            Log.i(TAG, "onUpgrade: Table for 'LibraryClient.class' created ! ");

            // 2- Puis on rappelle la création BDD
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.i(TAG, "onCreate: " + e.getMessage(), e);

        }
    }
}

