package com.example.moviestreamingnew.ui.movie;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviestreamingnew.CardImageChild;
import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.ShowWithGenreParent;
import com.example.moviestreamingnew.common.ShowsSharedPreferences;
import com.example.moviestreamingnew.homepage_recycler_adapters.MovieImageAdapter;
import com.example.moviestreamingnew.homepage_recycler_adapters.ShowWithGenreAdapter;
import com.example.moviestreamingnew.repository.MovieAPI;
import com.example.moviestreamingnew.repository.Storage;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MovieFragment extends Fragment {

    private View root;

    private MovieAPI movieAPI;

    private RecyclerView moviesWithGenre;
    private ViewPager2 movieImagePager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TabLayout viewPagerTabLayout;
    private MovieImageAdapter movieImageAdapter;
    private ShowWithGenreAdapter movieWithGenreAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<CardImageChild> showPagerImages;
    private ArrayList<String> selectedGenres;
    private ShowsSharedPreferences showsSharedPreferences;
    private Storage storage;

    private MovieViewModel movieViewModel;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.root = inflater.inflate(R.layout.fragment_movie, container, false);

        SwipeRefreshLayout.OnRefreshListener swipeRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                movieWithGenreAdapter.notifyDataSetChanged();
                movieImageAdapter.notifyDataSetChanged();
            }
        };

        movieViewModel.setShowWithGenreParent(selectedGenres.get(0), selectedGenres.get(1), selectedGenres.get(2));
        movieViewModel.getShowWithGenreParent()
                .observe(getViewLifecycleOwner(), new Observer<ArrayList<ShowWithGenreParent>>() {
                    @Override
                    public void onChanged(ArrayList<ShowWithGenreParent> showWithGenreParents) {
                        //homeViewModel.getShowWithGenreParent().postValue(showWithGenreParents);
                        movieWithGenreAdapter.notifyDataSetChanged();
                        Log.d("HomeFragment", "Livedata onChanged called!!");
                    }
                });

        this.movieImagePager = root.findViewById(R.id.movie_image_pager);
        this.swipeRefreshLayout = root.findViewById(R.id.pull_to_refresh_layout);
        this.viewPagerTabLayout = root.findViewById(R.id.viewpager_tab_layout);

        linearLayoutManager = new LinearLayoutManager(root.getContext());
        movieWithGenreAdapter = new ShowWithGenreAdapter(movieViewModel.getShowWithGenreParent().getValue(), root.getContext());

        moviesWithGenre = root.findViewById(R.id.movies_with_genre_view);
        moviesWithGenre.setLayoutManager(linearLayoutManager);
        moviesWithGenre.setAdapter(movieWithGenreAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                movieWithGenreAdapter.notifyDataSetChanged();
                movieImageAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                Log.d("HomeFragment: ", "ShowImages size: " + showPagerImages.size());
            }
        });

        //movieAPI = new MovieAPI();
        //movieAPI.getMoviesByGenre("Science Fiction");

        return root;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        showPagerImages = new ArrayList<>();
        selectedGenres = new ArrayList<>();

        showsSharedPreferences = new ShowsSharedPreferences(context);
        selectedGenres = showsSharedPreferences.getStoredGenres();

        storage = new Storage(context);

        movieViewModel.setImages1(selectedGenres.get(0));
        movieViewModel.setImages2(selectedGenres.get(1));
        movieViewModel.setImages3(selectedGenres.get(2));

        movieViewModel.setUpcoming();
        movieViewModel.setTrending();

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();

                showPagerImages = storage.downloadShowImages(selectedGenres.get(0));
                showPagerImages = storage.downloadShowImages(selectedGenres.get(1));
                showPagerImages = storage.downloadShowImages(selectedGenres.get(2));
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
                movieWithGenreAdapter.notifyDataSetChanged();
                movieImageAdapter.notifyDataSetChanged();
            }
        };

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //Log.d("HomeFragment: ", "ShowImages size: " + showPagerImages.size());

                movieImagePager.setClipToPadding(false);
                movieImagePager.setClipChildren(false);
                movieImagePager.setOffscreenPageLimit(3);
                movieImagePager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
                CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                compositePageTransformer.addTransformer(new MarginPageTransformer(40));
                movieImagePager.setPageTransformer(compositePageTransformer);

                movieImageAdapter = new MovieImageAdapter(showPagerImages, root.getContext());

                movieImagePager.canScrollHorizontally(1);
                movieImagePager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                movieImagePager.setAdapter(movieImageAdapter);

                viewPagerTabLayout = root.findViewById(R.id.viewpager_tab_layout);
                new TabLayoutMediator(viewPagerTabLayout, movieImagePager,
                        new TabLayoutMediator.TabConfigurationStrategy() {
                            @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                                //tab.setText("Tab " + (position + 1));
                            }
                        }).attach();

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