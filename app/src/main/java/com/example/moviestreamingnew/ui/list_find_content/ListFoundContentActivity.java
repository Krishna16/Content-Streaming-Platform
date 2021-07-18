package com.example.moviestreamingnew.ui.list_find_content;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
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
    }

    public void setRecyclerView(){
        listContentRecyclerView = findViewById(R.id.list_content_recyclerView);
        gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        listContentRecyclerView.setLayoutManager(gridLayoutManager);

        listFoundContentAdapter = new ListFoundContentAdapter(moviesByGenre, this);

        listContentRecyclerView.setAdapter(listFoundContentAdapter);
    }
}