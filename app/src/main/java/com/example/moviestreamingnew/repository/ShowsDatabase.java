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

import java.util.ArrayList;

public class ShowsDatabase {
    private DatabaseReference databaseReference;
    private DatabaseReference showsReference;
    private ArrayList<String> genres;

    public ShowsDatabase() {
        this.genres = new ArrayList<>();
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public ArrayList<String> getGenres(){
        this.showsReference = databaseReference.child("tv_shows");

        /*showsReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()){
                    Log.d("Shows Database", "Get genres failed");
                }

                else{
                    Log.d("Shows Database", String.valueOf(task.getResult().getValue()));
                    //genres.addAll(String.valueOf());
                }
            }
        });*/

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

        return this.genres;
    }
}
