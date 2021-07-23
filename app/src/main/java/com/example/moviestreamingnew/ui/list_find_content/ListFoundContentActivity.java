package com.example.moviestreamingnew.ui.list_find_content;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.moviestreamingnew.CardImageChild;
import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.adapters.ListFoundContentAdapter;
import com.example.moviestreamingnew.repository.MovieAPI;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ListFoundContentActivity extends AppCompatActivity {

    private String genre;

    private MovieAPI movieAPI;

    private ArrayList<CardImageChild> moviesByGenre;

    private RecyclerView listContentRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private ListFoundContentAdapter listFoundContentAdapter;

    private ExecutorService pool;

    private TextView genreTitle;
    private TextView notFound;

    public ListFoundContentActivity(){

    }

    public ListFoundContentActivity(String genre){
        this.genre = genre;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_found_content);

        genreTitle = findViewById(R.id.genre_title);
        notFound = findViewById(R.id.not_found_textView);

        if (getIntent().hasExtra("genre")){
            genre = getIntent().getStringExtra("genre");
            genreTitle.setText(genre);

            movieAPI = new MovieAPI(genre);

            pool = Executors.newFixedThreadPool(1);
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    moviesByGenre = movieAPI.getMoviesByGenre();
                }
            });

            try {
                pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setRecyclerView();
        }

        else if (getIntent().hasExtra("TV")){
            genre = getIntent().getStringExtra("TV");
            genreTitle.setText(genre);

            movieAPI = new MovieAPI();

            pool = Executors.newFixedThreadPool(1);
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    moviesByGenre = movieAPI.getTVContent();
                }
            });

            try {
                pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setRecyclerView();
        }

        else if (getIntent().hasExtra("Movie")){
            genre = getIntent().getStringExtra("Movie");
            genreTitle.setText(genre);

            movieAPI = new MovieAPI();

            pool = Executors.newFixedThreadPool(1);
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    moviesByGenre = movieAPI.getMovieContent();
                }
            });

            try {
                pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setRecyclerView();
        }

        else if (getIntent().hasExtra("Hollywood")){
            genre = getIntent().getStringExtra("Hollywood");
            genreTitle.setText(genre);

            movieAPI = new MovieAPI();

            pool = Executors.newFixedThreadPool(1);
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    moviesByGenre = movieAPI.getMovieContent();
                }
            });

            try {
                pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setRecyclerView();
        }

        else if (getIntent().hasExtra("Bollywood")){
            genre = getIntent().getStringExtra("Bollywood");
            genreTitle.setText(genre);

            movieAPI = new MovieAPI();

            pool = Executors.newFixedThreadPool(1);
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    moviesByGenre = movieAPI.getBollywoodMovieContent();
                }
            });

            try {
                pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setRecyclerView();
        }

        else if (getIntent().hasExtra("Query")){
            genre = getIntent().getStringExtra("Query");
            genreTitle.setText(genre);

            movieAPI = new MovieAPI();

            pool = Executors.newFixedThreadPool(1);
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    moviesByGenre = movieAPI.getQueriedContent(genre);
                }
            });

            try {
                pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setRecyclerView();
        }
    }

    public void setRecyclerView(){
        listContentRecyclerView = findViewById(R.id.list_content_recyclerView);
        gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        listContentRecyclerView.setLayoutManager(gridLayoutManager);

        if (moviesByGenre.size() == 0){
            notFound.setVisibility(View.VISIBLE);
        }

        else{
            notFound.setVisibility(View.GONE);
        }

        listFoundContentAdapter = new ListFoundContentAdapter(moviesByGenre, this);

        listContentRecyclerView.setAdapter(listFoundContentAdapter);
    }
}