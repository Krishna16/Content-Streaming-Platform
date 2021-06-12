package com.example.moviestreamingnew.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.moviestreamingnew.R;
import com.example.moviestreamingnew.ui.new_user_form.GenderFragment;
import com.example.moviestreamingnew.ui.new_user_form.NameFragment;

public class NewUserForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_form);

        NameFragment nameFragment = new NameFragment(this);
        GenderFragment genderFragment = new GenderFragment(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, genderFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}