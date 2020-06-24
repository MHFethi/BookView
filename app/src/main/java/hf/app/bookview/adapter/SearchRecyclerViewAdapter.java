package hf.app.bookview.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import hf.app.bookview.fragment.DetailsFragment;
import hf.app.bookview.R;
import hf.app.bookview.api.GoogleBookData;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private Context mContext;
    private List<GoogleBookData> mData;
    private RequestOptions options;

    public SearchRecyclerViewAdapter(Context mContext, List<GoogleBookData> mData) {
        this.mContext = mContext;
        this.mData = mData;

        //Request option for Glide
        options = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        final View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.book_raw_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bunble = new Bundle();
                int pos = viewHolder.getAdapterPosition();

                String title = mData.get(pos).getTitle();
                String auteur = mData.get(pos).getAuthors();
                String resume = mData.get(pos).getDescription();
                String categorie = mData.get(pos).getCategories();
                String date = mData.get(pos).getPublishedDate();
                int page = mData.get(pos).getPageCount();
                String isbn = mData.get(pos).getIsbn();
                String langue = mData.get(pos).getLangue();
                String cover = mData.get(pos).getThumbnail();


                bunble.putString("title", title);
                bunble.putString("auteur", auteur);
                bunble.putString("resume", resume);
                bunble.putString("categorie", categorie);
                bunble.putString("date", date);
                bunble.putInt("page", page);
                bunble.putString("isbn", isbn);
                bunble.putString("langue", langue);
                bunble.putString("cover", cover);

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                DetailsFragment resultat = new DetailsFragment();
                resultat.setArguments(bunble);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, resultat).addToBackStack(null).commit();

            }
        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        GoogleBookData gBook = mData.get(i);
        holder.tvTitle.setText(gBook.getTitle());
        holder.tvAuthor.setText(gBook.getAuthors());
        holder.tvPrice.setText(gBook.getPrice());
         holder.tvCategory.setText(gBook.getCategories());

        //load image from internet and set it into imageView using Glide
        Glide.with(mContext).load(gBook.getThumbnail()).apply(options).into(holder.ivThumbnail);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivThumbnail;
        TextView tvTitle, tvCategory, tvPrice, tvAuthor;
        LinearLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.thumbnail);
            tvTitle = itemView.findViewById(R.id.title);
            tvAuthor = itemView.findViewById(R.id.author);
            tvCategory = itemView.findViewById(R.id.category);
            tvPrice = itemView.findViewById(R.id.price);
            container = itemView.findViewById(R.id.container);


        }
    }


}
