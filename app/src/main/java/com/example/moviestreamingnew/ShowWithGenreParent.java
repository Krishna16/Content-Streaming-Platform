package com.example.moviestreamingnew;

import java.util.ArrayList;

public class ShowWithGenreParent {
    private String title;
    private ArrayList<CardImageChild> cards;

    public ShowWithGenreParent(String title, ArrayList<CardImageChild> cards) {
        this.title = title;
        this.cards = cards;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<CardImageChild> getCards() {
        return cards;
    }

    public void setCards(ArrayList<CardImageChild> cards) {
        this.cards = cards;
    }
}
