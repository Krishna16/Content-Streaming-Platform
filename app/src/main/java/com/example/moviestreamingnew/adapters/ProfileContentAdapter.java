package com.example.moviestreamingnew.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestreamingnew.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProfileContentAdapter extends RecyclerView.Adapter<ProfileContentAdapter.ContentViewHolder> {
    private ArrayList<String> content;

    public ProfileContentAdapter(ArrayList<String> content){
        this.content = content;
    }

    @NonNull
    @NotNull
    @Override
    public ProfileContentAdapter.ContentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_content_recycler_layout, parent, false);

        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProfileContentAdapter.ContentViewHolder holder, int position) {
        holder.editText.setText(content.get(position));
        holder.editText.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder{
        public EditText editText;

        public ContentViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.content_editText);
        }
    }
}
