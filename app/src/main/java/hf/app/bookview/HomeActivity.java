package hf.app.bookview;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import hf.app.bookview.fragment.AccountFragment;
import hf.app.bookview.fragment.HomeFragment;
import hf.app.bookview.fragment.MyLibraryFragment;
import hf.app.bookview.fragment.SearchFragment;
import hf.app.bookview.model.Client;
import hf.app.bookview.session.SessionManager;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "SessionManager";
    private SessionManager sessionManager;
    private String userName;
    private Client c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }

        sessionManager = new SessionManager(getApplicationContext());
        userName = sessionManager.getUsername();
        Log.i(TAG, "SessionManager HOME ACTIVITY Username=====>" + userName);
        Log.i(TAG, "SessionManager HOME ACTIVITY Login State =====>" + sessionManager.getLogin());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.action_library:
                            selectedFragment = new MyLibraryFragment();
                            break;
                        case R.id.action_my_account:
                            selectedFragment = new AccountFragment();
                            break;
                        case R.id.action_research:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.action_home:
                            selectedFragment = new HomeFragment();
                            break;
                    }


                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}