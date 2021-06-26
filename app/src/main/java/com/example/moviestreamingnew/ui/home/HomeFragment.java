package com.example.moviestreamingnew.ui.home;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
import java.util.concurrent.ExecutorService;

public class HomeFragment extends Fragment{

    private HomeViewModel homeViewModel;

    private MovieImageAdapter movieImageAdapter;
    private ArrayList<CardImageChild> movieImages = new ArrayList<>();
    private ViewPager2 movieImagePager;
    private TabLayout tabLayout;
    private RecyclerView showsWithGenre;
    private LinearLayoutManager linearLayoutManager;
    private ShowWithGenreAdapter showWithGenreAdapter;
    private ArrayList<CardImageChild> images1;
    private ArrayList<CardImageChild> images2;
    private ArrayList<CardImageChild> images3;
    private ArrayList<ShowWithGenreParent> showWithGenreParents;
    private Storage storage;
    private ExecutorService pool;
    private SwipeRefreshLayout swipeRefreshLayout;
    private UserDatabase userDatabase;
    private ArrayList<String> selectedGenres;
    private ShowsSharedPreferences showsSharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

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

        /*pool = Executors.newFixedThreadPool(3);
        pool.execute(new Runnable() {
            @Override
            public void run() {
                images1 = storage.downloadMovieImages(selectedGenres.get(0));
                images2 = storage.downloadMovieImages(selectedGenres.get(1));
                images3 = storage.downloadMovieImages(selectedGenres.get(2));
            }
        });

        try {
            pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        /*showWithGenreParents.add(new ShowWithGenreParent(selectedGenres.get(0), images1));
        showWithGenreParents.add(new ShowWithGenreParent(selectedGenres.get(1), images2));
        showWithGenreParents.add(new ShowWithGenreParent(selectedGenres.get(2), images3));

        linearLayoutManager = new LinearLayoutManager(root.getContext());
        showWithGenreAdapter = new ShowWithGenreAdapter(showWithGenreParents, root.getContext());

        showsWithGenre = root.findViewById(R.id.shows_with_genre_view);
        showsWithGenre.setLayoutManager(linearLayoutManager);
        showsWithGenre.setAdapter(showWithGenreAdapter);*/

        Toast.makeText(root.getContext(), "Loading...", Toast.LENGTH_LONG).show();

        movieImagePager = root.findViewById(R.id.movie_image_pager);

        movieImages.addAll(images1);
        movieImages.addAll(images2);
        movieImages.addAll(images3);

        movieImageAdapter = new MovieImageAdapter(movieImages, root.getContext());

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
            }
        });

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        return root;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        showWithGenreParents = new ArrayList<>();
        images1 = new ArrayList<>();
        images2 = new ArrayList<>();
        images3 = new ArrayList<>();
        selectedGenres = new ArrayList<>();

        storage = new Storage(context);

        userDatabase = new UserDatabase(context);

        showsSharedPreferences = new ShowsSharedPreferences(context);

        selectedGenres = showsSharedPreferences.getStoredGenres();

        if (selectedGenres.get(0) == null){
            userDatabase.getSelectedGenres();
            selectedGenres = showsSharedPreferences.getStoredGenres();
        }

        //images1 = storage.downloadMovieImages(selectedGenres.get(0));
        //images2 = storage.downloadMovieImages(selectedGenres.get(1));
        //images3 = storage.downloadMovieImages(selectedGenres.get(2));

        homeViewModel.setImages1(selectedGenres.get(0));
        homeViewModel.setImages2(selectedGenres.get(1));
        homeViewModel.setImages3(selectedGenres.get(2));
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
        }, 2500);
    }
}