package com.example.moviestreamingnew.ui.new_user_form;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.adapters.GenreSelectionAdapter;
import com.example.moviestreamingnew.common.RecyclerTouchListener;
import com.example.moviestreamingnew.repository.Storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class GenreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View root;
    private RecyclerView genre_selection;
    private GridLayoutManager gridLayoutManager;

    private ArrayList<String> genres;
    private Storage storage;
    private GenreSelectionAdapter genreSelectionAdapter;

    private ExecutorService pool;

    private Context context;

    private Set selectedGenres;
    private Button next;

    public GenreFragment(Context context) {
        // Required empty public constructor
        this.genres = new ArrayList<>();
        this.storage = new Storage();
        this.context = context;
        this.selectedGenres = new HashSet();
    }

    /*public static GenreFragment newInstance(String param1, String param2) {
        GenreFragment fragment = new GenreFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_genre, container, false);

        genre_selection = root.findViewById(R.id.genre_selection_recyclerView);

        gridLayoutManager = new GridLayoutManager(context, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        next = root.findViewById(R.id.next);
        next.setEnabled(false);
        next.setBackgroundResource(R.drawable.disabled_rounded_corner_button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IndustryFragment industryFragment = new IndustryFragment();

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, industryFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        /*pool = Executors.newFixedThreadPool(1);
        pool.execute(new Runnable() {
            @Override
            public void run() {
                genres = storage.getGenres();
            }
        });

        try {
            pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        genres = storage.getGenres();

        genreSelectionAdapter = new GenreSelectionAdapter(genres, context);

        genre_selection.setLayoutManager(gridLayoutManager);
        genre_selection.setAdapter(genreSelectionAdapter);

        genre_selection.addOnItemTouchListener(new RecyclerTouchListener(context, genre_selection, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                RecyclerView.ViewHolder tempHolder = genre_selection.findViewHolderForLayoutPosition(position);
                CardView tempCard = tempHolder.itemView.findViewById(R.id.genre_card);
                CheckBox temp = tempHolder.itemView.findViewById(R.id.genre_checkbox);


                if (temp.isChecked()) {
                    temp.setSelected(false);
                    selectedGenres.remove(temp.getText().toString());
                    tempCard.setBackgroundResource(R.drawable.cardview_genre_unselected);
                }
                else{
                    temp.setSelected(true);
                    selectedGenres.add(temp.getText().toString());
                    tempCard.setBackgroundResource(R.drawable.cardview_genre_selected);
                }

                if(selectedGenres.size() == 3)
                {
                    next.setEnabled(true);
                    next.setBackgroundResource(R.drawable.rounded_corner_button);
                }
                else if(selectedGenres.size()>3)
                {
                    next.setEnabled(false);
                    next.setBackgroundResource(R.drawable.disabled_rounded_corner_button);
                    Toast.makeText(context,"You must select exactly 3 genres",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    next.setEnabled(false);
                    next.setBackgroundResource(R.drawable.disabled_rounded_corner_button);

                }
                for(int i =0;i<selectedGenres.size();i++)
                {
                    Log.d("WorkingFineSelected",selectedGenres.toString());
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }


        }));

        /*new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(root.getContext(), "Adapter Refreshed!!", Toast.LENGTH_LONG).show();
                genreSelectionAdapter.notifyDataSetChanged();
            }
        }, 3000);*/
        return root;
    }
}