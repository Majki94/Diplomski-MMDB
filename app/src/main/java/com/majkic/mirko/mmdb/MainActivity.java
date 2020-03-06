package com.majkic.mirko.mmdb;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.majkic.mirko.mmdb.main.MainFragment;
import com.majkic.mirko.mmdb.people.PeopleFragment;
import com.majkic.mirko.mmdb.search.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int NONE_SELECTED = -1;
    public static final int MOVIES_SELECTED = 0;
    public static final int SEARCH_SELECTED = 1;
    public static final int PEOPLE_SELECTED = 2;

    @BindView(R.id.movies_button)
    LinearLayout moviesButton;
    @BindView(R.id.search_button)
    LinearLayout searchButton;
    @BindView(R.id.people_button)
    LinearLayout peopleButton;

    int selection = NONE_SELECTED;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

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

        moviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selection != MOVIES_SELECTED) {
                    setSelection(MOVIES_SELECTED);
                    BackStack.clearToHomeAndPresent();
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selection != SEARCH_SELECTED) {
                    setSelection(SEARCH_SELECTED);
                    BackStack.presentFragment(SearchFragment.newInstance());
                }
            }
        });

        peopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selection != PEOPLE_SELECTED) {
                    setSelection(PEOPLE_SELECTED);
                    BackStack.presentFragment(PeopleFragment.newInstance());
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        BackStack.onBackPressed();
    }

    public void setSelection(int selection) {
        this.selection = selection;
        moviesButton.setBackgroundColor(Color.TRANSPARENT);
        searchButton.setBackgroundColor(Color.TRANSPARENT);
        peopleButton.setBackgroundColor(Color.TRANSPARENT);
        switch (selection) {
            case MOVIES_SELECTED:
                moviesButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhiteTransparentSelector));
                break;
            case SEARCH_SELECTED:
                searchButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhiteTransparentSelector));
                break;
            case PEOPLE_SELECTED:
                peopleButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhiteTransparentSelector));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
