package com.example.moviestreamingnew.repository;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class ShowsDatabase {
    private DatabaseReference databaseReference;
    private DatabaseReference showsReference;
    private ArrayList<String> genres;
    private FirebaseFirestore firebaseFirestore;

    public ShowsDatabase() {
        this.genres = new ArrayList<>();
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
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
}
