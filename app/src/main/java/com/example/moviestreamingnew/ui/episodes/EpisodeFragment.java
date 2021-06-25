package com.example.moviestreamingnew.ui.episodes;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.adapters.ShowEpisodesAdapter;
import com.example.moviestreamingnew.models.Video;
import com.example.moviestreamingnew.repository.Storage;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EpisodeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View root;

    private RecyclerView episodeRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ShowEpisodesAdapter showEpisodesAdapter;

    private String show, industry, genre;
    private Storage storage;

    private ArrayList<Video> videos;

    private ProgressBar progressBar;

    private double total = 100;

    public EpisodeFragment() {
        // Required empty public constructor
    }

    public EpisodeFragment(String show, String industry, String genre){
        this.show = show;
        this.genre = genre;
        this.industry = industry;
        this.storage = new Storage();
        this.videos = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_episode, container, false);

        /*ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.execute(new Runnable() {
            @Override
            public void run() {
                //videos = storage.getEpisodes(show, industry, genre);
                videos.add(new Video("The Hofstadter Insufficiency", "The Hofstadter Insufficiency"));
                videos.add(new Video("Sheldon Going Crazy", "Sheldon Going Crazy"));
                videos.add(new Video("Penny Going Crazy", "Penny Going Crazy"));
                videos.add(new Video("Rajesh Going Crazy", "Rajesh Going Crazy"));
            }
        });

        try {
            pool.awaitTermination(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        this.progressBar = root.findViewById(R.id.progress_bar);

        /*this.episodeRecyclerView = root.findViewById(R.id.episodes_recyclerView);
        this.linearLayoutManager = new LinearLayoutManager(root.getContext());
        //this.showEpisodesAdapter = new ShowEpisodesAdapter(episodeTitles, episodeTitles.size());
        this.showEpisodesAdapter = new ShowEpisodesAdapter(root.getContext(), videos, videos.size());

        episodeRecyclerView.setAdapter(showEpisodesAdapter);
        episodeRecyclerView.setLayoutManager(linearLayoutManager);
        episodeRecyclerView.setHasFixedSize(true);*/

        progressBar.setProgress(0);
        progressBar.setSecondaryProgress(100);
        progressBar.setMax(100);
        progressBar.setProgressDrawable(ContextCompat.getDrawable(root.getContext(), R.drawable.circular_progress_bar));

        ContextCompat.getMainExecutor(root.getContext()).execute(() -> {
            progressBar.setProgress((int) total);
            double seconds= 1 * 2.5 * 1000; // 2.5 seconds in milli seconds

            /** CountDownTimer starts with 2.5 seconds and every onTick is 1 second */
            new CountDownTimer((long) seconds, 1000) {

                public void onTick(long millisUntilFinished) {
                    long total = (long) seconds - millisUntilFinished;
                    progressBar.setProgress((int) total);
                }

                public void onFinish() {
                    // DO something when 2.5 seconds are up
                    progressBar.setVisibility(View.GONE);
                    root.findViewById(R.id.progress_bar_layout).setVisibility(View.GONE);
                }
            }.start();
        });


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                episodeRecyclerView = root.findViewById(R.id.episodes_recyclerView);
                episodeRecyclerView.setVisibility(View.VISIBLE);
                linearLayoutManager = new LinearLayoutManager(root.getContext());
                //this.showEpisodesAdapter = new ShowEpisodesAdapter(episodeTitles, episodeTitles.size());
                showEpisodesAdapter = new ShowEpisodesAdapter(root.getContext(), videos, videos.size());

                episodeRecyclerView.setHasFixedSize(true);
                episodeRecyclerView.setAdapter(showEpisodesAdapter);
                episodeRecyclerView.setLayoutManager(linearLayoutManager);
            }
        }, 2500);

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        videos = storage.getEpisodes(show, industry, genre);
    }
}