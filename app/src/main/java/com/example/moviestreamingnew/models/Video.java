package com.example.moviestreamingnew.models;

public class Video {
    private String name;
    private String videoUrl;

    public Video() {
    }

    public Video(String name, String videoUrl) {
        this.name = name;
        this.videoUrl = videoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
