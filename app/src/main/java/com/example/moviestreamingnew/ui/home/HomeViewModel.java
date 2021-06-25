package com.example.moviestreamingnew.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviestreamingnew.CardImageChild;
import com.example.moviestreamingnew.ShowWithGenreParent;
import com.example.moviestreamingnew.repository.Storage;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private ArrayList<CardImageChild> images1;
    private ArrayList<CardImageChild> images2;
    private ArrayList<CardImageChild> images3;

    private MutableLiveData<ArrayList<ShowWithGenreParent>> showWithGenreParent;

    private Storage storage;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        storage = Storage.getInstance();

        images1 = new ArrayList<>();
        images2 = new ArrayList<>();
        images3 = new ArrayList<>();

        showWithGenreParent = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setImages1(String genre) {
        images1 = storage.downloadMovieImages(genre);
    }

    public void setImages2(String genre) {
        images2 = storage.downloadMovieImages(genre);
    }

    public void setImages3(String genre) {
        images3 = storage.downloadMovieImages(genre);
    }

    public MutableLiveData<ArrayList<ShowWithGenreParent>> getShowWithGenreParent() {
        return showWithGenreParent;
    }

    public void setShowWithGenreParent(String genre1, String genre2, String genre3) {
        ArrayList<ShowWithGenreParent> temp = new ArrayList<>();

        temp.add(new ShowWithGenreParent(genre1, images1));
        temp.add(new ShowWithGenreParent(genre2, images2));
        temp.add(new ShowWithGenreParent(genre3, images3));

        this.showWithGenreParent.setValue(temp);
    }
}