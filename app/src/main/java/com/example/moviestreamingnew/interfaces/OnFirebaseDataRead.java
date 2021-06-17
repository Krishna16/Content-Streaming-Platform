package com.example.moviestreamingnew.interfaces;

import com.google.firebase.database.DataSnapshot;

public interface OnFirebaseDataRead {
    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();
}
