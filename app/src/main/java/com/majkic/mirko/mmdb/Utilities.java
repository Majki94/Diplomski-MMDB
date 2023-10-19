package com.majkic.mirko.mmdb;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

/**
 * Created by hp on 08.11.2019.
 */

public class Utilities {

    public static void setFavoriteOrWatched(Context context, ImageView imageView, boolean favouriteOrWatched) {
        if (favouriteOrWatched) {
            imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent));
        } else {
            imageView.clearColorFilter();
        }
    }

    public static void hideKeyboard(View view) {
        if (view != null && view.getContext() != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
