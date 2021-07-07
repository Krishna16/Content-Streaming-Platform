package com.example.moviestreamingnew.ui.description;

import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.common.ShowsSharedPreferences;
import com.example.moviestreamingnew.models.Show;
import com.example.moviestreamingnew.repository.ShowsDatabase;
import com.example.moviestreamingnew.repository.UserDatabase;
import com.example.moviestreamingnew.ui.episodes.EpisodeFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;

public class DescriptionFragment extends AppCompatActivity {

    private MaterialTextView description;

    private TabLayout episodeTab;

    private ImageView image;
    private String imageUrl;
    private TextView title, rating, likes;

    private String show, industry, genre;

    private Button likeButton, watchLater;

    private UserDatabase userDatabase;
    private ShowsDatabase showsDatabase;

    private ShowsSharedPreferences showsSharedPreferences;
    private FirebaseAuth mAuth;

    public DescriptionFragment() {
        // Required empty public constructor
    }

    public DescriptionFragment(String imageUrl, String show, String industry, String genre){
        this.imageUrl = imageUrl;
        this.show = show;
        this.industry = industry;
        this.genre = genre;
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_description);

        //this.root = inflater.inflate(R.layout.fragment_description, container, false);
        // Inflate the layout for this fragment

        imageUrl = getIntent().getStringExtra("image");
        show = getIntent().getStringExtra("show");
        genre = getIntent().getStringExtra("genre");
        industry = getIntent().getStringExtra("industry");

        this.description = findViewById(R.id.show_description);
        this.image = findViewById(R.id.show_image);
        this.title = findViewById(R.id.show_title);
        this.rating = findViewById(R.id.show_rating);
        this.likes = findViewById(R.id.number_of_likes);

        this.mAuth = FirebaseAuth.getInstance();

        this.watchLater = findViewById(R.id.watch_later);
        this.likeButton = findViewById(R.id.like_button);

        this.userDatabase = new UserDatabase(this);
        this.showsDatabase = new ShowsDatabase(this);

        this.showsSharedPreferences = new ShowsSharedPreferences(this);

        if (showsSharedPreferences.hasUserWatchLaterShow(mAuth.getCurrentUser().getUid(), Show.getInstance().getName())){
            watchLater.setBackgroundResource(R.drawable.watch_later_selected);
        }

        if (showsSharedPreferences.hasUserLikedShow(mAuth.getCurrentUser().getUid(), Show.getInstance().getName())){
            likeButton.setBackgroundResource(R.drawable.like);
        }

        Glide.with(this)
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

        this.episodeTab = findViewById(R.id.episode_tab);
        episodeTab.addTab(episodeTab.newTab().setText("Episodes(" + Show.getInstance().getEpisodes() + ")"));
        episodeTab.addTab(episodeTab.newTab().setText("More Details"));
        episodeTab.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

        episodeTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    EpisodeFragment episodeFragment = new EpisodeFragment(show, industry, genre);

