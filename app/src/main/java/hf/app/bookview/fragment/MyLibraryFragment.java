package hf.app.bookview.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hf.app.bookview.R;
import hf.app.bookview.adapter.MyLibraryRecyclerViewAdapter;
import hf.app.bookview.adapter.SearchRecyclerViewAdapter;
import hf.app.bookview.model.Client;
import hf.app.bookview.session.SessionManager;

public class MyLibraryFragment extends Fragment {
    private static final String TAG = "SessionManager";
    private static SessionManager sessionManager;
    private Client c;
    private View view;
    private MyLibraryRecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_mylibrary, container, false);
        sessionManager = new SessionManager(getActivity().getApplicationContext());


        mRecyclerView = view.findViewById(R.id.recycler_view_mylibrary);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        mAdapter = new MyLibraryRecyclerViewAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);


        Log.i(TAG, "SessionManager MYLIBRARY FRAGMENT Username=====>" + sessionManager.getUsername());
        Log.i(TAG, "SessionManager MYLIBRARY FRAGMENT Login State =====>" + sessionManager.getLogin());
        return view;

    }

    public static SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
}
