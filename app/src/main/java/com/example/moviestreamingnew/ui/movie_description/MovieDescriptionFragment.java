package com.example.moviestreamingnew.ui.movie_description;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.models.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        setMovieDetails();

        trailer_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*YoutubeAPI youtubeAPI = new YoutubeAPI();
                try {
                    youtubeAPI.getYoutubeVideoId(Movie.getInstance().getTitle());
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        watchLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setMovieDetails(){
        Movie movie = (Movie) getIntent().getSerializableExtra("MovieData");
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