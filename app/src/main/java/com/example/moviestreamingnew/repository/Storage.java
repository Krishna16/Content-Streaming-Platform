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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private FirebaseStorage firebaseStorage;
    private final int ONE_MEG = 1024 * 1024 * 5;
    private ArrayList<CardImageChild> superheroImages;
    private ArrayList<CardImageChild> comedyImages;
    private ArrayList<CardImageChild> scienceFictionImages;
    private Context context;

    public Storage(Context context){
        this.firebaseStorage = FirebaseStorage.getInstance();
        this.superheroImages = new ArrayList<>();
        this.comedyImages = new ArrayList<>();
        this.scienceFictionImages = new ArrayList<>();
        this.context = context;
    }

    public ArrayList<CardImageChild> downloadSuperheroMovieImages(){
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://movie-streaming-e28e2.appspot.com/");
        StorageReference superhero = storageReference.child("images/Superhero");

        //StorageReference superImage1 = superhero.child("Arrow.jpg");
        //StorageReference superImage2 = superhero.child("The Flash.jpg");
        //StorageReference superImage3 = superhero.child("Wanda Vision.jpg");

        superhero.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item: listResult.getItems()){
//                    item.getBytes(ONE_MEG).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                        @Override
//                        public void onSuccess(byte[] bytes) {
//                            CardImageChild cardTemp = new CardImageChild(bytes);
//                            superheroImages.add(cardTemp);
//                            Toast.makeText(context, "Image Downloaded: Superhero", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    item.getDownloadUrl().addOnSuccessListener(
                            new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    CardImageChild cardTemp = new CardImageChild(uri.toString());
                                    superheroImages.add(cardTemp);
                                    Log.d("ShowUri","Uri is " + uri.toString());
                                }
                            }
                    );
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
                for (StorageReference prefix: listResult.getItems()){
//                    prefix.getBytes(ONE_MEG).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                        @Override
//                        public void onSuccess(byte[] bytes) {
////                            CardImageChild cardTemp = new CardImageChild(bytes);
////                            comedyImages.add(cardTemp);
//                            Toast.makeText(context, "Image Downloaded: Comedy", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    prefix.getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>()
                            {
                                @Override
                                public void onSuccess(Uri uri) {
                                    CardImageChild cardImageChild = new CardImageChild(uri.toString());
                                    comedyImages.add(cardImageChild);
                                    Log.d("ShowUri","Uri is " + uri.toString());
                                }
                            }
                    );


                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

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
                for (StorageReference prefix: listResult.getItems()){
//                    prefix.getBytes(ONE_MEG).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//                        @Override
//                        public void onSuccess(byte[] bytes) {
////                            CardImageChild cardTemp = new CardImageChild(bytes);
////                            scienceFictionImages.add(cardTemp);
//                            Toast.makeText(context, "Image Downloaded: Science Fiction", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    prefix.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            CardImageChild cardImageChild = new CardImageChild(uri.toString());
                            scienceFictionImages.add(cardImageChild);
                            Log.d("ShowUri","Uri is " + uri.toString());
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
}
