package com.example.moviestreamingnew.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviestreamingnew.CardImageChild;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<CardImageChild> images1;
    private MutableLiveData<CardImageChild> images2;
    private MutableLiveData<CardImageChild> images3;

    public MutableLiveData<CardImageChild> getImages1() {
        return images1;
    }

    public void setImages1(MutableLiveData<CardImageChild> images1) {
        this.images1 = images1;
    }

    public MutableLiveData<CardImageChild> getImages2() {
        return images2;
    }

    public void setImages2(MutableLiveData<CardImageChild> images2) {
        this.images2 = images2;
    }

    public MutableLiveData<CardImageChild> getImages3() {
        return images3;
    }

    public void setImages3(MutableLiveData<CardImageChild> images3) {
        this.images3 = images3;
    }
}
