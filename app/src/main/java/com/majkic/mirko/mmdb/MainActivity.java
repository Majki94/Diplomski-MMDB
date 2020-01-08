package com.majkic.mirko.mmdb;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.majkic.mirko.mmdb.main.MainFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int NONE_SELECTED = -1;
    private static final int MOVIES_SELECTED = 0;
    private static final int PEOPLE_SELECTED = 1;

    @BindView(R.id.movies_button)
    LinearLayout moviesButton;
    @BindView(R.id.people_button)
    LinearLayout peopleButton;

    int selection = NONE_SELECTED;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        if (selection == NONE_SELECTED || selection == MOVIES_SELECTED) {
            BackStack.setActivityReference(this);
            BackStack.presentFragment(MainFragment.newInstance());
            setSelection(MOVIES_SELECTED);
        } else {
            //TODO implement People fragment showing
        }

        moviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selection != MOVIES_SELECTED) {
                    setSelection(MOVIES_SELECTED);
                    BackStack.presentFragment(MainFragment.newInstance());
                }
            }
        });

        peopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selection != PEOPLE_SELECTED) {
                    setSelection(PEOPLE_SELECTED);
                    //TODO implement People fragment showing
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        BackStack.onBackPressed();
    }

    private void setSelection(int selection) {
        this.selection = selection;
        moviesButton.setBackgroundColor(Color.TRANSPARENT);
        peopleButton.setBackgroundColor(Color.TRANSPARENT);
        switch (selection) {
            case 0:
                moviesButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhiteTransparentSelector));
                break;
            case 1:
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
