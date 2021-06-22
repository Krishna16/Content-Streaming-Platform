package com.example.moviestreamingnew.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestreamingnew.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShowEpisodesAdapter extends RecyclerView.Adapter<ShowEpisodesAdapter.EpisodeViewHolder> {

    private final ArrayList<String> episodeTitles;
    private int numberOfEpisodes;

    public ShowEpisodesAdapter(ArrayList<String> episodeTitles, int numberOfEpisodes) {
        this.episodeTitles = episodeTitles;
        this.numberOfEpisodes = numberOfEpisodes;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episodes_recycler_layout, parent, false);

        return new EpisodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowEpisodesAdapter.EpisodeViewHolder holder, int position) {
        holder.episodeTitle.setText(this.episodeTitles.get(position));
        holder.episodeNumber.setText("Episode " + (position + 1));
    }

    @Override
    public int getItemCount() {
        return numberOfEpisodes;
    }

    public class EpisodeViewHolder extends RecyclerView.ViewHolder {

        private TextView episodeTitle;
        private TextView episodeNumber;

        public EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            episodeTitle = itemView.findViewById(R.id.episode_title);
            episodeNumber = itemView.findViewById(R.id.episode_number);
        }
    }
}
