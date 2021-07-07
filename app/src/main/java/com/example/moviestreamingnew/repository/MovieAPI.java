package com.example.moviestreamingnew.repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.room.ColumnInfo;

import com.example.moviestreamingnew.CardImageChild;
import com.example.moviestreamingnew.models.Movie;
import com.example.moviestreamingnew.ui.movie_description.MovieDescriptionFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieAPI {
    private int random_page;
    private String genre;

    public MovieAPI(){
    }

    public MovieAPI(String genre){
        this.genre = genre;
    }

    public ArrayList<CardImageChild> getMoviesByGenre(){
        genre.replace(" ", "%20");

        //setRandomPage(genre);

        switch (genre){
            case "Action": genre = "28";
            break;

            case "Comedy": genre = "35";
                break;

            case "Crime": genre = "80";
                break;

            case "Drama": genre = "18";
                break;

            case "Horror": genre = "27";
                break;

            case "Mystery": genre = "9648";
                break;

            case "Science Fiction": genre = "878";
                break;

            case "Thriller": genre = "53";
                break;
        }

        ArrayList<CardImageChild> movieImages = new ArrayList<>();

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                /*Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/search/movie?api_key=b0e77d10e994a9da4f7336d4efc50bc3&query=" + genre + "&page=1&include_adult=false&region=India")
                        .get()
                        .build();*/

                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/discover/movie?api_key=b0e77d10e994a9da4f7336d4efc50bc3&language=en-US&include_adult=false&include_video=false&page=1&with_genres=" + genre)
                        .get()
                        .build();

                Log.d("Random Page: ", "" + random_page);

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    String jsonData = response.body().string();

                    JSONObject Jobject = new JSONObject(jsonData);

                    Log.d("MovieFragment", "Response: " + Jobject.toString());

                    JSONArray results = (JSONArray) Jobject.get("results");

                    Log.d("MovieAPI results: ", "" + results.toString());

                    int y = 0;

                    while (movieImages.size() != 20){
                        if (results.getJSONObject(y).get("backdrop_path").toString().equals(null) && results.getJSONObject(y).get("poster_path").toString().equals(null))
                            y = y + 1;

                        else {
                            CardImageChild temp = new CardImageChild("http://image.tmdb.org/t/p/w500" + results.getJSONObject(y).get("poster_path").toString(), results.getJSONObject(y).get("original_title").toString(), "http://image.tmdb.org/t/p/w500" + results.getJSONObject(y).get("backdrop_path").toString(), results.getJSONObject(y).get("id").toString());
                            movieImages.add(temp);
                            y = y + 1;
                        }
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        return movieImages;
    }

    /*public void setRandomPage(String genre){
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/search/movie?api_key=b0e77d10e994a9da4f7336d4efc50bc3&query=" + genre + "&page=1&include_adult=false")
                        .get()
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    String jsonData = response.body().string();

                    JSONObject Jobject = new JSONObject(jsonData);

                    Log.d("MovieAPI", "Response: " + Jobject.toString());
                    Log.d("MovieAPI", "Total Responses: " + Jobject.get("total_pages"));

                    random_page = ThreadLocalRandom.current().nextInt(1, Integer.parseInt(Jobject.get("total_pages").toString()) + 1);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }*/

    public Movie getMovieById(String id, Context context){
        Movie movie = Movie.getInstance();

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/movie/" + id + "?api_key=b0e77d10e994a9da4f7336d4efc50bc3&language=en-US")
                        .get()
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    String jsonData = response.body().string();

                    JSONObject Jobject = new JSONObject(jsonData);

                    /*Movie.getInstance().setBackdrop_path("http://image.tmdb.org/t/p/w500" + Jobject.get("backdrop_path").toString());
                    Movie.getInstance().setPoster_path("http://image.tmdb.org/t/p/w500" + Jobject.get("poster_path").toString());
                    Movie.getInstance().setTitle(Jobject.get("original_title").toString());
                    Movie.getInstance().setDescription(Jobject.get("overview").toString());
                    Movie.getInstance().setRating(Jobject.get("vote_average").toString());*/

                    movie.setBackdrop_path("http://image.tmdb.org/t/p/w500" + Jobject.get("backdrop_path").toString());
                    movie.setPoster_path("http://image.tmdb.org/t/p/w500" + Jobject.get("poster_path").toString());
                    movie.setTitle(Jobject.get("original_title").toString());
                    movie.setDescription(Jobject.get("overview").toString());
                    movie.setRating(Jobject.get("vote_average").toString());

                    //Log.d("MovieFragment", "Response: " + Jobject.toString());

                    Intent intent = new Intent(context, MovieDescriptionFragment.class);
                    intent.putExtra("MovieData", movie);
                    context.startActivity(intent);

                    progressDialog.dismiss();

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        return movie;
    }

    public void getMovieWatchProviders(String movie_id){
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                /*Request request = new Request.Builder()
                        .url("https://movie-database-imdb-alternative.p.rapidapi.com/?s=" + name + "&page=1&type=movie&r=xml")
                        .get()
                        .addHeader("x-rapidapi-key", "e6cf8faa59mshd1806bce3d21f41p191fd3jsn90db1bb7ebc7")
                        .addHeader("x-rapidapi-host", "movie-database-imdb-alternative.p.rapidapi.com")
                        .build();*/

                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/movie/" + movie_id + "/watch/providers?api_key=b0e77d10e994a9da4f7336d4efc50bc3")
                        .get()
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    String jsonData = response.body().string();

                    JSONObject Jobject = new JSONObject(jsonData);
                    //JSONObject res = Jobject.getJSONObject("Response");

                    //Movie.getInstance()

                    Log.d("MovieFragment", "Watch Providers: " + Jobject.toString());

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }
}
