package com.example.moviestreamingnew.ui.description;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.models.Show;
import com.example.moviestreamingnew.repository.ShowsDatabase;
import com.example.moviestreamingnew.repository.UserDatabase;
import com.example.moviestreamingnew.ui.episodes.EpisodeFragment;
import com.example.moviestreamingnew.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

    private ImageView image;
    private String imageUrl;
    private TextView title, rating, likes;

    private String show, industry, genre;

    private Button likeButton, watchLater;

    private UserDatabase userDatabase;
    private ShowsDatabase showsDatabase;

    public DescriptionFragment() {
        // Required empty public constructor
    }

    public DescriptionFragment(String imageUrl, String show, String industry, String genre){
        this.imageUrl = imageUrl;
        this.show = show;
        this.industry = industry;
        this.genre = genre;
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
        this.image = root.findViewById(R.id.show_image);
        this.title = root.findViewById(R.id.show_title);
        this.rating = root.findViewById(R.id.show_rating);
        this.likes = root.findViewById(R.id.number_of_likes);

        this.watchLater = root.findViewById(R.id.watch_later);
        this.likeButton = root.findViewById(R.id.like_button);

        this.userDatabase = new UserDatabase(root.getContext());
        this.showsDatabase = new ShowsDatabase(root.getContext());

        Glide.with(root.getContext())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(image);

        description.setText(Show.getInstance().getDescription());
        title.setText(Show.getInstance().getName());
        rating.setText("Rating: " + Show.getInstance().getRating() + "/10");
        likes.setText("" + Show.getInstance().getLikes());

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = description.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                description.setLayoutParams(params);
            }
        });

        this.episodeTab = root.findViewById(R.id.episode_tab);
        episodeTab.addTab(episodeTab.newTab().setText("Episodes(" + Show.getInstance().getEpisodes() + ")"));
        episodeTab.addTab(episodeTab.newTab().setText("More Details"));
        episodeTab.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

        episodeTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    EpisodeFragment episodeFragment = new EpisodeFragment(show, industry, genre);

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.tab_fragment_container, episodeFragment);
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
            EpisodeFragment episodeFragment = new EpisodeFragment(show, industry, genre);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.tab_fragment_container, episodeFragment);
            transaction.commit();
        }

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);

        watchLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (watchLater.getBackground() == ContextCompat.getDrawable(root.getContext(), R.drawable.watch_later_unselected)) {
                    userDatabase.addWatchLater(Show.getInstance().getName());
                    watchLater.setBackgroundResource(R.drawable.watch_later_selected);
                    Toast.makeText(root.getContext(), "Added to watch later successfully!!", Toast.LENGTH_SHORT).show();
                }

                else{
                    userDatabase.removeWatchLater(Show.getInstance().getName());
                    watchLater.setBackgroundResource(R.drawable.watch_later_unselected);
                    Toast.makeText(root.getContext(), "Removed from watch later successfully!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }
}