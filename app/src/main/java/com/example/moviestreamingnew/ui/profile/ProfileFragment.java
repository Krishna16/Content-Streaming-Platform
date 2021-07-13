package com.example.moviestreamingnew.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.adapters.ProfileContentAdapter;
import com.example.moviestreamingnew.common.NetworkCheck;
import com.example.moviestreamingnew.common.ShowsSharedPreferences;
import com.example.moviestreamingnew.models.User;
import com.example.moviestreamingnew.repository.UserDatabase;
import com.example.moviestreamingnew.ui.new_user_form.GenreFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileViewModel notificationsViewModel;

    private TabLayout show_movie_layout;
    private Toolbar toolbar;

    private CardView genresCardView;
    private LinearLayout hiddenGenreView;
    private TextView genresTextView;

    private CardView likedShowsCardView;
    private LinearLayout hiddenLikedShowsView;
    private TextView likedShowsTextView;

    private CardView watchLaterShowsCardView;
    private LinearLayout hiddenWatchLaterShowsView;
    private TextView watchLaterShowsTextView;

    private CardView likedMoviesCardView;
    private LinearLayout hiddenLikedMoviesView;
    private TextView likedMoviesTextView;

    private CardView watchLaterMoviesCardView;
    private LinearLayout hiddenWatchLaterMoviesView;
    private TextView watchLaterMoviesTextView;

    private RecyclerView likedShowsRecyclerView;
    private RecyclerView likedMoviesRecyclerView;
    private RecyclerView watchLaterShowsRecyclerView;
    private RecyclerView watchLaterMoviesRecyclerView;

    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager1;
    private LinearLayoutManager linearLayoutManager2;
    private LinearLayoutManager linearLayoutManager3;

    private ProfileContentAdapter profileContentAdapter;
    private ProfileContentAdapter profileContentAdapter1;
    private ProfileContentAdapter profileContentAdapter2;
    private ProfileContentAdapter profileContentAdapter3;

    private ShowsSharedPreferences showsSharedPreferences;

    private EditText genre1;
    private EditText genre2;
    private EditText genre3;

    private EditText name;

    private FirebaseAuth mAuth;

    private User user;

    private Spinner preference;
    private Spinner industry;

    private View root;

    private TextView welcomeText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        /*notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        genresCardView = root.findViewById(R.id.genres_cardView);
        hiddenGenreView = root.findViewById(R.id.hidden_genre_view);
        genresTextView = root.findViewById(R.id.genres_textView);

        likedShowsCardView = root.findViewById(R.id.liked_shows_cardView);
        hiddenLikedShowsView = root.findViewById(R.id.hidden_liked_shows_view);
        likedShowsTextView = root.findViewById(R.id.liked_shows_textView);

        watchLaterShowsCardView = root.findViewById(R.id.watch_later_shows_cardView);
        hiddenWatchLaterShowsView = root.findViewById(R.id.hidden_watch_later_shows_view);
        watchLaterShowsTextView = root.findViewById(R.id.watch_later_shows_textView);

        likedMoviesCardView = root.findViewById(R.id.liked_movies_cardView);
        hiddenLikedMoviesView = root.findViewById(R.id.hidden_liked_movies_view);
        likedMoviesTextView = root.findViewById(R.id.liked_movies_textView);

        watchLaterMoviesCardView = root.findViewById(R.id.watch_later_movies_cardView);
        hiddenWatchLaterMoviesView = root.findViewById(R.id.hidden_watch_later_movies_view);
        watchLaterMoviesTextView = root.findViewById(R.id.watch_later_movies_textView);

        likedShowsRecyclerView = root.findViewById(R.id.liked_shows_recyclerView);
        likedMoviesRecyclerView = root.findViewById(R.id.liked_movies_recyclerView);
        watchLaterShowsRecyclerView = root.findViewById(R.id.watch_later_shows_recyclerView);
        watchLaterMoviesRecyclerView = root.findViewById(R.id.watch_later_movies_recyclerView);

        linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        linearLayoutManager1 = new LinearLayoutManager(root.getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);

        linearLayoutManager2 = new LinearLayoutManager(root.getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);

        linearLayoutManager3 = new LinearLayoutManager(root.getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);

        showsSharedPreferences = new ShowsSharedPreferences(root.getContext());

        genre1 = root.findViewById(R.id.genre1_editText);
        genre2 = root.findViewById(R.id.genre2_editText);
        genre3 = root.findViewById(R.id.genre3_editText);

        name = root.findViewById(R.id.name_editText);
        welcomeText = root.findViewById(R.id.welcome_textView);

        preference = root.findViewById(R.id.preference_spinner);
        industry = root.findViewById(R.id.industry_spinner);

        mAuth = FirebaseAuth.getInstance();

        user = showsSharedPreferences.getUserData(mAuth.getCurrentUser().getUid());

        name.setText(user.getName());
        welcomeText.setText("Hey, " + user.getName() + "!");

        setRecyclerViews();

        setSpinners();

        genresCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hiddenGenreView.getVisibility() == View.VISIBLE) {

                    // The transition of the hiddenView is carried out
                    //  by the TransitionManager class.
                    // Here we use an object of the AutoTransition
                    // Class to create a default transition.
                    TransitionManager.beginDelayedTransition(genresCardView,
                            new AutoTransition());
                    hiddenGenreView.setVisibility(View.GONE);
                    genresTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.profile_genres_expand, 0);
                }

                else {
                    TransitionManager.beginDelayedTransition(genresCardView,
                            new AutoTransition());

                    ArrayList<String> genres = showsSharedPreferences.getStoredGenres();

                    genre1.setText(genres.get(0));
                    genre2.setText(genres.get(1));
                    genre3.setText(genres.get(2));

                    hiddenGenreView.setVisibility(View.VISIBLE);
                    genresTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.profile_genres_collapse, 0);
                }
            }
        });

        likedShowsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hiddenLikedShowsView.getVisibility() == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(likedShowsCardView,
                            new AutoTransition());
                    hiddenLikedShowsView.setVisibility(View.GONE);
                    likedShowsTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.profile_genres_expand, 0);
                }

                else {
                    TransitionManager.beginDelayedTransition(likedShowsCardView,
                            new AutoTransition());

                    hiddenLikedShowsView.setVisibility(View.VISIBLE);
                    likedShowsTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.profile_genres_collapse, 0);
                }
            }
        });

        watchLaterShowsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hiddenWatchLaterShowsView.getVisibility() == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(watchLaterShowsCardView,
                            new AutoTransition());
                    hiddenWatchLaterShowsView.setVisibility(View.GONE);
                    watchLaterShowsTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.profile_genres_expand, 0);
                }

                else {
                    TransitionManager.beginDelayedTransition(watchLaterShowsCardView,
                            new AutoTransition());

                    hiddenWatchLaterShowsView.setVisibility(View.VISIBLE);
                    watchLaterShowsTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.profile_genres_collapse, 0);
                }
            }
        });

        likedMoviesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hiddenLikedMoviesView.getVisibility() == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(likedMoviesCardView,
                            new AutoTransition());
                    hiddenLikedMoviesView.setVisibility(View.GONE);
                    likedMoviesTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.profile_genres_expand, 0);
                }

                else {
                    TransitionManager.beginDelayedTransition(likedMoviesCardView,
                            new AutoTransition());

                    hiddenLikedMoviesView.setVisibility(View.VISIBLE);
                    likedMoviesTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.profile_genres_collapse, 0);
                }
            }
        });

        watchLaterMoviesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hiddenWatchLaterMoviesView.getVisibility() == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(watchLaterMoviesCardView,
                            new AutoTransition());
                    hiddenWatchLaterMoviesView.setVisibility(View.GONE);
                    watchLaterMoviesTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.profile_genres_expand, 0);
                }

                else {
                    TransitionManager.beginDelayedTransition(watchLaterMoviesCardView,
                            new AutoTransition());

                    hiddenWatchLaterMoviesView.setVisibility(View.VISIBLE);
                    watchLaterMoviesTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.profile_genres_collapse, 0);
                }
            }
        });

        //getActivity().getSupportFragmentManager().popBackStack();

        this.show_movie_layout = getActivity().findViewById(R.id.show_movie_tab_layout);
        this.toolbar = getActivity().findViewById(R.id.custom_toolbar);

        show_movie_layout.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);

        Log.d("Profile Fragment", "Context getClass: " + root.getContext().getClass());

        root.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()){
                    if (NetworkCheck.networkCheck(root.getContext())){
                        user.setName(name.getText().toString());
                        user.setPreference(preference.getSelectedItem().toString());
                        user.setIndustry(industry.getSelectedItem().toString());

                        List<String> temp = new ArrayList<>();
                        temp.add(genre1.getText().toString());
                        temp.add(genre2.getText().toString());
                        temp.add(genre3.getText().toString());

                        user.setGenres(temp);

                        //uploading to firebase
                        UserDatabase userDatabase = new UserDatabase(root.getContext());
                        userDatabase.uploadUserData(user);

                        //updating in shared preferences
                        showsSharedPreferences.storeUserData(user);
                    }

                    else{
                        Toast.makeText(root.getContext(), "Please check your network connection!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        root.findViewById(R.id.edit_genres).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenreFragment genreFragment = new GenreFragment(root.getContext());

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, genreFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }

    private void setRecyclerViews() {
        profileContentAdapter = new ProfileContentAdapter(user.getLikedShows());
        profileContentAdapter1 = new ProfileContentAdapter(user.getWatchLaterShows());
        profileContentAdapter2 = new ProfileContentAdapter(user.getLikedMovies());
        profileContentAdapter3 = new ProfileContentAdapter(user.getWatchLaterMovies());

        likedShowsRecyclerView.setLayoutManager(linearLayoutManager);
        likedShowsRecyclerView.setAdapter(profileContentAdapter);

        watchLaterShowsRecyclerView.setLayoutManager(linearLayoutManager1);
        watchLaterShowsRecyclerView.setAdapter(profileContentAdapter1);

        likedMoviesRecyclerView.setLayoutManager(linearLayoutManager2);
        likedMoviesRecyclerView.setAdapter(profileContentAdapter2);

        watchLaterMoviesRecyclerView.setLayoutManager(linearLayoutManager3);
        watchLaterMoviesRecyclerView.setAdapter(profileContentAdapter3);
    }

    private void setSpinners(){
        ArrayAdapter<CharSequence> industrySpinnerAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.industry, R.layout.profile_spinner_item_layout);
        industrySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        industry.setAdapter(industrySpinnerAdapter);

        ArrayAdapter<CharSequence> preferenceSpinnerAdapter = ArrayAdapter.createFromResource(root.getContext(), R.array.preferences, R.layout.profile_spinner_item_layout);
        preferenceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        preference.setAdapter(preferenceSpinnerAdapter);

        if (user.getIndustry().equals("Bollywood")){
            industry.setSelection(0);
        }

        else{
            industry.setSelection(1);
        }

        if (user.getPreference().equals("OTT Platform")){
            preference.setSelection(0);
        }

        else{
            preference.setSelection(1);
        }
    }

    public boolean validateInputs(){
        if (name.getText().toString().equals("")){
            name.setError("Please enter a valid name!");
            return false;
        }

        return true;
    }
}