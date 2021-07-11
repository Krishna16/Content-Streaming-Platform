package com.example.moviestreamingnew.ui.movie;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviestreamingnew.CardImageChild;
import com.example.moviestreamingnew.ShowWithGenreParent;
import com.example.moviestreamingnew.repository.MovieAPI;
import com.example.moviestreamingnew.repository.Storage;

import java.util.ArrayList;

public class MovieViewModel extends ViewModel {
    private ArrayList<CardImageChild> images1;
    private ArrayList<CardImageChild> images2;
    private ArrayList<CardImageChild> images3;

    private ArrayList<CardImageChild> upcoming;
    private ArrayList<CardImageChild> trending;

    private MutableLiveData<ArrayList<ShowWithGenreParent>> showWithGenreParent;
    private Storage storage;

    public MovieViewModel() {
        storage = Storage.getInstance();

        images1 = new ArrayList<>();
        images2 = new ArrayList<>();
        images3 = new ArrayList<>();

        upcoming = new ArrayList<>();
        trending = new ArrayList<>();

        showWithGenreParent = new MutableLiveData<>();
    }

    public void setImages1(String genre) {
        //images1 = storage.downloadMovieImages(genre);
        MovieAPI movieAPI = new MovieAPI(genre);

        images1 = movieAPI.getMoviesByGenre();

        Log.d("SetImages1", "Genre: " + genre);

        //movieAPI.getMoviesByGenre(genre);
    }

    public void setImages2(String genre) {
        //images2 = storage.downloadMovieImages(genre);
        MovieAPI movieAPI = new MovieAPI(genre);

        images2 = movieAPI.getMoviesByGenre();

        Log.d("SetImages1", "Genre: " + genre);
    }

    public void setImages3(String genre) {
        //images3 = storage.downloadMovieImages(genre);
        MovieAPI movieAPI = new MovieAPI(genre);

        images3 = movieAPI.getMoviesByGenre();

        Log.d("SetImages1", "Genre: " + genre);
    }

    public void setUpcoming(){
        MovieAPI movieAPI = new MovieAPI();

        upcoming = movieAPI.getUpcomingMovies();
    }

    public void setTrending(){
        MovieAPI movieAPI = new MovieAPI();

        trending = movieAPI.getTrendingMovies();
    }

    public MutableLiveData<ArrayList<ShowWithGenreParent>> getShowWithGenreParent() {
        return showWithGenreParent;
    }

    public void setShowWithGenreParent(String genre1, String genre2, String genre3) {
        ArrayList<ShowWithGenreParent> temp = new ArrayList<>();

        temp.add(new ShowWithGenreParent(genre1, images1));
        temp.add(new ShowWithGenreParent(genre2, images2));
        temp.add(new ShowWithGenreParent(genre3, images3));
        temp.add(new ShowWithGenreParent("Upcoming", upcoming));
        temp.add(new ShowWithGenreParent("Trending", trending));

        Log.d("MovieViewModel", "Images1: " + images1.size());
        Log.d("MovieViewModel", "Images2: " + images2.size());
        Log.d("MovieViewModel", "Images3: " + images3.size());

        this.showWithGenreParent.setValue(temp);
    }
}
