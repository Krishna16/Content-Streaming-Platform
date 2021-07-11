package com.example.moviestreamingnew;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.moviestreamingnew.common.ShowsSharedPreferences;
import com.example.moviestreamingnew.repository.UserDatabase;
import com.example.moviestreamingnew.ui.find.FindFragment;
import com.example.moviestreamingnew.ui.home.HomeFragment;
import com.example.moviestreamingnew.ui.movie.MovieFragment;
import com.example.moviestreamingnew.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

public class NavigationActivity extends AppCompatActivity {

    private TabLayout show_movie_layout;
    private Toolbar custom_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        makeCurrentFragment(new HomeFragment());
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.navigation_home : makeCurrentFragment(new HomeFragment());
                                                break;

                    case R.id.navigation_find: makeCurrentFragment(new FindFragment());
                                                break;

                    case R.id.navigation_profile: makeCurrentFragment(new ProfileFragment());
                                                break;
                }

                item.setChecked(true);

                return true;
            }
        });

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
                R.id.navigation_home, R.id.navigation_find, R.id.navigation_profile)
                .build();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //NavigationUI.setupWithNavController(navView, navController);

        show_movie_layout = findViewById(R.id.show_movie_tab_layout);

        show_movie_layout.addTab(show_movie_layout.newTab().setText("Tv Shows"));
        show_movie_layout.addTab(show_movie_layout.newTab().setText("Movies"));
        show_movie_layout.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

        show_movie_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    HomeFragment homeFragment = new HomeFragment();

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, homeFragment);
                    transaction.addToBackStack("HomeFragment");
                    transaction.commit();

                    Fragment fragment = getSupportFragmentManager().findFragmentByTag("MovieFragment");
                    if(fragment != null)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }

                else {
                    MovieFragment movieFragment = new MovieFragment();

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, movieFragment);
                    transaction.addToBackStack("MovieFragment");
                    transaction.commit();

                    Fragment fragment = getSupportFragmentManager().findFragmentByTag("HomeFragment");
                    if(fragment != null)
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
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

        /*if (show_movie_layout.getSelectedTabPosition() == 0){
            HomeFragment homeFragment = new HomeFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, homeFragment);
            transaction.addToBackStack("HomeFragment");
            transaction.commit();
        }*/
    }

    @Override
    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }
        else {
            super.onBackPressed();
        }
    }


    private void makeCurrentFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment)
                .commit();
    }
}