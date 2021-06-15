package com.example.moviestreamingnew.repository;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.moviestreamingnew.CardImageChild;
import com.example.moviestreamingnew.ShowWithGenreParent;
import com.example.moviestreamingnew.homepage_recycler_adapters.ShowWithGenreAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private FirebaseStorage firebaseStorage;
    private final int ONE_MEG = 1024 * 1024 * 5;
    private ArrayList<CardImageChild> superheroImages;
    private ArrayList<CardImageChild> comedyImages;
    private ArrayList<CardImageChild> scienceFictionImages;
    private Context context;
    private ArrayList<String> genres;

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

    public ArrayList<CardImageChild> downloadSuperheroMovieImages(){
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference superhero = storageReference.child("images/Superhero");

        //StorageReference superImage1 = superhero.child("Arrow.jpg");
        //StorageReference superImage2 = superhero.child("The Flash.jpg");
        //StorageReference superImage3 = superhero.child("Wanda Vision.jpg");

        superhero.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item: listResult.getItems()){
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            CardImageChild cardTemp = new CardImageChild(uri.toString());
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

        /*superImage1.getBytes(ONE_MEG).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                //when the file is downloaded set it to the
                //recycler view in the home fragment using
                //the parent and child recycler view
                //guide on geekforgeeks
                CardImageChild cardTemp = new CardImageChild(bytes);
                images.add(cardTemp);
                Toast.makeText(context, "Image Downloaded", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        superImage2.getBytes(ONE_MEG).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                //when the file is downloaded set it to the
                //recycler view in the home fragment using
                //the parent and child recycler view
                //guide on geekforgeeks
                CardImageChild cardTemp = new CardImageChild(bytes);
                images.add(cardTemp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        superImage3.getBytes(ONE_MEG).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                //when the file is downloaded set it to the
                //recycler view in the home fragment using
                //the parent and child recycler view
                //guide on geekforgeeks
                CardImageChild cardTemp = new CardImageChild(bytes);
                images.add(cardTemp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });*/

        return superheroImages;
    }

    public ArrayList<CardImageChild> downloadComedyMovieImages(){
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference comedy = storageReference.child("images/Comedy");

        comedy.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item: listResult.getItems()){
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            CardImageChild cardTemp = new CardImageChild(uri.toString());
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

        /*StorageReference comedy1 = comedy.child("The Big Bang Theory.jpg");
        StorageReference comedy2 = comedy.child("The Office.jpg");
        StorageReference comedy3 = comedy.child("Two and a half men.jpg");

        comedy1.getBytes(ONE_MEG).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                //when the file is downloaded set it to the
                //recycler view in the home fragment using
                //the parent and child recycler view
                //guide on geekforgeeks
                CardImageChild cardTemp = new CardImageChild(bytes);
                images.add(cardTemp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        comedy2.getBytes(ONE_MEG).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                //when the file is downloaded set it to the
                //recycler view in the home fragment using
                //the parent and child recycler view
                //guide on geekforgeeks
                CardImageChild cardTemp = new CardImageChild(bytes);
                images.add(cardTemp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        comedy3.getBytes(ONE_MEG).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                //when the file is downloaded set it to the
                //recycler view in the home fragment using
                //the parent and child recycler view
                //guide on geekforgeeks
                CardImageChild cardTemp = new CardImageChild(bytes);
                images.add(cardTemp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });*/

        return comedyImages;
    }

    public ArrayList<CardImageChild> downloadScienceFictionImages(){
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference scienceFiction = storageReference.child("images/Science Fiction");

        scienceFiction.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item: listResult.getItems()){
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            CardImageChild cardTemp = new CardImageChild(uri.toString());
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

        /*scienceFiction.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference prefix: listResult.getItems()){
                    prefix.getBytes(ONE_MEG).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            CardImageChild cardTemp = new CardImageChild(bytes);
                            scienceFictionImages.add(cardTemp);
                            //Toast.makeText(context, "Image Downloaded: Science Fiction", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });*/

        return scienceFictionImages;
    }

    public ArrayList<String> getGenres(){
        /*StorageReference storageReference = firebaseStorage.getReference();
        StorageReference images = storageReference.child("images");

        images.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item: listResult.getItems()){
                    //genres.add(item.getName().toString());
                    //Log.d("Storage", item.getName());
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d("Storage", uri.getPath());
                            genres.add(uri.getPath());
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Storage", "Failed to get genres");
            }
        });

        Log.d("Storage", "Size of genres in storage: " + genres.size());*/

        genres.add("Action");
        genres.add("Adventure");
        genres.add("Animation");
        genres.add("Comedy");
        genres.add("Crime");
        genres.add("Drama");
        genres.add("Horror");
        genres.add("Mystery");
        genres.add("Science Fiction");
        genres.add("Superhero");
        genres.add("War");

        return genres;
    }
}
