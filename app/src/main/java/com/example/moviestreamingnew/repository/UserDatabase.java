package com.example.moviestreamingnew.repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.moviestreamingnew.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDatabase {
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private Context context;

    public UserDatabase(){

    }

    public UserDatabase(Context context){
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        this.mAuth = FirebaseAuth.getInstance();
        this.context = context;
    }

    public boolean uploadUserData(){
        String userId = this.mAuth.getUid();

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait while we create your profile...");
        progressDialog.show();

        final boolean[] userCreated = {false};

        databaseReference.child("users").child(userId).setValue(User.getInstance()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(context, "Profile Created Successfully!!", Toast.LENGTH_LONG).show();
                userCreated[0] = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context, "Error Creating Profile!!", Toast.LENGTH_LONG).show();
            }
        });

        return userCreated[0];
    }
}
