package com.example.moviestreamingnew;

public class CardImageChild {
    private String url;
    private String path;

    public CardImageChild(String url, String path) {
        this.url = url;
        this.path = path;
    }

    public String getImage() {
        return url;
    }

    public void setImage(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
