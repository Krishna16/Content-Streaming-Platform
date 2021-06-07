package com.example.moviestreamingnew.ui.home;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.moviestreamingnew.CardImageChild;
import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.ShowWithGenreParent;
import com.example.moviestreamingnew.adapters.MovieImageAdapter;
import com.example.moviestreamingnew.homepage_recycler_adapters.CardImageAdapter;
import com.example.moviestreamingnew.homepage_recycler_adapters.ShowWithGenreAdapter;
import com.example.moviestreamingnew.repository.Storage;
import com.example.moviestreamingnew.viewmodel.ImagesViewModel;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.okhttp.internal.framed.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.Result;

public class HomeFragment extends Fragment{

    private HomeViewModel homeViewModel;

    private MovieImageAdapter movieImageAdapter;
    private ArrayList<Integer> movieImages = new ArrayList<>();
    private ViewPager2 movieImagePager;
    private TabLayout tabLayout;
    private RecyclerView showsWithGenre;
    private LinearLayoutManager linearLayoutManager;
    private ShowWithGenreAdapter showWithGenreAdapter;
    private ArrayList<CardImageChild> cardImageSuperhero;
    private ArrayList<CardImageChild> cardImageComedy;
    private ArrayList<CardImageChild> cardImageScienceFiction;
    private ArrayList<ShowWithGenreParent> showWithGenreParents;
    private Storage storage;
    private ImagesViewModel imagesViewModel;
    private ExecutorService pool;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Future<Result>> resultList = null;
    private List<Task> taskList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        /*homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        showWithGenreParents = new ArrayList<>();
        cardImageSuperhero = new ArrayList<>();
        cardImageComedy = new ArrayList<>();
        cardImageScienceFiction = new ArrayList<>();

        storage = new Storage(root.getContext());

        pool = Executors.newFixedThreadPool(3);
        pool.execute(new Runnable() {
            @Override
            public void run() {
                cardImageSuperhero = storage.downloadSuperheroMovieImages();
                cardImageComedy = storage.downloadComedyMovieImages();
                cardImageScienceFiction = storage.downloadScienceFictionImages();
            }
        });

        try {
            pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //imagesViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance();

        showWithGenreParents.add(new ShowWithGenreParent("Superhero", cardImageSuperhero));
        showWithGenreParents.add(new ShowWithGenreParent("Comedy", cardImageComedy));
        showWithGenreParents.add(new ShowWithGenreParent("Science Fiction", cardImageScienceFiction));

        linearLayoutManager = new LinearLayoutManager(root.getContext());
        showWithGenreAdapter = new ShowWithGenreAdapter(showWithGenreParents, root.getContext());

        showsWithGenre = root.findViewById(R.id.shows_with_genre_view);
        showsWithGenre.setLayoutManager(linearLayoutManager);
        showsWithGenre.setAdapter(showWithGenreAdapter);

        showWithGenreAdapter.notifyDataSetChanged();

        swipeRefreshLayout = root.findViewById(R.id.pull_to_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showWithGenreAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //Snackbar.make(root.findViewById(R.id.pull_to_refresh_layout), "If the images haven't loaded, pull down to refresh", Snackbar.LENGTH_LONG).show();
        Toast.makeText(root.getContext(), "Loading...", Toast.LENGTH_LONG).show();

        SwipeRefreshLayout.OnRefreshListener swipeRefreshListner = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showWithGenreAdapter.notifyDataSetChanged();
            }
        };

        movieImagePager = root.findViewById(R.id.movie_image_pager);

        movieImages.add(R.drawable.aladdin_poster);
        movieImages.add(R.drawable.avengers_endgame);
        movieImages.add(R.drawable.dunkirk);
        movieImages.add(R.drawable.inside_edge);
        movieImages.add(R.drawable.the_family_man);

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

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        swipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(true);
                                swipeRefreshListner.onRefresh();
                            }
                        });
                    }

                    public void onFinish() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }.start();
            }
        }, 3000);

        return root;
    }
}