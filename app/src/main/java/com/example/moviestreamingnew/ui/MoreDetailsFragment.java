package com.example.moviestreamingnew.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviestreamingnew.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MoreDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View root;
    private TextView genres;

    private String showGenres;

    private String platforms;
    private TextView alternativePlatforms;

    public MoreDetailsFragment() {
        // Required empty public constructor
    }

    public MoreDetailsFragment(String genres, String platforms){
        this.showGenres = genres;
        this.platforms = platforms;
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
        root = inflater.inflate(R.layout.fragment_more_details, container, false);

        if (showGenres.contains("[") || showGenres.contains("]")){
            showGenres.replace("[", "");
            showGenres.replace("]", "");
        }

        if (platforms.contains("[") || platforms.contains("]")){
            platforms.replace("[", "");
            platforms.replace("]", "");
        }

        genres = root.findViewById(R.id.genres);
        genres.setText(showGenres);

        alternativePlatforms = root.findViewById(R.id.alternate_platforms);
        alternativePlatforms.setText(platforms);

        return root;
    }
}