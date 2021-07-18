package com.example.moviestreamingnew.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.ColumnInfo;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviestreamingnew.CardImageChild;
import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.common.BlurTransformation;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListFoundContentAdapter extends RecyclerView.Adapter<ListFoundContentAdapter.ListContentViewHolder> {
    private ArrayList<CardImageChild> images;
    private Context context;

    public ListFoundContentAdapter(ArrayList<CardImageChild> images, Context context){
        this.images = images;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ListFoundContentAdapter.ListContentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_scroll_with_genre, parent, false);

        return new ListContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListFoundContentAdapter.ListContentViewHolder holder, int position) {
        Glide.with(context)
                .load(images.get(position).getImage())
                .transform(new BlurTransformation(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.cardView_blur_image);

        Glide.with(context)
                .load(images.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.cardView_image);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ListContentViewHolder extends RecyclerView.ViewHolder{
        public ImageView cardView_image;
        public ImageView cardView_blur_image;

        public ListContentViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            cardView_image = itemView.findViewById(R.id.cardView_image);
            cardView_blur_image = itemView.findViewById(R.id.cardView_blur_image);
        }
    }
}
