package com.majkic.mirko.mmdb;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.majkic.mirko.mmdb.databinding.ActivityMainBinding;
import com.majkic.mirko.mmdb.ui.main.MainFragment;
import com.majkic.mirko.mmdb.ui.people.PeopleFragment;
import com.majkic.mirko.mmdb.ui.search.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int NONE_SELECTED = -1;
    public static final int MOVIES_SELECTED = 0;
    public static final int SEARCH_SELECTED = 1;
    public static final int PEOPLE_SELECTED = 2;

    int selection = NONE_SELECTED;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BackStack.setActivityReference(this);

        if (selection == NONE_SELECTED || selection == MOVIES_SELECTED) {
            BackStack.presentFragment(MainFragment.newInstance());
            setSelection(MOVIES_SELECTED);
        } else if (selection == SEARCH_SELECTED) {
            BackStack.presentFragment(SearchFragment.newInstance());
            setSelection(SEARCH_SELECTED);
        } else {
            BackStack.presentFragment(PeopleFragment.newInstance());
            setSelection(PEOPLE_SELECTED);
        }

        binding.moviesButton.setOnClickListener(v -> {
            if (selection != MOVIES_SELECTED) {
                setSelection(MOVIES_SELECTED);
                BackStack.clearToHomeAndPresent();
            }
        });

        binding.searchButton.setOnClickListener(view -> {
            if (selection != SEARCH_SELECTED) {
                setSelection(SEARCH_SELECTED);
                BackStack.presentFragment(SearchFragment.newInstance());
            }
        });

        binding.peopleButton.setOnClickListener(v -> {
            if (selection != PEOPLE_SELECTED) {
                setSelection(PEOPLE_SELECTED);
                BackStack.presentFragment(PeopleFragment.newInstance());
            }
        });

    }

    @Override
    public void onBackPressed() {
        BackStack.onBackPressed();
    }

    public void setSelection(int selection) {
        this.selection = selection;
        binding.moviesButton.setBackgroundColor(Color.TRANSPARENT);
        binding.searchButton.setBackgroundColor(Color.TRANSPARENT);
        binding.peopleButton.setBackgroundColor(Color.TRANSPARENT);
        switch (selection) {
            case MOVIES_SELECTED:
                binding.moviesButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhiteTransparentSelector));
                break;
            case SEARCH_SELECTED:
                binding.searchButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhiteTransparentSelector));
                break;
            case PEOPLE_SELECTED:
                binding.peopleButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhiteTransparentSelector));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
