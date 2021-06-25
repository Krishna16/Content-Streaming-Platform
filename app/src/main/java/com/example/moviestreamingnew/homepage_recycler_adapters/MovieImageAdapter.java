package com.example.moviestreamingnew.homepage_recycler_adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviestreamingnew.CardImageChild;
import com.example.moviestreamingnew.R;

import java.util.ArrayList;

public class MovieImageAdapter extends RecyclerView.Adapter<MovieImageAdapter.ViewHolder> {

    private ArrayList<CardImageChild> movieImages;
    private Context mContext;

    public MovieImageAdapter(ArrayList<CardImageChild> movieImages, Context mContext) {
        this.movieImages = movieImages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View movieImage = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_movie_image, parent, false);

        ViewHolder holder = new ViewHolder(movieImage);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestOptions defaultOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_background);

        /*Glide.with(mContext)
                .setDefaultRequestOptions(defaultOptions)
                .load(movieImages.get(position))
                .into(holder.movieImage);*/

        Log.d("MovieImage: ", "" + movieImages.get(position).getImage());

        Glide.with(mContext)
                .load(movieImages.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        Log.d("MovieImage size: ", "" + movieImages.size());
        return movieImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView movieImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_image);
        }
    }
}
