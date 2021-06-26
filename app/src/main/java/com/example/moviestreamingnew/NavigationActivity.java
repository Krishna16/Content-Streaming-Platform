package com.example.moviestreamingnew;

import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.moviestreamingnew.common.ShowsSharedPreferences;
import com.example.moviestreamingnew.repository.UserDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        getSupportActionBar().hide();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_find, R.id.navigation_downloads)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        ShowsSharedPreferences showsSharedPreferences = new ShowsSharedPreferences(this);
        if (showsSharedPreferences.getStoredGenres().get(0) == null){
            new UserDatabase(this).getSelectedGenres();
        }
    }
}