package com.example.hw4;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

class FavoritesUtil {
    static private SharedPreferences settings;
    static private Context context;
    static private String PREF_NAME;

    public static void initFavoritesUtil(Context context, String PREF_NAME, int MODE) {
        FavoritesUtil.context = context;
        FavoritesUtil.PREF_NAME = PREF_NAME;

        settings = context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static void addID(String id) {
        String ids = settings.getString(PREF_NAME, MainActivity.EMPTY_STRING);

        String newIDS = ids + " " + id;

        newIDS = newIDS.trim();

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_NAME, newIDS);
        editor.commit();

    }

    public static void removeID(String id) {
        String ids = settings.getString(PREF_NAME, MainActivity.EMPTY_STRING);

        if (ids != MainActivity.EMPTY_STRING) {
            if (ids.contains(id)) {
                Log.d("remove", "ids before removal: " + ids);
                ids = ids.replace(id, "");

                //clean up double space if exists
                if (ids.contains("  ")) {
                    ids.replace("  ", " ");
                }

                ids = ids.trim();

                SharedPreferences.Editor editor = settings.edit();
                editor.putString(PREF_NAME, ids);
                editor.commit();
                Log.d("remove", "ids after removal: " + ids);

            }
        }
    }

    public static boolean hasID(String id) {
        String ids = settings.getString(PREF_NAME, MainActivity.EMPTY_STRING);

        if (ids == null) {
            return false;
        } else {
            return ids.contains(id);
        }
    }

    public static void clearFavorites() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_NAME, MainActivity.EMPTY_STRING);
        editor.commit();

    }

}