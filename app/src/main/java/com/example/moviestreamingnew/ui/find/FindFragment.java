package com.example.moviestreamingnew.ui.find;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.adapters.FindGenresRecyclerAdapter;
import com.example.moviestreamingnew.adapters.ListFoundContentAdapter;
import com.example.moviestreamingnew.repository.ShowsDatabase;
import com.example.moviestreamingnew.ui.list_find_content.ListFoundContentActivity;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FindFragment extends Fragment {

    private FindViewModel dashboardViewModel;

    private TabLayout show_movie_layout;
    private Toolbar toolbar;

    private CardView tvCardView;
    private CardView movieCardView;

    private CardView hollywoodCardView;
    private CardView bollywoodCardView;

    private RecyclerView genresRecyclerView;
    private LinearLayoutManager linearLayoutManager;

    private ArrayList<String> genres;

    private ShowsDatabase showsDatabase;

    private FindGenresRecyclerAdapter findGenresRecyclerAdapter;

    private View root;

    private EditText query_editText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(FindViewModel.class);
        root = inflater.inflate(R.layout.fragment_find, container, false);

        this.show_movie_layout = getActivity().findViewById(R.id.show_movie_tab_layout);
        this.toolbar = getActivity().findViewById(R.id.custom_toolbar);

        show_movie_layout.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);

        tvCardView = root.findViewById(R.id.tv_cardView);
        movieCardView = root.findViewById(R.id.movie_cardView);

        hollywoodCardView = root.findViewById(R.id.hollywood_cardView);
        bollywoodCardView = root.findViewById(R.id.bollywood_cardView);

        query_editText = root.findViewById(R.id.query_editText);

        findGenresRecyclerAdapter = new FindGenresRecyclerAdapter(genres, root.getContext());

        genresRecyclerView = root.findViewById(R.id.genres_recyclerView);
        linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        genresRecyclerView.setLayoutManager(linearLayoutManager);
        genresRecyclerView.setAdapter(findGenresRecyclerAdapter);

        hollywoodCardView = root.findViewById(R.id.hollywood_cardView);
        bollywoodCardView = root.findViewById(R.id.bollywood_cardView);

        query_editText = root.findViewById(R.id.query_editText);

        query_editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (query_editText.getText().toString().equals("")){
                        Toast.makeText(root.getContext(), "Please enter your query!!", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    else {
                        // Perform action on key press
                        Toast.makeText(root.getContext(), query_editText.getText(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(root.getContext(), ListFoundContentActivity.class);
                        intent.putExtra("Query", query_editText.getText().toString());
                        root.getContext().startActivity(intent);

                        return true;
                    }
                }

                return false;
            }
        });

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                findGenresRecyclerAdapter.notifyDataSetChanged();
            }
        }, 1000);

        tvCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), ListFoundContentActivity.class);
                intent.putExtra("TV", "TV");
                root.getContext().startActivity(intent);
            }
        });

        movieCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), ListFoundContentActivity.class);
                intent.putExtra("Movie", "Movie");
                root.getContext().startActivity(intent);
            }
        });

        hollywoodCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), ListFoundContentActivity.class);
                intent.putExtra("Hollywood", "Hollywood");
                root.getContext().startActivity(intent);
            }
        });

        bollywoodCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), ListFoundContentActivity.class);
                intent.putExtra("Bollywood", "Bollywood");
                root.getContext().startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);

        showsDatabase = new ShowsDatabase(context);

        this.genres = new ArrayList<>();
        this.genres = showsDatabase.getGenres();
    }
}