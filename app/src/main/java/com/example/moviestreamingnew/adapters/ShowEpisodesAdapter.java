package com.example.moviestreamingnew.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.models.Video;
import com.example.moviestreamingnew.ui.new_user_form.NameFragment;
import com.example.moviestreamingnew.ui.videoview.VideoFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShowEpisodesAdapter extends RecyclerView.Adapter<ShowEpisodesAdapter.EpisodeViewHolder> {

    private final ArrayList<Video> episodeTitles;
    private int numberOfEpisodes;
    private Context context;

    public ShowEpisodesAdapter(Context context, ArrayList<Video> episodeTitles, int numberOfEpisodes) {
        this.episodeTitles = episodeTitles;
        this.numberOfEpisodes = numberOfEpisodes;
        this.context = context;
        Log.d("ShowEpisodesAdapter: ", "" + numberOfEpisodes);
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
        holder.episodeTitle.setText(this.episodeTitles.get(position).getName());
        holder.episodeNumber.setText("Episode " + (position + 1));

        Log.d("ShowEpisodesAdapter: ", this.episodeTitles.get(position).getName());

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "" + episodeTitles.get(position).getVideoUrl(), Toast.LENGTH_LONG).show();
                VideoFragment nameFragment = new VideoFragment(episodeTitles.get(position).getVideoUrl());

                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nameFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("ShowEpisodesAdapter: ", "Number of episodes: " + numberOfEpisodes);
        return numberOfEpisodes;
    }

    public static class EpisodeViewHolder extends RecyclerView.ViewHolder {

        private TextView episodeTitle;
        private TextView episodeNumber;
        private Button playButton;

        public EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            episodeTitle = itemView.findViewById(R.id.episode_title);
            episodeNumber = itemView.findViewById(R.id.episode_number);
            playButton = itemView.findViewById(R.id.play_button);
        }
    }
}
