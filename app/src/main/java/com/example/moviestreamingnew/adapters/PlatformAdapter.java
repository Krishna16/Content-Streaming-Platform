package com.example.moviestreamingnew.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestreamingnew.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PlatformAdapter extends RecyclerView.Adapter<PlatformAdapter.PlatformViewHolder> {

    private ArrayList<String> watchProviders;

    public PlatformAdapter(ArrayList<String> watchProviders) {
        this.watchProviders = watchProviders;
    }

    @NonNull
    @NotNull
    @Override
    public PlatformAdapter.PlatformViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.platform_with_logo_recycler_layout, parent, false);

        return new PlatformViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PlatformAdapter.PlatformViewHolder holder, int position) {
        String platform = watchProviders.get(position).toLowerCase();

        if (platform.equals("netflix")){
            holder.platform_logo.setImageResource(R.drawable.netflix);
            holder.platform_name.setText(watchProviders.get(position));
        }

        else if (platform.equals("amazon prime video") || platform.equals("amazon video")){
            holder.platform_logo.getLayoutParams().width = 300;
            holder.platform_logo.requestLayout();
            holder.platform_logo.setImageResource(R.drawable.prime_video);
        }

        else if (platform.equals("youtube")){
            holder.platform_logo.setImageResource(R.drawable.youtube);
            holder.platform_name.setText(watchProviders.get(position));
        }

        else if (platform.equals("apple itunes")){
            holder.platform_logo.setImageResource(R.drawable.apple_itunes);
            holder.platform_name.setText(watchProviders.get(position));
        }

        else if (platform.equals("google play movies")){
            holder.platform_logo.setImageResource(R.drawable.google_play_movies);
            holder.platform_name.setText(watchProviders.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return watchProviders.size();
    }

    public class PlatformViewHolder extends RecyclerView.ViewHolder {
        public ImageView platform_logo;
        public TextView platform_name;

        public PlatformViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            platform_logo = itemView.findViewById(R.id.platform_logo);
            platform_name = itemView.findViewById(R.id.platform_name);
        }
    }
}
