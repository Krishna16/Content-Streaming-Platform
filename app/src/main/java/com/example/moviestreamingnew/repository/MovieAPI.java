package com.example.moviestreamingnew.repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.moviestreamingnew.CardImageChild;
import com.example.moviestreamingnew.models.Movie;
import com.example.moviestreamingnew.ui.movie_description.MovieDescriptionFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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

                    movie.setBackdrop_path("http://image.tmdb.org/t/p/w500" + Jobject.get("backdrop_path").toString());
                    movie.setPoster_path("http://image.tmdb.org/t/p/w500" + Jobject.get("poster_path").toString());
                    movie.setTitle(Jobject.get("original_title").toString());
                    movie.setDescription(Jobject.get("overview").toString());
                    movie.setRating(Jobject.get("vote_average").toString());

                    JSONArray genres = Jobject.getJSONArray("genres");

                    Movie.getInstance().getGenres().clear();

                    //Log.d("MovieAPI", "Genres are: " + genres.toString());
                    //Log.d("MovieAPI", "Genres length: " + genres.length());

                    for (int i = 0; i < genres.length(); i++){
                        movie.setGenres(genres.getJSONObject(i).get("name").toString());
                    }

                    getMovieWatchProviders(id);

                    Log.d("MovieAPI GetMovieById", "Response: " + Jobject.toString());

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

    public ArrayList<String> getMovieWatchProviders(String movie_id){
        ArrayList<String> watchProviders = new ArrayList<>();

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/movie/" + movie_id + "/watch/providers?api_key=b0e77d10e994a9da4f7336d4efc50bc3")
                        .get()
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    String jsonData = response.body().string();

                    JSONObject Jobject = new JSONObject(jsonData);

                    JSONObject results = Jobject.getJSONObject("results");

                    Log.d("MovieAPI", "Watch Providers: " + results.toString());

                    Movie.getInstance().getWatchProviders().clear();

                    JSONObject in_results = results.getJSONObject("IN");
                    Movie.getInstance().setAvailableInCountry(true);
                    JSONArray flatrate = in_results.getJSONArray("flatrate");

                    Movie.getInstance().setWatchProviders(flatrate.getJSONObject(0).get("provider_name").toString());

                    Log.d("MovieAPI", "Watch Providers: " + in_results.toString());
                    Log.d("MovieAPI", "Watch Providers ArrayList size: " + Movie.getInstance().getWatchProviders().size());

                } catch (IOException | JSONException e) {
                    Movie.getInstance().setAvailableInCountry(false);
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        return watchProviders;
    }

    public ArrayList<CardImageChild> getUpcomingMovies(){
        ArrayList<CardImageChild> upcoming = new ArrayList<>();

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/movie/upcoming?api_key=b0e77d10e994a9da4f7336d4efc50bc3&language=en-US&page=1")
                        .get()
                        .build();

                Response response = null;
                try{
                    response = client.newCall(request).execute();
                    String jsonData = response.body().string();

                    JSONObject Jobject = new JSONObject(jsonData);

                    JSONArray results = Jobject.getJSONArray("results");

                    int y = 0;

                    while (upcoming.size() != 20){
                        if (results.getJSONObject(y).get("backdrop_path").toString().equals(null) && results.getJSONObject(y).get("poster_path").toString().equals(null))
                            y = y + 1;

                        else {
                            CardImageChild temp = new CardImageChild("http://image.tmdb.org/t/p/w500" + results.getJSONObject(y).get("poster_path").toString(), results.getJSONObject(y).get("original_title").toString(), "http://image.tmdb.org/t/p/w500" + results.getJSONObject(y).get("backdrop_path").toString(), results.getJSONObject(y).get("id").toString());
                            upcoming.add(temp);
                            y = y + 1;
                        }
                    }
                }

                catch (IOException | JSONException e){
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        return upcoming;
    }

    public ArrayList<CardImageChild> getTrendingMovies(){
        ArrayList<CardImageChild> trending = new ArrayList<>();

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/trending/movie/day?api_key=b0e77d10e994a9da4f7336d4efc50bc3")
                        .get()
                        .build();

                Response response = null;
                try{
                    response = client.newCall(request).execute();
                    String jsonData = response.body().string();

                    JSONObject Jobject = new JSONObject(jsonData);

                    JSONArray results = Jobject.getJSONArray("results");

                    Log.d("MovieAPI Trending: ", "Results: " + results.toString());

                    int y = 0;

                    while (trending.size() != 20){
                        if (results.getJSONObject(y).get("backdrop_path").toString().equals(null) && results.getJSONObject(y).get("poster_path").toString().equals(null))
                            y = y + 1;

                        else {
                            CardImageChild temp = new CardImageChild("http://image.tmdb.org/t/p/w500" + results.getJSONObject(y).get("poster_path").toString(), results.getJSONObject(y).get("original_title").toString(), "http://image.tmdb.org/t/p/w500" + results.getJSONObject(y).get("backdrop_path").toString(), results.getJSONObject(y).get("id").toString());
                            trending.add(temp);
                            y = y + 1;
                        }
                    }
                }

                catch (IOException | JSONException e){
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        return trending;
    }

    public ArrayList<CardImageChild> getTVContent(){
        ArrayList<CardImageChild> movieImages = new ArrayList<>();

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/discover/tv?api_key=b0e77d10e994a9da4f7336d4efc50bc3&language=en-US&sort_by=popularity.desc&page=1&include_null_first_air_dates=false")
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

                    while (movieImages.size() != 30){
                        if (results.getJSONObject(y).get("backdrop_path").toString().equals(null) && results.getJSONObject(y).get("poster_path").toString().equals(null))
                            y = y + 1;

                        else {
                            CardImageChild temp = new CardImageChild("http://image.tmdb.org/t/p/w500" + results.getJSONObject(y).get("poster_path").toString(), results.getJSONObject(y).get("name").toString(), "http://image.tmdb.org/t/p/w500" + results.getJSONObject(y).get("backdrop_path").toString(), results.getJSONObject(y).get("id").toString());
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

    public ArrayList<CardImageChild> getMovieContent(){
        ArrayList<CardImageChild> movieImages = new ArrayList<>();

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/discover/movie?api_key=b0e77d10e994a9da4f7336d4efc50bc3&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=2")
                        .get()
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    String jsonData = response.body().string();

                    JSONObject Jobject = new JSONObject(jsonData);

                    JSONArray results = (JSONArray) Jobject.get("results");

                    int y = 0;

                    while (movieImages.size() != 30){
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

    public ArrayList<CardImageChild> getBollywoodMovieContent(){
        ArrayList<CardImageChild> movieImages = new ArrayList<>();

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/discover/movie?api_key=b0e77d10e994a9da4f7336d4efc50bc3&region=IN&language=hi&sort_by=popularity.desc&with_release_type=3|2")
                        .get()
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    String jsonData = response.body().string();

                    JSONObject Jobject = new JSONObject(jsonData);

                    JSONArray results = (JSONArray) Jobject.get("results");

                    int y = 0;

                    while (movieImages.size() != 30){
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

    public ArrayList<CardImageChild> getQueriedContent(String query){
        ArrayList<CardImageChild> movieImages = new ArrayList<>();

        if (query.contains(" ")){
            query = query.replace(" ", "%20");
        }

        String finalQuery = query;

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://api.themoviedb.org/3/search/multi?api_key=b0e77d10e994a9da4f7336d4efc50bc3&query=" + finalQuery + "&page=1&include_adult=true")
                        .get()
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    String jsonData = response.body().string();

                    JSONObject Jobject = new JSONObject(jsonData);

                    JSONArray results = (JSONArray) Jobject.get("results");

                    Log.d("MovieAPI results: ", "" + results.toString());

                    int y = 0;

                    while (movieImages.size() != 30){
                        if (results.getJSONObject(y).get("backdrop_path").toString().equals(null) && results.getJSONObject(y).get("poster_path").toString().equals(null))
                            y = y + 1;

                        else {
                            try {
                                CardImageChild temp = new CardImageChild("http://image.tmdb.org/t/p/w500" + results.getJSONObject(y).get("poster_path").toString(), results.getJSONObject(y).get("name").toString(), "http://image.tmdb.org/t/p/w500" + results.getJSONObject(y).get("backdrop_path").toString(), results.getJSONObject(y).get("id").toString(), results.getJSONObject(y).get("media_type").toString());
                                movieImages.add(temp);
                                y = y + 1;
                            }

                            catch (Exception e){
                                CardImageChild temp = new CardImageChild("http://image.tmdb.org/t/p/w500" + results.getJSONObject(y).get("poster_path").toString(), results.getJSONObject(y).get("original_title").toString(), "http://image.tmdb.org/t/p/w500" + results.getJSONObject(y).get("backdrop_path").toString(), results.getJSONObject(y).get("id").toString(), results.getJSONObject(y).get("media_type").toString());
                                movieImages.add(temp);
                                y = y + 1;
                            }
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

    public Movie getContentById(String id, Context context, String type){
        Movie movie = Movie.getInstance();

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                Request request = null;

                if (type.equals("movie")) {
                    request = new Request.Builder()
                            .url("https://api.themoviedb.org/3/movie/" + id + "?api_key=b0e77d10e994a9da4f7336d4efc50bc3&language=en-US")
                            .get()
                            .build();
                }

                else if (type.equals("tv")){
                    request = new Request.Builder()
                            .url("https://api.themoviedb.org/3/tv/" + id + "?api_key=b0e77d10e994a9da4f7336d4efc50bc3&language=en-US")
                            .get()
                            .build();
                }

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    String jsonData = response.body().string();

                    JSONObject Jobject = new JSONObject(jsonData);

                    if (type.equals("movie")) {
                        movie.setBackdrop_path("http://image.tmdb.org/t/p/w500" + Jobject.get("backdrop_path").toString());
                        movie.setPoster_path("http://image.tmdb.org/t/p/w500" + Jobject.get("poster_path").toString());
                        movie.setTitle(Jobject.get("original_title").toString());
                        movie.setDescription(Jobject.get("overview").toString());
                        movie.setRating(Jobject.get("vote_average").toString());

                        JSONArray genres = Jobject.getJSONArray("genres");

                        Movie.getInstance().getGenres().clear();

                        //Log.d("MovieAPI", "Genres are: " + genres.toString());
                        //Log.d("MovieAPI", "Genres length: " + genres.length());

                        for (int i = 0; i < genres.length(); i++) {
                            movie.setGenres(genres.getJSONObject(i).get("name").toString());
                        }

                        getMovieWatchProviders(id);
                    }

                    else if (type.equals("tv")){
                        movie.setBackdrop_path("http://image.tmdb.org/t/p/w500" + Jobject.get("backdrop_path").toString());
                        movie.setPoster_path("http://image.tmdb.org/t/p/w500" + Jobject.get("poster_path").toString());
                        movie.setTitle(Jobject.get("original_name").toString());
                        movie.setDescription(Jobject.get("overview").toString());
                        movie.setRating(Jobject.get("vote_average").toString());

                        JSONArray genres = Jobject.getJSONArray("genres");

                        Movie.getInstance().getGenres().clear();

                        //Log.d("MovieAPI", "Genres are: " + genres.toString());
                        //Log.d("MovieAPI", "Genres length: " + genres.length());

                        for (int i = 0; i < genres.length(); i++) {
                            movie.setGenres(genres.getJSONObject(i).get("name").toString());
                        }

                        getMovieWatchProviders(id);
                    }

                    Log.d("MovieAPI GetMovieById", "Response: " + Jobject.toString());

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
}
