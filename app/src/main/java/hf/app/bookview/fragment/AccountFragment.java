package hf.app.bookview.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import hf.app.bookview.MainActivity;
import hf.app.bookview.R;
import hf.app.bookview.dao.ClientManager;
import hf.app.bookview.model.Client;
import hf.app.bookview.session.SessionManager;

public class AccountFragment extends Fragment {
    private SessionManager sessionManager;
    private static final String TAG = "SessionManager";
    private String userName;
    private Client c;
    private ClientManager cm;
    private View view;
    private EditText login;
    private EditText mail;
    private EditText password;
    private TextView message;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        Log.i(TAG, "SessionManager ACCOUNT FRAGMENT Username=====>" + sessionManager.getUsername());
        Log.i(TAG, "SessionManager ACCOUNT FRAGMENT Login State =====>" + sessionManager.getLogin());


        login = (EditText) view.findViewById(R.id.dateParutionDetail);
        mail = (EditText) view.findViewById(R.id.tfMailAccount);
        password = (EditText) view.findViewById(R.id.tfPasswordAccount);

        cm = new ClientManager(getActivity().getApplicationContext());
        c = cm.find(sessionManager.getUsername());

        login.setText(c.getLogin().replaceFirst(".", (c.getLogin().charAt(0) + "").toUpperCase()));
        mail.setText(c.getEmail());
        password.setText(c.getPassword());

        actionButton();

        return view;

    }

    private void actionButton() {
        Button btn_update = view.findViewById(R.id.btn_update_account);
        Button btn_delete = view.findViewById(R.id.btn_delete_account);
        Button btn_deconnexion = view.findViewById(R.id.btn_deconnexion);
        message = view.findViewById(R.id.message_update_frag);

        View.OnClickListener listener = new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_delete_account) {
                    deleteAccountConfirmation();
                    return;
                }else if (v.getId()==R.id.btn_deconnexion){
                    alertDeconnexion();
                    return;
                } else if (v.getId() == R.id.btn_update_account) {
                    c = cm.find(sessionManager.getUsername());
                    c.setLogin(login.getText().toString());
                    c.setEmail(mail.getText().toString());
                    c.setPassword(password.getText().toString());
                    cm.update(c);
                    message.setText("La mise à jour a bien été effectuée ");
                    sessionManager.setUsername(c.getLogin());
                    return;
                }
            }
        };
        btn_update.setOnClickListener(listener);
        btn_delete.setOnClickListener(listener);
        btn_deconnexion.setOnClickListener(listener);
    }


    // Methode qui permettra d'ouvrir une activity
    private void openActivity(Class<?> activityClass) {
        Log.i(TAG, "OpenActivity:" + activityClass);
        Intent intent = new Intent(getActivity().getApplicationContext(), activityClass);
        startActivity(intent);
    }



    private void deleteAccountConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Voulez-vous vraiment supprimer votre compte ?");
        // Position boutton
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                c = cm.find(sessionManager.getUsername());
                cm.delete(c);
                // Fermeture de la session
                sessionManager.setLogin(false);
                sessionManager.setUsername(null);
                // Redirect vers l'activity Login
                openActivity(MainActivity.class);
            }
        });
        // Negative button
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    private void alertDeconnexion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Voulez-vous vous deconnectez ?");
        // Position boutton
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sessionManager.setLogin(false);
                sessionManager.setUsername(null);
                openActivity(MainActivity.class);
            }
        });
        // Negative button
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }






}
