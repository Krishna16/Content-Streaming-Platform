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
import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class FirebaseAccount {
    private Context context;
    private FirebaseAuth mAuth;
    private boolean isSuccess;
    private ProgressDialog progressDialog;

    public FirebaseAccount(Context context){
        this.context = context;
        this.mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this.context);
    }

    public void createUserWithEmail(String email, String password){
        progressDialog.setMessage("Registering....");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Create User With Email", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            progressDialog.dismiss();

                            Toast.makeText(context, "User successfully registered!!", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(context, NewUserForm.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Create User With Email", "createUserWithEmail:failure", task.getException());
                            progressDialog.dismiss();
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signInWithEmail(String email, String password){
        progressDialog.setMessage("Signing in....");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignInWithEmail", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            progressDialog.dismiss();

                            if (new UserDatabase().doesUserExist()){
                                Intent intent = new Intent(context, NavigationActivity.class);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            }

                            else{
                                Intent intent = new Intent(context, NewUserForm.class);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignInWithEmail", "signInWithEmail:failure", task.getException());
                            progressDialog.dismiss();
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //google sign-in
    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (new UserDatabase().doesUserExist()){
                                Intent intent = new Intent(context, NavigationActivity.class);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            }

                            else{
                                Intent intent = new Intent(context, NewUserForm.class);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login", "signInWithCredential:failure", task.getException());
                            Toast.makeText(context, "Sign in failed!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //facebook sign-in
    public void handleFacebookAccessToken(AccessToken token) {
        Log.d("Login", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            isSuccess = true;

                            if (new UserDatabase().doesUserExist()){
                                Intent intent = new Intent(context, NavigationActivity.class);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            }

                            else{
                                Intent intent = new Intent(context, NewUserForm.class);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login", "signInWithCredential:failure", task.getException());
                            Toast.makeText(context, "Facebook Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            isSuccess = false;
                        }
                    }
                });
    }
}
