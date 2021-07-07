package com.example.moviestreamingnew;

public class CardImageChild {
    private String url;
    private String path;
    private String backdrop_url;
    private String movie_id;

    public CardImageChild(String url, String path) {
        this.url = url;
        this.path = path;
    }

    public CardImageChild(String url, String path, String backdrop_url, String movie_id) {
        this.url = url;
        this.path = path;
        this.backdrop_url = backdrop_url;
        this.movie_id = movie_id;
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

    public String getBackdrop_url() {
        return backdrop_url;
    }

    public void setBackdrop_url(String backdrop_url) {
        this.backdrop_url = backdrop_url;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }
}
