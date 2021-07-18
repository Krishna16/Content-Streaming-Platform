package com.example.moviestreamingnew.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.repository.MovieAPI;
import com.example.moviestreamingnew.ui.list_find_content.ListFoundContentActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FindGenresRecyclerAdapter extends RecyclerView.Adapter<FindGenresRecyclerAdapter.FindViewHolder> {
    private ArrayList<String> genres;
    private Context context;

    public FindGenresRecyclerAdapter(ArrayList<String> genres, Context context){
        this.genres = genres;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public FindGenresRecyclerAdapter.FindViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.find_genres_recycler_layout, parent, false);

        return new FindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FindGenresRecyclerAdapter.FindViewHolder holder, int position) {
        holder.genreText.setText(genres.get(position));

        holder.genreCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked on: " + genres.get(position), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, ListFoundContentActivity.class);
                intent.putExtra("genre", genres.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public class FindViewHolder extends RecyclerView.ViewHolder{
        public CardView genreCard;
        public TextView genreText;

        public FindViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            genreCard = itemView.findViewById(R.id.genre_cardView);
            genreText = itemView.findViewById(R.id.genre_text);
        }
    }
}
