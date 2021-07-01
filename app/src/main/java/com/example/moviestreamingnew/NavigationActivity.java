package com.example.moviestreamingnew;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.view.Window;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.moviestreamingnew.common.ShowsSharedPreferences;
import com.example.moviestreamingnew.repository.UserDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class NavigationActivity extends AppCompatActivity {

    private TabLayout show_movie_layout;
    private Toolbar custom_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        custom_toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(custom_toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        /*custom_toolbar.setLogo(R.drawable.movie_streaming_logo);
        custom_toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);*/

        //getSupportActionBar().setCustomView(R.layout.activity_splash);
        //getSupportActionBar().setTitle("");

        /*getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.movie_streaming_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);*/

        //getSupportActionBar().setCustomView(R.layout.activity_splash);

        // getSupportActionBar().hide();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_find, R.id.navigation_downloads)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        show_movie_layout = findViewById(R.id.show_movie_tab_layout);

        show_movie_layout.addTab(show_movie_layout.newTab().setText("Tv Shows"));
        show_movie_layout.addTab(show_movie_layout.newTab().setText("Movies"));
        show_movie_layout.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

        show_movie_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ShowsSharedPreferences showsSharedPreferences = new ShowsSharedPreferences(this);
        if (showsSharedPreferences.getStoredGenres().get(0) == null){
            new UserDatabase(this).getSelectedGenres();
        }
    }
}