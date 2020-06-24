package hf.app.bookview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import hf.app.bookview.R;
import hf.app.bookview.dao.BookLangueManager;
import hf.app.bookview.dao.BookManager;
import hf.app.bookview.dao.ClientManager;
import hf.app.bookview.dao.LangueManager;
import hf.app.bookview.dao.LibraryClientManager;
import hf.app.bookview.fragment.DetailsFragment;
import hf.app.bookview.fragment.MyLibraryFragment;
import hf.app.bookview.model.Book;
import hf.app.bookview.model.BookLangue;
import hf.app.bookview.model.Client;
import hf.app.bookview.model.Langue;
import hf.app.bookview.model.LibraryClient;

public class MyLibraryRecyclerViewAdapter extends RecyclerView.Adapter<MyLibraryRecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "MyLibraryRecycler";
    private List<LibraryClient> data = new ArrayList<LibraryClient>();
    private static Context context;
    private RequestOptions options;
    static MyViewHolder viewHolder;


    private LibraryClient lc = new LibraryClient();
    private LibraryClientManager librayClientDao;
    private BookManager bookDao;
    private LangueManager langueDao;
    private ClientManager clientManager;

    public MyLibraryRecyclerViewAdapter(Context context) {
        this.context = context;
        librayClientDao = new LibraryClientManager(context);
        clientManager = new ClientManager(context);

        Client client = clientManager.find(MyLibraryFragment.getSessionManager().getUsername());
        data = librayClientDao.getAllByClient(client.getId());
        //Request option for Glide
        options = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.book_mylibray_item, parent, false);
        viewHolder = new MyViewHolder(view);


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        lc = data.get(position);
        holder.tvTitle.setText(lc.getLivre().getBook().getTitle());
        holder.tvIsbn.setText("Ref ISBN: " + lc.getLivre().getBook().getIsbn());
        holder.tvLangue.setText("Langue: " + lc.getLivre().getLangue().getnLangue());
        Glide.with(context).load(lc.getLivre().getBook().getThumbnail()).apply(options).into(holder.ivThumbnail);

        //SetText Statue dans Recycler Item row selon si False ou True
        if (lc.isStatue() == true) {
            holder.tvStatue.setText("Lue");
        } else {
            holder.tvStatue.setText("Non lue");
        }


        // Ouverture AlertDialog pour modification données dans la Library client
        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_detail_mylibrary) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                    mBuilder.setView(LayoutInflater.from(context).inflate(R.layout.alert_dialog_mylibrary, null, false));


                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    Button delete = (Button) dialog.findViewById(R.id.btn_delete_book);
                    Button cancel = (Button) dialog.findViewById(R.id.btn_cancel);
                    Button update = (Button) dialog.findViewById(R.id.btn_update_statue);

                    //SetText Statue dans Alert Dialog  selon si False ou True
                    if (holder.tvStatue.getText() == "Non lue") {
                        update.setText("Marquer comme lue");
                    } else {
                        update.setText("Marquer comme Non lue");
                    }


                    //Mise en place des listenner dans l'alertDialog (Modification, suppression, annulation)
                    View.OnClickListener mAlertDialListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Ici on recupere les donnée de la list dans un objet LibraryClient
                            lc = data.get(position);
                            // On recherche id LibraryClient par ID livre et ID Client pour etre sur de supprimer le bon dans la BDD
                            int idLibraryClient = librayClientDao.findId(lc.getClient().getId(), lc.getLivre().getId());
                            // Si btn_cancel -> Fermeture de l'Alert Dialog
                            if (v.getId() == R.id.btn_cancel) {
                                dialog.cancel();
                                // Si btn_delete -> suppression donnée dans la BDD
                            } else if (v.getId() == R.id.btn_delete_book) {
                                librayClientDao.delete(idLibraryClient);
                                // On refresh la list du recyclerView
                                // On recupere la position
                                data.remove(holder.getAdapterPosition());
                                // Puis on la supprime
                                notifyDataSetChanged();
                                dialog.cancel();
                            } else if (v.getId() == R.id.btn_update_statue) {


                                if (holder.tvStatue.getText() == "Non lue") {
                                    lc.setStatue(true);
                                } else if (holder.tvStatue.getText() == "Lue") {
                                    lc.setStatue(false);
                                }
                                librayClientDao.update(lc);
                                notifyDataSetChanged();
                                dialog.cancel();
                            }
                        }
                    };
                    delete.setOnClickListener(mAlertDialListener);
                    cancel.setOnClickListener(mAlertDialListener);
                    update.setOnClickListener(mAlertDialListener);

                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivThumbnail;
        private static TextView tvTitle;
        private TextView tvStatue;
        private TextView tvIsbn;
        private TextView tvLangue;
        private Button btn_update;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.cover);
            tvTitle = itemView.findViewById(R.id.title_mylibrary);
            tvIsbn = itemView.findViewById(R.id.isbn_mylibrary);
            tvStatue = itemView.findViewById(R.id.statue_mylibrary);
            tvLangue = itemView.findViewById(R.id.langue_mylibrary);
            btn_update = itemView.findViewById(R.id.btn_detail_mylibrary);

        }


    }
}
