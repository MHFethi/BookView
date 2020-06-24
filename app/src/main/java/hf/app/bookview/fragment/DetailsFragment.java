package hf.app.bookview.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import hf.app.bookview.MainActivity;
import hf.app.bookview.R;
import hf.app.bookview.dao.AuthorManager;
import hf.app.bookview.dao.BookLangueManager;
import hf.app.bookview.dao.BookManager;
import hf.app.bookview.dao.CategoryManager;
import hf.app.bookview.dao.ClientManager;
import hf.app.bookview.dao.LangueManager;
import hf.app.bookview.dao.LibraryClientManager;
import hf.app.bookview.fragment.AccountFragment;
import hf.app.bookview.fragment.HomeFragment;
import hf.app.bookview.fragment.MyLibraryFragment;
import hf.app.bookview.fragment.SearchFragment;
import hf.app.bookview.model.Author;
import hf.app.bookview.model.Book;
import hf.app.bookview.model.BookLangue;
import hf.app.bookview.model.Category;
import hf.app.bookview.model.Client;
import hf.app.bookview.model.Langue;
import hf.app.bookview.model.LibraryClient;
import hf.app.bookview.session.SessionManager;

public class DetailsFragment extends Fragment {
    private static final String TAG = "SessionManager";
    private SessionManager sessionManager;
    private Client mClient;
    private Author mAuthor;
    private Category mCategory;
    private Langue mLangue;
    private Book mBook;
    private BookLangue mBooklangue;
    private LibraryClient mLibraryClient;

    private BookManager bookDao;
    private AuthorManager auteurDao;
    private CategoryManager categoryDao;
    private LangueManager langueDao;
    private BookLangueManager bookLangueDao;
    private ClientManager clientDao;
    private LibraryClientManager librayClientDao;


    private View view;
    private String titre;
    private String auteur;
    private String resume;
    private String categorie;
    private String date;
    private int page;
    private String isbn;
    private String langue;
    private String cover;

    private TextView tvTitle;
    private TextView tvAuteur;
    private TextView tvResume;
    private TextView tvCategorie;
    private TextView tvDate;
    private TextView tvPage;
    private TextView tvIsbn;
    private TextView tvLangue;
    private ImageView ivThumbnail;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, container, false);
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        // Appelle de la classe Bundle pour recuperer les donnÃ©es envoyÃ©e avec la method (getArguments)
        titre = getArguments().getString("title");
        auteur = getArguments().getString("auteur");
        resume = getArguments().getString("resume");
        categorie = getArguments().getString("categorie");
        date = getArguments().getString("date");
        page = getArguments().getInt("page");
        isbn = getArguments().getString("isbn");
        langue = getArguments().getString("langue");
        cover = getArguments().getString("cover");

        tvTitle = view.findViewById(R.id.title_book_detail);
        tvAuteur = view.findViewById(R.id.title_author_detail);
        tvResume = view.findViewById(R.id.tv_resume_details);
        tvCategorie = view.findViewById(R.id.genreDetail);
        tvDate = view.findViewById(R.id.dateParutionDetail);
        tvPage = view.findViewById(R.id.nbPageDetail);
        tvIsbn = view.findViewById(R.id.isbnDetail);
        tvLangue = view.findViewById(R.id.languesDetail);
        ivThumbnail = view.findViewById(R.id.ivCover);

        tvTitle.setText(titre);
        tvAuteur.setText(auteur);
        tvResume.setText(resume);
        tvCategorie.setText(categorie);
        tvDate.setText(date);
        tvPage.setText(String.valueOf(page));
        tvIsbn.setText(isbn);
        tvLangue.setText(langue);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
        Glide.with(this).load(cover).apply(requestOptions).into(ivThumbnail);

        Log.i(TAG, "SessionManager HOME FRAGMENT Username=====>" + sessionManager.getUsername());
        Log.i(TAG, "SessionManager HOME FRAGMENT Login State =====>" + sessionManager.getLogin());
        actionButton();
        return view;
    }


    private void actionButton() {
        Button btn_add = view.findViewById(R.id.btn_add);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_add) {
                    mAuthor = new Author(auteur);
                    mCategory = new Category(categorie);
                    mLangue = new Langue(langue);

                    auteurDao = new AuthorManager(getActivity().getApplicationContext());
                    categoryDao = new CategoryManager(getActivity().getApplicationContext());
                    langueDao = new LangueManager(getActivity().getApplicationContext());
                    bookLangueDao = new BookLangueManager(getActivity().getApplicationContext());
                    bookDao = new BookManager(getActivity().getApplicationContext());
                    librayClientDao = new LibraryClientManager(getActivity().getApplicationContext());

                    //1- Ajout donnée dans les tables
                    auteurDao.add(mAuthor);
                    categoryDao.add(mCategory);
                    if (langueDao.find(mLangue.getnLangue()) == null) {
                        langueDao.add(mLangue);
                    }

                    // 2- Ajout Book
                    mBook = new Book(titre, auteurDao.find(mAuthor.getNom_prenom()), resume, date, categoryDao.find(mCategory.getnCategory()), cover, page, isbn);

                    if (bookDao.add(mBook) == true) {
                        //3- Ajout donnée Livre + Langue dans la table intermediaire Book_Langue
                        mBooklangue = new BookLangue(bookDao.find(mBook.getIsbn()), langueDao.find(mLangue.getnLangue()));
                        bookLangueDao.add(mBooklangue);
                    }

                    // 3- Ajout Livre dans la Library Client
                    clientDao = new ClientManager(getActivity().getApplicationContext());
                    mClient = clientDao.find(sessionManager.getUsername());

                    // 3.2 Recherche objet Livre/Langue
                    int idBook = bookDao.findId(mBook.getIsbn());
                    int idLangue = langueDao.findId(mLangue.getnLangue());
                    mBooklangue = bookLangueDao.findbyIdBookIdLangue(idBook, idLangue);

                    //3-3 Creation object LibraryClient
                    mLibraryClient = new LibraryClient(mClient, mBooklangue, false);

                    // 3-4 Recherche ID Livre/Langue
                    int idBookLangue = bookLangueDao.findId(idBook, idLangue);
                    // 3.5 Ajout dans la table LibraryClient Si non existant dans la table
                    if (librayClientDao.ifExist(idBookLangue) == false) {
                        infoStatueBook();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Déja existant", Toast.LENGTH_LONG).show();
                    }

                }
            }
        };
        btn_add.setOnClickListener(listener);
    }


    private void infoStatueBook() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Marqué comme:");
        // Position boutton
        builder.setPositiveButton("Lu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mLibraryClient = new LibraryClient(mClient, mBooklangue, true);
                librayClientDao.add(mLibraryClient);
            }
        });
        // Negative button
        builder.setNegativeButton("Non lu ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mLibraryClient = new LibraryClient(mClient, mBooklangue, false);
                librayClientDao.add(mLibraryClient);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
