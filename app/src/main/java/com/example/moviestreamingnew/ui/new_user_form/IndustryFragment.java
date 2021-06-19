package com.example.moviestreamingnew.ui.new_user_form;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.models.User;


public class IndustryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View root;
    private String selected;

    private CardView hollywoodCard, bollywoodCard;
    private RadioButton hollywoodRadio, bollywoodRadio;

    public IndustryFragment() {
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
        this.root = inflater.inflate(R.layout.fragment_industry, container, false);

        this.hollywoodRadio = root.findViewById(R.id.radio_hollywood);
        this.bollywoodRadio = root.findViewById(R.id.radio_bollywood);

        this.hollywoodCard = root.findViewById(R.id.hollywood_cardView);
        this.bollywoodCard = root.findViewById(R.id.bollywood_cardView);

        hollywoodCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hollywoodRadio.setChecked(true);
                bollywoodRadio.setChecked(false);
                hollywoodCard.setBackgroundResource(R.drawable.cardview_genre_selected);
                bollywoodCard.setBackgroundResource(R.drawable.original_cardview);
                selected = "Hollywood";
            }
        });

        bollywoodCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bollywoodRadio.setChecked(true);
                hollywoodRadio.setChecked(false);
                bollywoodCard.setBackgroundResource(R.drawable.cardview_genre_selected);
                hollywoodCard.setBackgroundResource(R.drawable.original_cardview);
                selected = "Bollywood";
            }
        });

        root.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!bollywoodRadio.isChecked()&&!hollywoodRadio.isChecked())
                {
                    Toast.makeText(root.getContext(),"Select One",Toast.LENGTH_SHORT).show();
                }
                else {
                    PreferenceFragment preferenceFragment = new PreferenceFragment();

                    User.getInstance().setIndustry(selected);
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, preferenceFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return root;
    }
}