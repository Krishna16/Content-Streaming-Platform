package com.example.moviestreamingnew.repository;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.moviestreamingnew.CardImageChild;
import com.example.moviestreamingnew.models.Video;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Storage {
    private FirebaseStorage firebaseStorage;
    private final int ONE_MEG = 1024 * 1024 * 5;
    private ArrayList<CardImageChild> superheroImages;
    private ArrayList<CardImageChild> comedyImages;
    private ArrayList<CardImageChild> scienceFictionImages;
    private Context context;
    private ArrayList<String> genres;

    private static Storage storage;

    public Storage(){
        this.firebaseStorage = FirebaseStorage.getInstance();
        this.superheroImages = new ArrayList<>();
        this.comedyImages = new ArrayList<>();
        this.scienceFictionImages = new ArrayList<>();
        this.genres = new ArrayList<>();
    }

    public Storage(Context context){
        this.firebaseStorage = FirebaseStorage.getInstance();
        this.superheroImages = new ArrayList<>();
        this.comedyImages = new ArrayList<>();
        this.scienceFictionImages = new ArrayList<>();
        this.context = context;
    }

    public static synchronized Storage getInstance(){
        if (storage == null){
            storage = new Storage();
        }

        return storage;
    }

    public ArrayList<CardImageChild> downloadHollywoodSuperheroMovieImages(){
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference superhero = storageReference.child("images/Superhero/Hollywood");

        superhero.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item: listResult.getItems()){
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            CardImageChild cardTemp = new CardImageChild(uri.toString(), uri.getPath());
                            superheroImages.add(cardTemp);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        return superheroImages;
    }

    public ArrayList<CardImageChild> downloadHollywoodComedyMovieImages(){
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference comedy = storageReference.child("images/Comedy/Hollywood");

        comedy.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item: listResult.getItems()){
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            CardImageChild cardTemp = new CardImageChild(uri.toString(), uri.getPath());
                            comedyImages.add(cardTemp);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        /*comedy.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference prefix: listResult.getItems()){
                    prefix.getBytes(ONE_MEG).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            CardImageChild cardTemp = new CardImageChild(bytes);
                            comedyImages.add(cardTemp);
                            //Toast.makeText(context, "Image Downloaded: Comedy", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });*/

        return comedyImages;
    }

    public ArrayList<CardImageChild> downloadHollywoodScienceFictionImages(){
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference scienceFiction = storageReference.child("images/Science Fiction/Hollywood");

        scienceFiction.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item: listResult.getItems()){
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            CardImageChild cardTemp = new CardImageChild(uri.toString(), uri.getPath());
                            scienceFictionImages.add(cardTemp);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        return scienceFictionImages;
    }

    public ArrayList<CardImageChild> downloadMovieImages(String genre){
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference hollywood = storageReference.child("images/" + genre + "/Hollywood");
        StorageReference bollywood = storageReference.child("images/" + genre + "/Bollywood");

        ArrayList<CardImageChild> allGenres = new ArrayList<>();

        hollywood.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item: listResult.getItems()){
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            CardImageChild cardTemp = new CardImageChild(uri.toString(), uri.getPath());
                            allGenres.add(cardTemp);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        bollywood.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item: listResult.getItems()){
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            CardImageChild cardTemp = new CardImageChild(uri.toString(), uri.getPath());
                            allGenres.add(cardTemp);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        return allGenres;
    }

    public ArrayList<Video> getEpisodes(String show, String industry, String genre){
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference videoReference = storageReference.child("videos/" + genre + "/" + industry + "/" + show);
        //StorageReference videoReference = storageReference.child("images/" + genre + "/" + industry);

        ArrayList<Video> allVideos = new ArrayList<>();

        videoReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item: listResult.getItems()){
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Video video = new Video(item.getName(), uri.toString());
                            allVideos.add(video);
                            Log.d("Episode Fragment: ", video.getName());
                            Log.d("Episode Fragment: ", video.getVideoUrl());
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Storage", "Failed to get videos: " + e.getMessage());
            }
        });

        return allVideos;
    }
}
