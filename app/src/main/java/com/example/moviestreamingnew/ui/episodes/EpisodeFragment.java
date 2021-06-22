package com.example.moviestreamingnew.ui.episodes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.adapters.ShowEpisodesAdapter;

import java.util.ArrayList;

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

    public EpisodeFragment() {
        // Required empty public constructor
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

        ArrayList<String> episodeTitles = new ArrayList<>();
        episodeTitles.add("The Hofstadter Insufficiency");
        episodeTitles.add("The Deception Verification");
        episodeTitles.add("The Scavenger Vortex");
        episodeTitles.add("The Raiders Minimization");
        episodeTitles.add("The Workplace Proximity");

        this.episodeRecyclerView = root.findViewById(R.id.episodes_recyclerView);
        this.linearLayoutManager = new LinearLayoutManager(root.getContext());
        this.showEpisodesAdapter = new ShowEpisodesAdapter(episodeTitles, episodeTitles.size());

        episodeRecyclerView.setLayoutManager(linearLayoutManager);
        episodeRecyclerView.setAdapter(showEpisodesAdapter);

        return root;
    }
}