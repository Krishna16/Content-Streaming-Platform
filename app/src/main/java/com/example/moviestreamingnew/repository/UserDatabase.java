package com.example.moviestreamingnew.repository;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.moviestreamingnew.NavigationActivity;
import com.example.moviestreamingnew.account.NewUserForm;
import com.example.moviestreamingnew.common.ShowsSharedPreferences;
import com.example.moviestreamingnew.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private Context context;
    private User user = new User();
    private ShowsSharedPreferences showsSharedPreferences;
    private List<String> genres;
    private boolean hasLiked = false;
    private boolean hasWatchLater = false;

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

    public void getSelectedGenres(){
        /*ValueEventListener genresListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> genresSnapshot = snapshot.getChildren();

                Log.d("UserDatabase: ", "Outside for loop");

                for (DataSnapshot genre: genresSnapshot){
                    //Log.d("UserDatabase: ", genre.getValue().toString());
                    genres.add(genre.getValue().toString());
                }

                Log.d("UserDatabase: ", "for loop executed");
                showsSharedPreferences.storeSelectedGenres(genres);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("UserDatabase: ", error.getMessage());
            }
        };*/

        databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).child("genres").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> genresSnapshot = dataSnapshot.getChildren();

                for(DataSnapshot genre: genresSnapshot){
                    Log.d("UserDatabase: ", genre.getValue().toString());
                    genres.add(genre.getValue().toString());
                }

                showsSharedPreferences.storeSelectedGenres(genres);
            }
        });
    }

    public void doesUserExist(){
        databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Log.d("DataSnapshot exists: ", "" + dataSnapshot.exists());

                if (dataSnapshot.exists()){
                    downloadUserDataToSharedPreferences();

                    /*Intent intent = new Intent(context, NavigationActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();*/
                }

                else{
                    Intent intent = new Intent(context, NewUserForm.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
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

    public void addLikedShow(String show){
        databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).child("likedShows").push().setValue(show);
    }

    public void removeLikedShow(String show){
        databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).child("likedShows").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();

                for (DataSnapshot item: children){
                    if (item.getValue().equals(show)){
                        item.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void addWatchLaterShow(String show){
        databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).child("watchLaterShows").push().setValue(show);
    }

    public void removeWatchLaterShow(String show){
        databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).child("watchLaterShows").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();

                for (DataSnapshot item: children){
                    if (item.getValue().equals(show)){
                        item.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public boolean isLikedShow(String show){
        databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).child("likedShows").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();

                for (DataSnapshot item: children){
                    if (item.getValue().equals(show)){
                        hasLiked = true;
                        Log.d("UserDatabase: ", "hasLiked: " + hasLiked);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return hasLiked;
    }

    public boolean isWatchLater(String show){
        databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).child("watchLaterShows").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();

                for (DataSnapshot item: children){
                    if (item.getValue().equals(show)){
                        hasWatchLater = true;
                        Log.d("UserDatabase: ", "hasWatchLater: " + hasWatchLater);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return hasWatchLater;
    }

    public void downloadUserDataToSharedPreferences(){
        databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Log.d("UserDatabase: ", "Downloading user data...");

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                User user = new User();

                int i = 0;

                for (DataSnapshot item: children){
                    switch (i){
                        case 0: Iterable<DataSnapshot> genres = item.getChildren();
                                for (DataSnapshot genre: genres){
                                    user.addGenre(genre.getValue().toString());
                                }
                                i = i + 1;
                                break;

                        case 1: user.setIndustry(item.getValue().toString());
                                i = i + 1;
                                break;

                        case 2: Iterable<DataSnapshot> likedMovies = item.getChildren();
                                for (DataSnapshot like: likedMovies){
                                    user.addToLikedMovies(like.getValue().toString());
                                }
                                i = i + 1;
                                break;

                        case 3: Iterable<DataSnapshot> likedShows = item.getChildren();
                                for (DataSnapshot like: likedShows){
                                    user.addToLikedShows(like.getValue().toString());
                                }
                                i = i + 1;
                                break;

                        case 4: user.setName(item.getValue().toString());
                                i = i + 1;
                                break;

                        case 5: user.setPreference(item.getValue().toString());
                                i = i + 1;
                                break;

                        case 6: user.setUid(item.getValue().toString());
                                i = i + 1;
                                break;

                        case 7: Iterable<DataSnapshot> watchLaterMovies = item.getChildren();
                                for (DataSnapshot later: watchLaterMovies){
                                    user.addToWatchLaterMovies(later.getValue().toString());
                                }
                                i = i + 1;
                                break;

                        case 8: Iterable<DataSnapshot> watchLaterShows = item.getChildren();
                                for (DataSnapshot later: watchLaterShows){
                                    user.addToWatchLaterShows(later.getValue().toString());
                                }
                                i = i + 1;
                                break;

                        default: if (i >= 9){
                            break;
                        }
                    }
                }

                ShowsSharedPreferences showsSharedPreferences = new ShowsSharedPreferences(context);
                showsSharedPreferences.storeUserData(user);

                Intent intent = new Intent(context, NavigationActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();

                Log.d("User Database: ", "Stored User Data: " + user.getUid());
                Log.d("Login: ", "In UserDatabase");

                Toast.makeText(context, "Please restart the app to see changes!!", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

            }
        });
    }

    public void addLikedMovie(String movie){
        databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).child("likedMovies").push().setValue(movie);
    }

    public void removeLikedMovie(String movie){
        databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).child("likedMovies").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();

                for (DataSnapshot item: children){
                    if (item.getValue().equals(movie)){
                        item.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void addWatchLaterMovie(String movie){
        databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).child("watchLaterMovies").push().setValue(movie);
    }

    public void removeWatchLaterMovie(String movie){
        databaseReference.child("users").child(mAuth.getCurrentUser().getUid()).child("watchLaterMovies").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();

                for (DataSnapshot item: children){
                    if (item.getValue().equals(movie)){
                        item.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
