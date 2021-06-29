package com.example.moviestreamingnew.repository;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.models.Show;
import com.example.moviestreamingnew.ui.description.DescriptionFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ShowsDatabase {
    private ArrayList<String> genres;
    private FirebaseFirestore firebaseFirestore;
    private Context context;
    private boolean hasLiked = false;
    private boolean hasWatchLater = false;

    public ShowsDatabase(Context context) {
        this.genres = new ArrayList<>();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.context = context;
    }

    //for form
    public ArrayList<String> getGenres(){
        /*this.showsReference = databaseReference.child("tv_shows");

        final ValueEventListener showsDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> showsSnapshot = snapshot.getChildren();

                for (DataSnapshot show: showsSnapshot){
                    genres.add(show.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        showsReference.addValueEventListener(showsDataListener);

        return this.genres;*/

        firebaseFirestore.collection("tv_shows").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        genres.add(document.getId());
                        Log.d("Shows Database", document.getId());
                    }
                    Log.d("Shows Database", genres.toString());
                } else {
                    Log.d("Shows Database", "Error getting documents: ", task.getException());
                }
            }
        });

        return this.genres;
    }

    public void getDetails(String show, String industry, String genre, String image){

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        firebaseFirestore.collection("tv_shows")
                .document(genre)
                .collection(industry)
                .document(show)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();

                        if (task.isSuccessful()){
                            Thread getData = new Thread(){
                                @Override
                                public void run(){
                                    Log.d("Shows Database: ", "" + documentSnapshot.getData());
                                    Show.getInstance().setDescription(documentSnapshot.get("description").toString());
                                    Show.getInstance().setName(documentSnapshot.get("name").toString());
                                    Show.getInstance().setRating(Double.parseDouble(documentSnapshot.get("rating").toString()));
                                    Show.getInstance().setEpisodes(Integer.parseInt(documentSnapshot.get("episodes").toString()));
                                    Show.getInstance().setLikes(Integer.parseInt(documentSnapshot.get("likes").toString()));
                                    Show.getInstance().setGenre(documentSnapshot.get("genre").toString());
                                }
                            };

                            getData.start();

                            Thread getLiked = new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                    UserDatabase userDatabase = new UserDatabase(context);
                                    hasLiked = userDatabase.isLikedShow(documentSnapshot.get("name").toString());
                                }
                            };

                            Log.d("ShowsDatabase: ", "hasLiked: " + hasLiked);

                            getLiked.start();

                            Thread getWatchLater = new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                    UserDatabase userDatabase = new UserDatabase(context);
                                    hasWatchLater = userDatabase.isWatchLater(documentSnapshot.get("name").toString());
                                }
                            };

                            getWatchLater.start();

                            Log.d("ShowsDatabase: ", "hasWatchLater: " + hasWatchLater);

                            DescriptionFragment descriptionFragment = new DescriptionFragment(image, show, industry, genre, hasLiked, hasWatchLater);

                            FragmentTransaction transaction =  ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.nav_host_fragment, descriptionFragment);
                            transaction.addToBackStack("description");
                            transaction.commit();

                            progressDialog.dismiss();
                        }
                    }
                });
    }

    public void addLikes(String show, String industry, String genre, int likes){
        firebaseFirestore.collection("tv_shows")
                .document(genre)
                .collection(industry)
                .document(show)
                .update("likes", likes);
    }

    public void removeLikes(String show, String industry, String genre, int likes){
        firebaseFirestore.collection("tv_shows")
                .document(genre)
                .collection(industry)
                .document(show)
                .update("likes", likes);
    }
}
