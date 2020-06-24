package hf.app.bookview.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hf.app.bookview.R;
import hf.app.bookview.adapter.SearchRecyclerViewAdapter;
import hf.app.bookview.api.GoogleBookData;
import hf.app.bookview.session.SessionManager;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class SearchFragment extends Fragment {
    private static final String TAG = SearchFragment.class.getSimpleName();
    private SessionManager sessionManager;


    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private ArrayList<GoogleBookData> mBooks;
    private RequestQueue mRequestQueue;

    private EditText search_edit_text;
    private Button search_button;
    private ProgressBar loading_indicator;
    private TextView error_message;

    private View view;
    private RecyclerView mRecyclerView;
    private SearchRecyclerViewAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        sessionManager = new SessionManager(getActivity().getApplicationContext());
        //Log.i(TAG, "SessionManager SEARCH FRAGMENT Username=====>" + sessionManager.getUsername());
        // Log.i(TAG, "SessionManager SEARCH FRAGMENT Login State =====>" + sessionManager.getLogin());

        search_edit_text = view.findViewById(R.id.tfSearch);
        search_button = view.findViewById(R.id.btn_search);
        loading_indicator = view.findViewById(R.id.loading_indicator);
        error_message = view.findViewById(R.id.message_display);


        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mBooks = new ArrayList<GoogleBookData>();
        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        searchBooks();
        return view;

    }


    public void searchBooks() {
        Button btn_search = view.findViewById(R.id.btn_search);

        View.OnClickListener listener = new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.btn_search) {
                    mBooks.clear();
                    search();
                }
            }
        };
        btn_search.setOnClickListener(listener);
    }

    private void search() {
        String search_query = search_edit_text.getText().toString();

        boolean is_connected = Read_network_state(getActivity());
        if (!is_connected) {
            error_message.setText(R.string.Failed_to_Load_data);
            mRecyclerView.setVisibility(View.INVISIBLE);
            error_message.setVisibility(View.VISIBLE);
            return;
        }

        if (search_query.equals("")) {
            return;

        }

        String final_query = search_query.replace(" ", "+");
        Uri uri = Uri.parse(BASE_URL + final_query);
        Uri.Builder buider = uri.buildUpon();

        parseJson(buider.toString());
    }


    private void parseJson(String key) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        String title = "";
                        String author = "";
                        String categories = "";
                        String langue = "";
                        String publishedDate = "NoT Available";
                        String description = "No Description";
                        int pageCount = 0;
                        categories="No categories Available ";
                        String idIsbn = "";

                        String price = "NOT_FOR_SALE";
                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0; i < items.length(); i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");


                                try {
                                    title = volumeInfo.getString("title");

                                    JSONArray authors = volumeInfo.getJSONArray("authors");
                                    if (authors.length() == 1) {
                                        author= authors.getString(0);
                                    } else {
                                        author= authors.getString(0) + "|" + authors.getString(1);
                                    }


                                    publishedDate = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");
                                    price = listPrice.getString("amount") + " " + listPrice.getString("currencyCode");
                                    description = volumeInfo.getString("description");
                                    categories=volumeInfo.getJSONArray("categories").getString(0);

                                } catch (Exception e) {

                                }

                                JSONArray isbnInfo = volumeInfo.getJSONArray("industryIdentifiers");
                                for (int j = 0; j < isbnInfo.length(); j++) {
                                    JSONObject jsonObject1 = isbnInfo.getJSONObject(j);
                                    idIsbn = jsonObject1.optString("identifier");
                                }

                                String previewLink = volumeInfo.getString("previewLink");
                                String url = volumeInfo.getString("infoLink");
                                String thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
                                langue=volumeInfo.getString("language").toUpperCase();




                                mBooks.add(new GoogleBookData(title , author , publishedDate , description ,categories
                                        ,thumbnail, previewLink, price, pageCount, url , idIsbn, langue.toUpperCase()));

                                mAdapter = new SearchRecyclerViewAdapter(getActivity(), mBooks);
                                mRecyclerView.setAdapter(mAdapter);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("TAG", e.toString());

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }


    private boolean Read_network_state(Context context) {
        boolean is_connected;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        is_connected = info != null && info.isConnectedOrConnecting();
        return is_connected;
    }


}
