package com.example.moviestreamingnew.repository;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.moviestreamingnew.NavigationActivity;
import com.example.moviestreamingnew.common.ShowsSharedPreferences;
import com.example.moviestreamingnew.interfaces.OnFirebaseDataRead;
import com.example.moviestreamingnew.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private Context context;
    private User user = new User();
    private ShowsSharedPreferences showsSharedPreferences;
    private List<String> genres;

    public UserDatabase(){

    }

    public UserDatabase(Context context){
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        this.mAuth = FirebaseAuth.getInstance();
        this.context = context;
        this.showsSharedPreferences = new ShowsSharedPreferences(context);
        this.genres = new ArrayList<>();
    }

    public void uploadUserData(){
        String userId = this.mAuth.getCurrentUser().getUid();

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait while we create your profile...");
        progressDialog.show();

        user = User.getInstance();

        databaseReference.child("users").child(userId).setValue(user,1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(context, "Profile Created Successfully!!", Toast.LENGTH_LONG).show();

                Intent i = new Intent(context, NavigationActivity.class);
                context.startActivity(i);
                ((Activity) context).finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context, "Error Creating Profile!!", Toast.LENGTH_LONG).show();

            }
        });
    }

    public boolean getSelectedGenres(){
//        ValueEventListener genresListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Iterable<DataSnapshot> genresSnapshot = snapshot.getChildren();
//
//                Log.d("UserDatabase: ", "Outside for loop");
//
//                for (DataSnapshot genre: genresSnapshot){
//                    //Log.d("UserDatabase: ", genre.getValue().toString());
//                    genres.add(genre.getValue().toString());
//                }
//
//                Log.d("UserDatabase: ", "for loop executed");
//                showsSharedPreferences.storeSelectedGenres(genres);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.d("UserDatabase: ", error.getMessage());
//            }
//        };

        databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).child("genres").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Log.d("OnSuccess","Data is : "+ dataSnapshot.getValue().toString());
                if (dataSnapshot.hasChildren())
                {
                    Log.d("OnSuccess","true");
                }
                else
                {
                    Log.d("OnSuccess","False");
                }
                for (DataSnapshot genre: dataSnapshot.getChildren()){
                    Log.d("HomeFragment: ", genre.getValue().toString());
                    genres.add(genre.getValue().toString());

                }
                showsSharedPreferences.storeSelectedGenres(genres);

            }
        });
        return true;
    }
    public void readData(DatabaseReference ref, final OnFirebaseDataRead listener) {
        listener.onStart();
        ref.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                listener.onFailure();
            }
        });
    }

    /*public void readData(DatabaseReference ref, final OnFirebaseDataRead listener) {
        listener.onStart();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //listener.onSuccess(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                listener.onFailure();
            }
        });
    }*/
}
