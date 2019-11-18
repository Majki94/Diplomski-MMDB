package com.majkic.mirko.mmdb;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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

}
