package com.example.moviestreamingnew.ui.movie_description;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.common.ShowsSharedPreferences;
import com.example.moviestreamingnew.models.Movie;
import com.example.moviestreamingnew.models.Show;
import com.example.moviestreamingnew.repository.UserDatabase;
import com.example.moviestreamingnew.repository.YoutubeAPI;
import com.example.moviestreamingnew.ui.description.DescriptionFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MovieDescriptionFragment extends AppCompatActivity {

    private FloatingActionButton trailer_fab;

    private TextView description;
    private TextView rating;
    private TextView title;

    private ImageView poster;
    private ImageView backdrop;

    private Button like;
    private Button watchLater;

    private RecyclerView platforms;

    private Movie movie;

    private UserDatabase userDatabase;

    private FirebaseAuth mAuth;

    private ShowsSharedPreferences showsSharedPreferences;

    public MovieDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_movie_description);

        trailer_fab = findViewById(R.id.trailer_fab);
        trailer_fab.bringToFront();

        description = findViewById(R.id.movie_description);
        rating = findViewById(R.id.movie_rating);
        title = findViewById(R.id.movie_title);

        poster = findViewById(R.id.movie_poster);
        backdrop = findViewById(R.id.backdrop_image);

        like = findViewById(R.id.like_button);
        watchLater = findViewById(R.id.watch_later);

        platforms = findViewById(R.id.platform_icons_recyclerView);

        movie = (Movie) getIntent().getSerializableExtra("MovieData");

        userDatabase = new UserDatabase(this);

        mAuth = FirebaseAuth.getInstance();

        showsSharedPreferences = new ShowsSharedPreferences(this);

        if (showsSharedPreferences.hasUserWatchLaterMovie(mAuth.getCurrentUser().getUid(), Movie.getInstance().getTitle())){
            watchLater.setBackgroundResource(R.drawable.watch_later_selected);
        }

        if (showsSharedPreferences.hasUserLikedMovie(mAuth.getCurrentUser().getUid(), Movie.getInstance().getTitle())){
            like.setBackgroundResource(R.drawable.like);
        }

        setMovieDetails();

        trailer_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();

                        YoutubeAPI youtubeAPI = new YoutubeAPI();
                        try {
                            youtubeAPI.getYoutubeVideoId(movie.getTitle());
                        } catch (GeneralSecurityException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like.getBackground().getConstantState().equals(ContextCompat.getDrawable(MovieDescriptionFragment.this, R.drawable.unlike).getConstantState())){
                    userDatabase.addLikedMovie(Movie.getInstance().getTitle());
                    like.setBackgroundResource(R.drawable.like);
                    Toast.makeText(MovieDescriptionFragment.this, "Like!!", Toast.LENGTH_SHORT).show();
                    showsSharedPreferences.addToLikedMovie(mAuth.getCurrentUser().getUid(), Movie.getInstance().getTitle());
                }

                else if (like.getBackground().getConstantState().equals(ContextCompat.getDrawable(MovieDescriptionFragment.this, R.drawable.like).getConstantState())){
                    userDatabase.removeLikedMovie(Movie.getInstance().getTitle());
                    like.setBackgroundResource(R.drawable.unlike);
                    Toast.makeText(MovieDescriptionFragment.this, "Unlike!!", Toast.LENGTH_SHORT).show();
                    showsSharedPreferences.removeFromLikedMovie(mAuth.getCurrentUser().getUid(), Movie.getInstance().getTitle());
                }
            }
        });

        watchLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (watchLater.getBackground().getConstantState().equals(ContextCompat.getDrawable(MovieDescriptionFragment.this, R.drawable.watch_later_unselected).getConstantState())) {
                    userDatabase.addWatchLaterMovie(Movie.getInstance().getTitle());
                    watchLater.setBackgroundResource(R.drawable.watch_later_selected);
                    Toast.makeText(MovieDescriptionFragment.this, "Added to watch later successfully!!", Toast.LENGTH_SHORT).show();
                    showsSharedPreferences.addToWatchLaterMovie(mAuth.getCurrentUser().getUid(), Movie.getInstance().getTitle());
                }

                else if (watchLater.getBackground().getConstantState().equals(ContextCompat.getDrawable(MovieDescriptionFragment.this, R.drawable.watch_later_selected).getConstantState())){
                    userDatabase.removeWatchLaterMovie(Movie.getInstance().getTitle());
                    watchLater.setBackgroundResource(R.drawable.watch_later_unselected);
                    Toast.makeText(MovieDescriptionFragment.this, "Removed from watch later successfully!!", Toast.LENGTH_SHORT).show();
                    showsSharedPreferences.removeFromWatchLaterMovie(mAuth.getCurrentUser().getUid(), Movie.getInstance().getTitle());
                }
            }
        });
    }

    public void setMovieDetails(){
        description.setText(Movie.getInstance().getDescription());

        Glide.with(this)
                .load(movie.getBackdrop_path())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(backdrop);

        Glide.with(this)
                .load(movie.getPoster_path())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(poster);

        rating.setText("Rating: " + movie.getRating());

        title.setText(movie.getTitle());
    }
}