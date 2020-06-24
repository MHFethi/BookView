package hf.app.bookview;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.List;

import hf.app.bookview.dao.AuthorManager;
import hf.app.bookview.dao.BookLangueManager;
import hf.app.bookview.dao.BookManager;
import hf.app.bookview.dao.CategoryManager;
import hf.app.bookview.dao.ClientManager;
import hf.app.bookview.dao.LangueManager;
import hf.app.bookview.dao.LibraryClientManager;
import hf.app.bookview.model.Author;
import hf.app.bookview.model.Book;
import hf.app.bookview.model.BookLangue;
import hf.app.bookview.model.Category;
import hf.app.bookview.model.Client;
import hf.app.bookview.model.Langue;
import hf.app.bookview.model.LibraryClient;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "DataManager";


    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        BookManager bDao = new BookManager(appContext);
        AuthorManager aDao = new AuthorManager(appContext);
        CategoryManager cDao = new CategoryManager(appContext);
        LangueManager lDao = new LangueManager(appContext);
        BookLangueManager blDao = new BookLangueManager(appContext);

        blDao.findbyIdBookIdLangue(1,1 );

        LibraryClientManager librayClientDao = new LibraryClientManager(appContext);




        List<LibraryClient> livres=librayClientDao.getAllByClient(1);
        Log.i(TAG, "====>LIST LIVRE PAR CLIENT "+ livres.size());


//2811648992

    }
}
