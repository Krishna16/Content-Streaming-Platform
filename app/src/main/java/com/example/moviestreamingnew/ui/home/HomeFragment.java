package com.example.moviestreamingnew.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.moviestreamingnew.CardImageChild;
import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.ShowWithGenreParent;
import com.example.moviestreamingnew.adapters.MovieImageAdapter;
import com.example.moviestreamingnew.homepage_recycler_adapters.CardImageAdapter;
import com.example.moviestreamingnew.homepage_recycler_adapters.ShowWithGenreAdapter;
import com.example.moviestreamingnew.repository.Storage;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

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

        cardImageSuperhero = storage.downloadSuperheroMovieImages();
        cardImageComedy = storage.downloadComedyMovieImages();
        cardImageScienceFiction = storage.downloadScienceFictionImages();

        //cardImageComedy.add();

        showWithGenreParents.add(new ShowWithGenreParent("Superhero", cardImageSuperhero));
        showWithGenreParents.add(new ShowWithGenreParent("Comedy", cardImageComedy));
        showWithGenreParents.add(new ShowWithGenreParent("Science Fiction", cardImageScienceFiction));

        linearLayoutManager = new LinearLayoutManager(root.getContext());
        showWithGenreAdapter = new ShowWithGenreAdapter(showWithGenreParents);

        showsWithGenre = root.findViewById(R.id.shows_with_genre_view);
        showsWithGenre.setLayoutManager(linearLayoutManager);
        showsWithGenre.setAdapter(showWithGenreAdapter);

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

        return root;
    }
}