package com.example.moviestreamingnew.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.models.User;

import java.util.ArrayList;

public class GenreSelectionAdapter extends RecyclerView.Adapter<GenreSelectionAdapter.GenreViewHolder> {

    private final ArrayList<String> genres;
    private Context context;

    public GenreSelectionAdapter(ArrayList<String> genres, Context context){
        this.genres = genres;
        this.context = context;
    }

    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.user_genre_selection_recycler_layout, parent, false);

        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreSelectionAdapter.GenreViewHolder holder, int position) {
        holder.genreCheck.setText(this.genres.get(position));
        holder.genreCard.setBackgroundResource(R.drawable.cardview_genre_unselected);


        //holder.genreCheck.setTag();

        //User user = new User();

        /*holder.genreCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getGenres().size() < 3){
                    user.addGenre(holder.genreCheck.getText().toString());
                }

                else{
                    Toast.makeText(context, "You cannot select more than 3!!", Toast.LENGTH_LONG).show();
                }

                //holder.genreCard.setBackgroundResource(R.drawable.cardview_genre_selected);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return this.genres.size();
    }



    public class GenreViewHolder extends RecyclerView.ViewHolder {

        public CheckBox genreCheck;
        private CardView genreCard;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            this.genreCheck = itemView.findViewById(R.id.genre_checkbox);
            this.genreCard = itemView.findViewById(R.id.genre_card);
        }
    }
}
