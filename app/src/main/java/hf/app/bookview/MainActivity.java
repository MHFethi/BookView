package hf.app.bookview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import hf.app.bookview.dao.ClientManager;
import hf.app.bookview.fragment.HomeFragment;
import hf.app.bookview.model.Client;
import hf.app.bookview.session.SessionManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Login";

    private EditText saisieLogin;
    private EditText saisieMdp;

    private TextView ErrorMessgage;
    private String message;

    private String login;
    private String password;

    private SessionManager sessionManager;

    private TextView messageMainActivity ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(getApplicationContext());
        Log.i(TAG, "SessionManager USERNAME =====>" + sessionManager.getUsername());
        Log.i(TAG, "SessionManager LOGIN STATE =====>" + sessionManager.getLogin());

        actionbutton();
        performTextView();


        messageMainActivity = (TextView) findViewById(R.id.messageMainActivity);
        Intent intent = getIntent();
        String messageFromSubscribe = intent.getStringExtra("succesSubscribe");
        if (intent != null) {
            // On modifie le text view Welcome en ajouter la donnée grace a la methode getStringExtra() qui prend en parametre le name donnée dans le putExtra() de l'Activity precedent
            messageMainActivity.setText(messageFromSubscribe);
        }

    }

    private void actionbutton() {
        Button connexion = findViewById(R.id.btn_login);

        saisieLogin = (EditText) findViewById(R.id.tfLogin);
        saisieMdp = (EditText) findViewById(R.id.tfPassword);
        ErrorMessgage = (TextView) findViewById(R.id.errorMessage);


        View.OnClickListener listener = new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                HomeFragment hf = new HomeFragment();


                // On recupere le Pseudo avec saisiePseudo et on utilise la methode getText() suivie de toString()
                login = saisieLogin.getText().toString();
                password = saisieMdp.getText().toString();

                ClientManager cm = new ClientManager(getApplicationContext());
                Client c = new Client();

                if (v.getId() == R.id.btn_login) {
                    if (!"".equals(login) && !"".equals(password)) {
                        if (cm.find(login, password) != null) {
                            c = cm.find(login, password);
                            // Initalisation de la session, true == ouverture session
                            sessionManager.setLogin(true);
                            // On attribue un visiteur a la session
                            sessionManager.setUsername(c.getLogin());
                            Log.i(TAG, "login OK ");
                            openActivity(HomeActivity.class);
                            finish();
                            return;
                        } else {
                            message = "Erreur de saisie";
                            Log.i(TAG, "login KO ");
                            ErrorMessgage.setText(message);
                        }
                    } else {
                        message = "Veuillez remplir les champs";
                        ErrorMessgage.setText(message);
                        Log.i(TAG, "login KO ");

                    }
                }
            }
        };
        connexion.setOnClickListener(listener);

    }


    private void performTextView() {
        TextView tv = (TextView) findViewById(R.id.lkNewAccount);
        String newAccountLink = "Pas de compte ? Par ici";
        SpannableString ss = new SpannableString(newAccountLink);

        ClickableSpan clickableLink = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                openActivity(SubscribeActivity.class);
            }

            // ici on redefini son style
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#f9943a"));
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableLink, 16, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ss);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }


    // Methode qui permettra d'ouvrir une activity
    private void openActivity(Class<?> activityClass) {
        Log.i(TAG, "OpenActivity:" + activityClass);
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
