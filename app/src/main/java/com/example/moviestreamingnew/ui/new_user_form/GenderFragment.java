package com.example.moviestreamingnew.ui.new_user_form;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.moviestreamingnew.R;


public class GenderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView male_text, female_text;
    private Context context;
    private View root;

    private RadioButton maleButton;
    private RadioButton femaleButton;

    private CardView male_cardView, female_cardView;

    public GenderFragment(Context context) {
        // Required empty public constructor
        this.context = context;
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
        this.root = inflater.inflate(R.layout.fragment_gender, container, false);

        this.male_text = root.findViewById(R.id.male_textView);
        this.male_text.bringToFront();

        this.female_text = root.findViewById(R.id.female_textView);
        this.female_text.bringToFront();

        this.maleButton = root.findViewById(R.id.radio_male);
        this.femaleButton = root.findViewById(R.id.radio_female);

        this.male_cardView = root.findViewById(R.id.male_cardView);
        this.female_cardView = root.findViewById(R.id.female_cardView);

        male_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleButton.setChecked(true);
                femaleButton.setEnabled(false);
            }
        });

        female_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                femaleButton.setChecked(true);
                maleButton.setEnabled(false);
            }
        });

        maleButton.bringToFront();
        femaleButton.bringToFront();

        return root;
    }
}