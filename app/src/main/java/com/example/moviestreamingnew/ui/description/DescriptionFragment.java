package com.example.moviestreamingnew.ui.description;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.ui.episodes.EpisodeFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;

public class DescriptionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View root;

    private MaterialTextView description;

    private TabLayout episodeTab;

    public DescriptionFragment() {
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
        this.root = inflater.inflate(R.layout.fragment_description, container, false);
        // Inflate the layout for this fragment

        this.description = root.findViewById(R.id.show_description);

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = description.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                description.setLayoutParams(params);
            }
        });

        this.episodeTab = root.findViewById(R.id.episode_tab);
        episodeTab.addTab(episodeTab.newTab().setText("Episodes(24)"));
        episodeTab.addTab(episodeTab.newTab().setText("More Details"));
        episodeTab.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

        episodeTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    EpisodeFragment episodeFragment = new EpisodeFragment();

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.tab_fragment_container, episodeFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

                if (tab.getPosition() == 1){
                    Toast.makeText(root.getContext(), "Tab 1 Selected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (episodeTab.getSelectedTabPosition() == 0){
            EpisodeFragment episodeFragment = new EpisodeFragment();

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.tab_fragment_container, episodeFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        return root;
    }
}