                    //FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.tab_fragment_container, episodeFragment);
                    transaction.commit();
                }

                if (tab.getPosition() == 1){
                    Toast.makeText(DescriptionFragment.this, "Tab 1 Selected", Toast.LENGTH_SHORT).show();
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

            //FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.tab_fragment_container, episodeFragment);
            transaction.commit();
        }

        /*BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);*/

        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        /*Toolbar toolbar = getActivity().findViewById(R.id.custom_toolbar);
        toolbar.setVisibility(View.GONE);

        TabLayout tabLayout = getActivity().findViewById(R.id.show_movie_tab_layout);
        tabLayout.setVisibility(View.GONE);*/

        watchLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (watchLater.getBackground().getConstantState().equals(ContextCompat.getDrawable(DescriptionFragment.this, R.drawable.watch_later_unselected).getConstantState())) {
                    userDatabase.addWatchLaterShow(Show.getInstance().getName());
                    watchLater.setBackgroundResource(R.drawable.watch_later_selected);
                    Toast.makeText(DescriptionFragment.this, "Added to watch later successfully!!", Toast.LENGTH_SHORT).show();
                    showsSharedPreferences.addToWatchLaterShow(mAuth.getCurrentUser().getUid(), Show.getInstance().getName());
                }

                else if (watchLater.getBackground().getConstantState().equals(ContextCompat.getDrawable(DescriptionFragment.this, R.drawable.watch_later_selected).getConstantState())){
                    userDatabase.removeWatchLaterShow(Show.getInstance().getName());
                    watchLater.setBackgroundResource(R.drawable.watch_later_unselected);
                    Toast.makeText(DescriptionFragment.this, "Removed from watch later successfully!!", Toast.LENGTH_SHORT).show();
                    showsSharedPreferences.removeFromWatchLaterShow(mAuth.getCurrentUser().getUid(), Show.getInstance().getName());
                }
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likeButton.getBackground().getConstantState().equals(ContextCompat.getDrawable(DescriptionFragment.this, R.drawable.unlike).getConstantState())){
                        userDatabase.addLikedShow(Show.getInstance().getName());
                        likeButton.setBackgroundResource(R.drawable.like);
                        int numberOfLikes = Integer.parseInt(likes.getText().toString());
                        numberOfLikes++;
                        showsDatabase.addLikes(show, industry, genre, numberOfLikes);
                        Toast.makeText(DescriptionFragment.this, "Like!!", Toast.LENGTH_SHORT).show();
                        likes.setText("" + numberOfLikes);
                        showsSharedPreferences.addToLikedShow(mAuth.getCurrentUser().getUid(), Show.getInstance().getName());
                    }

                else if (likeButton.getBackground().getConstantState().equals(ContextCompat.getDrawable(DescriptionFragment.this, R.drawable.like).getConstantState())){
                        userDatabase.removeLikedShow(Show.getInstance().getName());
                        likeButton.setBackgroundResource(R.drawable.unlike);
                        int numberOfLikes = Integer.parseInt(likes.getText().toString());
                        numberOfLikes--;
                        showsDatabase.removeLikes(show, industry, genre, numberOfLikes);
                        Toast.makeText(DescriptionFragment.this, "Unlike!!", Toast.LENGTH_SHORT).show();
                        likes.setText("" + numberOfLikes);
                        showsSharedPreferences.removeFromLikedShow(mAuth.getCurrentUser().getUid(), Show.getInstance().getName());
                }
            }
        });
    }

    /*@Override
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

        this.showsSharedPreferences = new ShowsSharedPreferences(root.getContext());

        if (showsSharedPreferences.hasUserWatchLater(mAuth.getCurrentUser().getUid(), Show.getInstance().getName())){
            Log.d("Description Fragment: ", "hasWatchLater: " + hasWatchLater);
            watchLater.setBackgroundResource(R.drawable.watch_later_selected);
        }

        if (showsSharedPreferences.hasUserLiked(mAuth.getCurrentUser().getUid(), Show.getInstance().getName())){
            Log.d("Description Fragment: ", "hasLiked: " + hasLiked);
            likeButton.setBackgroundResource(R.drawable.like);
        }

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

        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        Toolbar toolbar = getActivity().findViewById(R.id.custom_toolbar);
        toolbar.setVisibility(View.GONE);

        TabLayout tabLayout = getActivity().findViewById(R.id.show_movie_tab_layout);
        tabLayout.setVisibility(View.GONE);

        watchLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (watchLater.getBackground().getConstantState().equals(ContextCompat.getDrawable(root.getContext(), R.drawable.watch_later_unselected).getConstantState())) {
                    userDatabase.addWatchLater(Show.getInstance().getName());
                    watchLater.setBackgroundResource(R.drawable.watch_later_selected);
                    Toast.makeText(root.getContext(), "Added to watch later successfully!!", Toast.LENGTH_SHORT).show();
                    showsSharedPreferences.addToWatchLater(mAuth.getCurrentUser().getUid(), Show.getInstance().getName());
                }

                else if (watchLater.getBackground().getConstantState().equals(ContextCompat.getDrawable(root.getContext(), R.drawable.watch_later_selected).getConstantState())){
                    userDatabase.removeWatchLater(Show.getInstance().getName());
                    watchLater.setBackgroundResource(R.drawable.watch_later_unselected);
                    Toast.makeText(root.getContext(), "Removed from watch later successfully!!", Toast.LENGTH_SHORT).show();
                    showsSharedPreferences.removeFromWatchLater(mAuth.getCurrentUser().getUid(), Show.getInstance().getName());
                }
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likeButton.getBackground().getConstantState().equals(ContextCompat.getDrawable(root.getContext(), R.drawable.unlike).getConstantState())){
                    userDatabase.addLikedShow(Show.getInstance().getName());
                    likeButton.setBackgroundResource(R.drawable.like);
                    int numberOfLikes = Integer.parseInt(likes.getText().toString());
                    numberOfLikes++;
                    showsDatabase.addLikes(show, industry, genre, numberOfLikes);
                    Toast.makeText(root.getContext(), "Like!!", Toast.LENGTH_SHORT).show();
                    likes.setText("" + numberOfLikes);
                    showsSharedPreferences.addToLiked(mAuth.getCurrentUser().getUid(), Show.getInstance().getName());
                }

                else if (likeButton.getBackground().getConstantState().equals(ContextCompat.getDrawable(root.getContext(), R.drawable.like).getConstantState())){
                    userDatabase.removeLikedShow(Show.getInstance().getName());
                    likeButton.setBackgroundResource(R.drawable.unlike);
                    int numberOfLikes = Integer.parseInt(likes.getText().toString());
                    numberOfLikes--;
                    showsDatabase.removeLikes(show, industry, genre, numberOfLikes);
                    Toast.makeText(root.getContext(), "Unlike!!", Toast.LENGTH_SHORT).show();
                    likes.setText("" + numberOfLikes);
                    showsSharedPreferences.removeFromLiked(mAuth.getCurrentUser().getUid(), Show.getInstance().getName());
                }
            }
        });

        return root;
    }*/

    @Override
    public void onStart() {
        super.onStart();
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }
}