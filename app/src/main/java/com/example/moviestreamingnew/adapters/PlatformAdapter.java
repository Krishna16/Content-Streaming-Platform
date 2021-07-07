package com.example.moviestreamingnew.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class PlatformAdapter extends RecyclerView.Adapter<PlatformAdapter.PlatformViewHolder> {

    @NonNull
    @NotNull
    @Override
    public PlatformAdapter.PlatformViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PlatformAdapter.PlatformViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PlatformViewHolder extends RecyclerView.ViewHolder {
        public PlatformViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
