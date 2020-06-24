package hf.app.bookview;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import hf.app.bookview.dao.ClientManager;
import hf.app.bookview.model.Client;

public class SubscribeActivity extends AppCompatActivity {
    private static final String TAG = "Subscribe";

    private String login;
    private String email;
    private String password;
    private String confirmPassword;


    private EditText saisieLogin;
    private EditText saisieMail;
    private EditText saisiePassword;
    private EditText saisiePasswordConfirm;

    private TextView messageError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        performTextView();
        handlebuttons();

    }


    private void handlebuttons() {
        Button btn_subscribe = findViewById(R.id.btn_subscribe);
        saisieLogin = (EditText) findViewById(R.id.tfLoginSubscribe);
        saisieMail = (EditText) findViewById(R.id.tfMailSubscribe);
        saisiePassword = (EditText) findViewById(R.id.tfPasswordSubscribe);
        saisiePasswordConfirm = (EditText) findViewById(R.id.tfPWConfirmSubscribe);
        messageError = (TextView) findViewById(R.id.messageErrorSubscribe);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = saisieLogin.getText().toString();
                email = saisieMail.getText().toString();
                password = saisiePassword.getText().toString();
                confirmPassword = saisiePasswordConfirm.getText().toString();

                ClientManager cm = new ClientManager(getApplicationContext());

                if (v.getId() == R.id.btn_subscribe) {
                    if (!"".equals(login) && !"".equals(password) && !"".equals(email)) {
                        if (isValidEmail(email)==true) {
                            Client c = new Client(login, password, email);
                            Log.i(TAG, "New Client ====>" + c);
                            if (cm.find(login) == null) {
                                cm.add(c);
                                openActivity(MainActivity.class);
                                finish();
                            } else {
                                messageError.setText("Login déja existant");
                            }
                        } else {
                            messageError.setText("Format E-mail incorrect");
                        }
                    } else {
                        messageError.setText("Veuillez remplir tous les champs");
                    }

                }
            }
        };
        btn_subscribe.setOnClickListener(listener);
    }




    private void performTextView() {
        TextView tv = (TextView) findViewById(R.id.lkConnexion);
        String connexionLink = "J'ai déjà  un compte";
        SpannableString ss = new SpannableString(connexionLink);

        ClickableSpan clickableLink = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                openActivity(MainActivity.class);
            }

            // Style set up
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#6c6c6c"));
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableLink, 0, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ss);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }


    // Method: Allow to open a new Activity
    private void openActivity(Class<?> activityClass) {
        Log.i(TAG, "OpenActivity:");
        Intent intent = new Intent(this, activityClass);
        String  messageFromSubscribe="Votre inscription validée!";

        intent.putExtra("succesSubscribe", messageFromSubscribe);

        startActivity(intent);
    }



    public static boolean isValidEmail(String email) {

        String regExp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-z]{2,8}+$";

        boolean matches = email.matches(regExp);

        if (matches == true) {
            System.out.println("Email"
                    + " validé");
            return email.matches(regExp);
        } else {
            System.out.println("Email invalide");
            return email.matches(regExp);
        }

    }




}
