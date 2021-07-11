package com.example.moviestreamingnew.ui.movie_description;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.adapters.PlatformAdapter;
import com.example.moviestreamingnew.common.ShowsSharedPreferences;
import com.example.moviestreamingnew.homepage_recycler_adapters.MovieImageAdapter;
import com.example.moviestreamingnew.models.Movie;
import com.example.moviestreamingnew.models.Show;
import com.example.moviestreamingnew.repository.UserDatabase;
import com.example.moviestreamingnew.repository.YoutubeAPI;
import com.example.moviestreamingnew.ui.description.DescriptionFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MovieDescriptionFragment extends AppCompatActivity {

    private FloatingActionButton trailer_fab;

    private TextView description;
    private TextView rating;
    private TextView title;
    private TextView genres;
    private TextView notAvailableInCountry;

    private ImageView poster;
    private ImageView backdrop;

    private Button like;
    private Button watchLater;

    private RecyclerView platforms;

    private Movie movie;

    private UserDatabase userDatabase;

    private FirebaseAuth mAuth;

    private ShowsSharedPreferences showsSharedPreferences;

    private PlatformAdapter platformAdapter;
    private LinearLayoutManager linearLayoutManager;

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
        genres = findViewById(R.id.movie_genres);
        genres.setText("");
        notAvailableInCountry = findViewById(R.id.not_available);

        poster = findViewById(R.id.movie_poster);
        backdrop = findViewById(R.id.backdrop_image);

        like = findViewById(R.id.like_button);
        watchLater = findViewById(R.id.watch_later);

        platforms = findViewById(R.id.platform_icons_recyclerView);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

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

        genres.clearComposingText();
        genres.setText("Genres: ");
        for (int i = 0; i < movie.getGenres().size(); i++){
            if (movie.getGenres().size() != 1)
                genres.append(movie.getGenres().get(i) + ", ");

            else if (i == movie.getGenres().size() - 1){
                genres.append(movie.getGenres().get(i));
            }

            else
                genres.append(movie.getGenres().get(i));
        }

        if (movie.getGenres().size() > 1) {
            int lastComma = genres.getText().toString().lastIndexOf(",");
            StringBuilder sb = new StringBuilder(genres.getText().toString());
            sb.deleteCharAt(genres.getText().toString().lastIndexOf(","));
        }

        Log.d("MovieAPI", "Available in country: " + Movie.getInstance().isAvailableInCountry());
        if (!Movie.getInstance().isAvailableInCountry()){
            notAvailableInCountry.setVisibility(View.VISIBLE);
            platforms.setVisibility(View.GONE);
        }

        else{
            notAvailableInCountry.setVisibility(View.GONE);
            platforms.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Movie.getInstance().isAvailableInCountry()){
                    platformAdapter = new PlatformAdapter(Movie.getInstance().getWatchProviders());

                    platforms.setLayoutManager(new LinearLayoutManager(MovieDescriptionFragment.this));
                    platforms.setAdapter(platformAdapter);
                    platforms.setHasFixedSize(true);

                    platformAdapter.notifyDataSetChanged();
                }
            }
        }, 1500);
    }
}