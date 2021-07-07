package com.example.moviestreamingnew.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.common.NetworkCheck;
//import com.facebook.login.widget.LoginButton;
import com.example.moviestreamingnew.repository.FirebaseAccount;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.Objects;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private TabLayout tabLayout;
    private TextInputEditText username, password, confirmPassword;
    private TextInputLayout layoutUsername, layoutPassword, layoutConfirmPassword;
    private Button register;
    private GoogleSignInButton googleButton;
    private LoginButton facebookButton;
    //private final CognitoUserAttributes userAttributes = new CognitoUserAttributes();
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //getSupportActionBar().hide();

        //facebook callback manager
        callbackManager = CallbackManager.Factory.create();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);

        register = findViewById(R.id.register);

        //googleButton = findViewById(R.id.google_button);
        //facebookButton = findViewById(R.id.facebook_button);

        layoutUsername = findViewById(R.id.textInputLayoutUsername);
        layoutPassword = findViewById(R.id.textInputLayoutPassword);
        layoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword);

        //tabLayout = findViewById(R.id.sign_up_tabLayout);
        //tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.email_icon));
        //tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.social_icon));

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()){
                    if (NetworkCheck.networkCheck(SignUp.this)){
                        FirebaseAccount firebaseAccount = new FirebaseAccount(SignUp.this);
                        firebaseAccount.createUserWithEmail(Objects.requireNonNull(username.getText()).toString(), Objects.requireNonNull(password.getText()).toString());
                    }

                    else{
                        Toast.makeText(SignUp.this, "You're lacking an internet connection!!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        /*facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(SignUp.this, "SignUp with Facebook Succeeded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SignUp.this, "Exception: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Login.class));
        finish();
    }

    /*@Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    emailView();
                }

                if (tab.getPosition() == 1){
                    socialView();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }*/

    private void emailView(){
        layoutUsername.setHint("Email");
        layoutUsername.setHintAnimationEnabled(true);
        username.setText("");
        layoutUsername.setVisibility(View.VISIBLE);
        username.setHintTextColor(getColor(R.color.textInputEditTextHintColor));
        username.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        layoutPassword.setHintAnimationEnabled(true);
        layoutPassword.setHint("Password");
        layoutPassword.setVisibility(View.VISIBLE);
        password.setText("");
        password.setHintTextColor(getColor(R.color.textInputEditTextHintColor));
        password.setInputType(81);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        layoutConfirmPassword.setHintAnimationEnabled(true);
        layoutConfirmPassword.setHint("Confirm Password");
        layoutConfirmPassword.setVisibility(View.VISIBLE);
        confirmPassword.setText("");
        confirmPassword.setHintTextColor(getColor(R.color.textInputEditTextHintColor));
        confirmPassword.setInputType(81);
        confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        googleButton.setVisibility(View.GONE);
        //facebookButton.setVisibility(View.GONE);
        register.setVisibility(View.VISIBLE);
    }

    /*private void socialView(){
        layoutUsername.setVisibility(View.GONE);
        layoutPassword.setVisibility(View.GONE);
        layoutConfirmPassword.setVisibility(View.GONE);
        register.setVisibility(View.GONE);

        //facebookButton.setVisibility(View.VISIBLE);
        googleButton.setVisibility(View.VISIBLE);
    }*/

    private boolean validateInputs(){
        if (username.getText().toString().equals("")){
            username.setError("Please specify your email!!");
            return false;
        }
        if (password.getText().toString().equals("")){
            password.setError("Please specify a password!!");
            return false;
        }
        if (confirmPassword.getText().toString().equals("")){
            confirmPassword.setError("Please confirm your password!!");
            return false;
        }
        if (!password.getText().toString().equals(confirmPassword.getText().toString())){
            confirmPassword.setError("Passwords do not match!!");
            return false;
        }
        if (!validateEmail(username.getText().toString())){
            username.setError("Please enter a valid email!!");
            return false;
        }

        return true;
    }

    private boolean validateEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        if (email == null)
            return false;

        return pat.matcher(email).matches();
    }

    /*//for facebook intent to sign-in
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}