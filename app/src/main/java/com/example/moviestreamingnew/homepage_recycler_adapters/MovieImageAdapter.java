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
import com.example.moviestreamingnew.repository.ShowsDatabase;

import java.util.ArrayList;
import java.util.List;

public class MovieImageAdapter extends RecyclerView.Adapter<MovieImageAdapter.ViewHolder> {

    private List<CardImageChild> movieImages;
    private Context mContext;

    public MovieImageAdapter(ArrayList<CardImageChild> movieImages, Context mContext) {
        this.movieImages = movieImages;
        this.mContext = mContext;
    }

    public void setMovieImages(ArrayList<CardImageChild> movieImages){
        this.movieImages = movieImages;
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
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.movieImage);

        //imageLoader.DisplayImage(movieImages.get(position).getImage(), holder.movieImage);

        holder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] elements = movieImages.get(position).getPath().split("/");

                String show = elements[elements.length - 1];
                String industry = elements[elements.length - 2];
                String genre = elements[elements.length - 3];

                if (show.indexOf(".") > 0)
                    show = show.substring(0, show.lastIndexOf("."));

                ShowsDatabase showsDatabase = new ShowsDatabase(mContext);

                //description fragment will open from this method
                showsDatabase.getDetails(show, industry, genre, movieImages.get(position).getImage());
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("MovieImageAdapter: ", "MovieImage size: " + movieImages.size());
        return movieImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView movieImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_image);
        }
    }
}
