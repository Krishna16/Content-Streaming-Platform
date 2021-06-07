package com.example.moviestreamingnew.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviestreamingnew.CardImageChild;
import com.example.moviestreamingnew.repository.Storage;

import java.util.ArrayList;

public class ImagesViewModel extends ViewModel {
    private MutableLiveData<ArrayList<CardImageChild>> imageLiveData;
    private ArrayList<CardImageChild> superheroImages;
    private ArrayList<CardImageChild> comedyImages;
    private ArrayList<CardImageChild> scienceFictionImages;
    private Storage storage;

    public ImagesViewModel(){
        this.imageLiveData = new MutableLiveData<>();
        this.storage = new Storage();

        this.superheroImages = new ArrayList<>();
        this.comedyImages = new ArrayList<>();
        this.scienceFictionImages = new ArrayList<>();
    }

    private ArrayList<CardImageChild> populateSuperheroList() {
        this.superheroImages = storage.downloadSuperheroMovieImages();

        return superheroImages;
    }

    private ArrayList<CardImageChild> populateComedyList() {
        this.comedyImages = storage.downloadComedyMovieImages();

        return comedyImages;
    }

    private ArrayList<CardImageChild> populateScienceFictionList() {
        this.scienceFictionImages = storage.downloadScienceFictionImages();

        return scienceFictionImages;
    }
}
