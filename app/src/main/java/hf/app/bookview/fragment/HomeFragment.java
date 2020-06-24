package hf.app.bookview.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import hf.app.bookview.R;
import hf.app.bookview.model.Client;
import hf.app.bookview.session.SessionManager;

public class HomeFragment extends Fragment {
    private static final String TAG = "SessionManager";
    private SessionManager sessionManager;
    private Client c;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        Log.i(TAG, "SessionManager HOME FRAGMENT Username=====>" + sessionManager.getUsername());
        Log.i(TAG, "SessionManager HOME FRAGMENT Login State =====>" + sessionManager.getLogin());
        return inflater.inflate(R.layout.fragment_home, container, false);

    }
}
