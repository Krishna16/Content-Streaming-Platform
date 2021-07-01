package com.example.moviestreamingnew.ui.home;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.moviestreamingnew.CardImageChild;
import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.ShowWithGenreParent;
import com.example.moviestreamingnew.common.ShowsSharedPreferences;
import com.example.moviestreamingnew.homepage_recycler_adapters.MovieImageAdapter;
import com.example.moviestreamingnew.homepage_recycler_adapters.ShowWithGenreAdapter;
import com.example.moviestreamingnew.repository.Storage;
import com.example.moviestreamingnew.repository.UserDatabase;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment{

    private HomeViewModel homeViewModel;

    private MovieImageAdapter movieImageAdapter;
    private ViewPager2 movieImagePager;
    private TabLayout tabLayout;
    private RecyclerView showsWithGenre;
    private LinearLayoutManager linearLayoutManager;
    private ShowWithGenreAdapter showWithGenreAdapter;
    private ArrayList<ShowWithGenreParent> showWithGenreParents;
    private SwipeRefreshLayout swipeRefreshLayout;
    private UserDatabase userDatabase;
    private ArrayList<String> selectedGenres;
    private ShowsSharedPreferences showsSharedPreferences;
    private View root;
    private ArrayList<CardImageChild> showImages;
    private Storage storage;
    private Toolbar toolbar;
    private TabLayout show_movie_layout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        SwipeRefreshLayout.OnRefreshListener swipeRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showWithGenreAdapter.notifyDataSetChanged();
                movieImageAdapter.notifyDataSetChanged();
            }
        };

        homeViewModel.setShowWithGenreParent(selectedGenres.get(0), selectedGenres.get(1), selectedGenres.get(2));
        homeViewModel.getShowWithGenreParent()
                .observe(getViewLifecycleOwner(), new Observer<ArrayList<ShowWithGenreParent>>() {
                    @Override
                    public void onChanged(ArrayList<ShowWithGenreParent> showWithGenreParents) {
                        //homeViewModel.getShowWithGenreParent().postValue(showWithGenreParents);
                        showWithGenreAdapter.notifyDataSetChanged();
                        Log.d("HomeFragment", "Livedata onChanged called!!");
                    }
                });

        toolbar = getActivity().findViewById(R.id.custom_toolbar);
        //((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        show_movie_layout = getActivity().findViewById(R.id.show_movie_tab_layout);

        /*show_movie_layout.addTab(show_movie_layout.newTab().setText("Tv Shows"));
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
        });*/

        makeActionBarVisible();

        /*final Observer<ArrayList<ShowWithGenreParent>> showWithGenreParentObserver = new Observer<ArrayList<ShowWithGenreParent>>() {
            @Override
            public void onChanged(ArrayList<ShowWithGenreParent> showWithGenreParents) {
                homeViewModel.getShowWithGenreParent().postValue(showWithGenreParents);
                showWithGenreAdapter.notifyDataSetChanged();
            }
        };

        homeViewModel.getShowWithGenreParent().observe(getViewLifecycleOwner(), showWithGenreParentObserver);*/

        //showWithGenreParents.add(new ShowWithGenreParent(selectedGenres.get(0), images1));
        //showWithGenreParents.add(new ShowWithGenreParent(selectedGenres.get(1), images2));
        //showWithGenreParents.add(new ShowWithGenreParent(selectedGenres.get(2), images3));

        linearLayoutManager = new LinearLayoutManager(root.getContext());
        showWithGenreAdapter = new ShowWithGenreAdapter(homeViewModel.getShowWithGenreParent().getValue(), root.getContext());

        showsWithGenre = root.findViewById(R.id.shows_with_genre_view);
        showsWithGenre.setLayoutManager(linearLayoutManager);
        showsWithGenre.setAdapter(showWithGenreAdapter);

        /*linearLayoutManager = new LinearLayoutManager(root.getContext());
        showWithGenreAdapter = new ShowWithGenreAdapter(showWithGenreParents, root.getContext());*/

        Toast.makeText(root.getContext(), "Loading...", Toast.LENGTH_LONG).show();

        movieImagePager = root.findViewById(R.id.movie_image_pager);
        movieImagePager.setClipToPadding(false);
        movieImagePager.setClipChildren(false);
        movieImagePager.setOffscreenPageLimit(3);
        movieImagePager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        movieImagePager.setPageTransformer(compositePageTransformer);

        movieImageAdapter = new MovieImageAdapter(showImages, root.getContext());

        movieImagePager.canScrollHorizontally(1);
        movieImagePager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        movieImagePager.setAdapter(movieImageAdapter);

        tabLayout = root.findViewById(R.id.viewpager_tab_layout);
        new TabLayoutMediator(tabLayout, movieImagePager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        //tab.setText("Tab " + (position + 1));
                    }
                }).attach();

        swipeRefreshLayout = root.findViewById(R.id.pull_to_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showWithGenreAdapter.notifyDataSetChanged();
                movieImageAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                Log.d("HomeFragment: ", "ShowImages size: " + showImages.size());
            }
        });

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        return root;
    }

    private void makeActionBarVisible() {
        if (toolbar != null)
            toolbar.setVisibility(View.VISIBLE);

        if (show_movie_layout != null)
            show_movie_layout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        showWithGenreParents = new ArrayList<>();
        selectedGenres = new ArrayList<>();
        showImages = new ArrayList<>();

        storage = new Storage();

        userDatabase = new UserDatabase(context);

        showsSharedPreferences = new ShowsSharedPreferences(context);

        selectedGenres = showsSharedPreferences.getStoredGenres();

        if (selectedGenres.get(0) == null){
            userDatabase.getSelectedGenres();
            selectedGenres = showsSharedPreferences.getStoredGenres();
        }

        homeViewModel.setImages1(selectedGenres.get(0));
        homeViewModel.setImages2(selectedGenres.get(1));
        homeViewModel.setImages3(selectedGenres.get(2));

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();

                showImages = storage.downloadShowImages(selectedGenres.get(0));
                showImages = storage.downloadShowImages(selectedGenres.get(1));
                showImages = storage.downloadShowImages(selectedGenres.get(2));
            }
        };

        thread.start();
    }


    @Override
    public void onStart() {
        super.onStart();

        SwipeRefreshLayout.OnRefreshListener swipeRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showWithGenreAdapter.notifyDataSetChanged();
                movieImageAdapter.notifyDataSetChanged();
            }
        };

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("HomeFragment: ", "ShowImages size: " + showImages.size());

                makeActionBarVisible();

                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        swipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(true);
                                swipeRefreshListener.onRefresh();
                            }
                        });
                    }

                    public void onFinish() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }.start();
            }
        }, 3000);
    }
}