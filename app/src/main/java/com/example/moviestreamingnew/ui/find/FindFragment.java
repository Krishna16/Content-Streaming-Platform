package com.example.moviestreamingnew.ui.find;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.moviestreamingnew.R;
import com.google.android.material.tabs.TabLayout;

public class FindFragment extends Fragment {

    private FindViewModel dashboardViewModel;

    private TabLayout show_movie_layout;
    private Toolbar toolbar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(FindViewModel.class);
        View root = inflater.inflate(R.layout.fragment_find, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        this.show_movie_layout = getActivity().findViewById(R.id.show_movie_tab_layout);
        this.toolbar = getActivity().findViewById(R.id.custom_toolbar);

        show_movie_layout.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);

        return root;
    }
}