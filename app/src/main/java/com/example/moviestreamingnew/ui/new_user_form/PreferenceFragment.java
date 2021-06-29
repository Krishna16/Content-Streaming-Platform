package com.example.moviestreamingnew.ui.new_user_form;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.common.ShowsSharedPreferences;
import com.example.moviestreamingnew.models.User;
import com.example.moviestreamingnew.repository.UserDatabase;


public class PreferenceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View root;

    private String selected;
    private CardView theatreCard, ottCard;
    private RadioButton theatreRadio, ottRadio;

    private ShowsSharedPreferences showsSharedPreferences;

    public PreferenceFragment() {
        // Required empty public constructor
    }

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
        this.root = inflater.inflate(R.layout.fragment_preference, container, false);

        this.theatreRadio = root.findViewById(R.id.radio_theatre);
        this.ottRadio = root.findViewById(R.id.radio_ott);

        this.theatreCard = root.findViewById(R.id.theatre_cardView);
        this.ottCard = root.findViewById(R.id.ott_cardView);

        this.showsSharedPreferences = new ShowsSharedPreferences(root.getContext());

        theatreCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theatreRadio.setChecked(true);
                ottRadio.setChecked(false);
                theatreCard.setBackgroundResource(R.drawable.cardview_genre_selected);
                ottCard.setBackgroundResource(R.drawable.original_cardview);
                selected = "Theatre";
            }
        });

        ottCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ottRadio.setChecked(true);
                theatreRadio.setChecked(false);
                ottCard.setBackgroundResource(R.drawable.cardview_genre_selected);
                theatreCard.setBackgroundResource(R.drawable.original_cardview);
                selected="OTT";
            }
        });

        root.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ottRadio.isChecked() && !theatreRadio.isChecked())
                {
                    Toast.makeText(root.getContext(),"Select One",Toast.LENGTH_SHORT).show();
                }
                else {
                    User.getInstance().setPreference(selected);
                    UserDatabase userDB = new UserDatabase(root.getContext());
                    userDB.uploadUserData();

                    showsSharedPreferences.storeUserData(User.getInstance());
                }
            }
        });

        return root;
    }
}