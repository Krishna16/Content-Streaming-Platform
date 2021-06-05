package com.example.moviestreamingnew.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestreamingnew.NavigationActivity;
import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.common.NetworkCheck;
//import com.facebook.login.widget.LoginButton;
import com.example.moviestreamingnew.repository.FirebaseAccount;
import com.example.moviestreamingnew.ui.home.HomeFragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    private Button signIn;
    private GoogleSignInButton googleButton;
    private LoginButton facebookButton;
    private TextView signUp;
    private TabLayout tabLayout;
    private TextInputEditText username, password;
    private TextInputLayout layoutUsername, layoutPassword;
    private CallbackManager callbackManager;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mSignInClient;
    private FirebaseAuth mAuth;
    private FirebaseAccount firebaseAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        //facebook callback manager
        callbackManager = CallbackManager.Factory.create();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        googleButton = findViewById(R.id.google_button);
        facebookButton = findViewById(R.id.facebook_button);

        signUp = findViewById(R.id.sign_up);
        signIn = findViewById(R.id.sign_in);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        layoutUsername = findViewById(R.id.textInputLayoutUsername);
        layoutPassword = findViewById(R.id.textInputLayoutPassword);

        firebaseAccount = new FirebaseAccount(this);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
                finish();
            }
        });

        tabLayout = findViewById(R.id.sign_in_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.email_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.social_icon));

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()){
                    if (NetworkCheck.networkCheck(Login.this)){
                        //UserRepository userRepository = new UserRepository();
                        //userRepository.signIn(username.getText().toString(), password.getText().toString());

                        firebaseAccount.signInWithEmail(username.getText().toString(), password.getText().toString());
                    }

                    else{
                        Toast.makeText(Login.this, "You're lacking an internet connection!!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Login", "facebook:onSuccess:" + loginResult);
                firebaseAccount.handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Login", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Login", "facebook:onError", error);
            }
        });
    }

    //google sign-in & facebook sign-in
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("Login", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAccount.firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("Login", "Google sign in failed", e);
            }
        }

        else{
            // Pass the activity result back to the Facebook SDK
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void emailView(){
        googleButton.setVisibility(View.INVISIBLE);
        //facebookButton.setVisibility(View.INVISIBLE);
        signIn.setVisibility(View.VISIBLE);

        layoutUsername.setHintAnimationEnabled(true);
        layoutUsername.setHint("Email");
        layoutUsername.setVisibility(View.VISIBLE);
        username.setText("");
        username.setHintTextColor(getColor(R.color.textInputEditTextHintColor));
        username.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        layoutPassword.setHintAnimationEnabled(true);
        layoutPassword.setHint("Password");
        layoutPassword.setVisibility(View.VISIBLE);
        password.setText("");
        password.setHintTextColor(getColor(R.color.textInputEditTextHintColor));
        password.setInputType(81);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    private void socialView(){
        layoutUsername.setVisibility(View.GONE);
        layoutPassword.setVisibility(View.GONE);
        signIn.setVisibility(View.GONE);

        googleButton.setVisibility(View.VISIBLE);
        facebookButton.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    emailView();
                }

                else if (tab.getPosition() == 1){
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
    }

    private boolean validateInputs(){
        if (username.getText().toString().equals("")){
            username.setError("Please specify your email!!");
            return false;
        }
        if (password.getText().toString().equals("")){
            password.setError("Please specify a password!!");
            return false;
        }
        if (!validateEmail(username.getText().toString())){
            username.setError("Please enter a valid email!!");
            return false;
        }
        return false;
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
}