package com.leothosthoren.moodtracker.model;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.leothosthoren.moodtracker.controler.HistoryActivity.MOOD_DATA;
import static com.leothosthoren.moodtracker.controler.HistoryActivity.SHARED_PREFERENCES;

/**
 * Created by Sofiane M. alias Leothos Thoren on 16/01/2018
 */
public class MoodDataStorage {

    public MoodDataStorage() {
    }

    public static void saveData(Activity activity) {
        ArrayList<ListMoodItem> mListMoodItems = new ArrayList<>();
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mListMoodItems);
        editor.putString(MOOD_DATA, json);
        editor.apply();
    }

    public static void loadData(Activity activity) {
        ArrayList<ListMoodItem> mListMoodItems;
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(MOOD_DATA, null);
        Type type = new TypeToken<ArrayList<ListMoodItem>>() {
        }.getType();
        mListMoodItems = gson.fromJson(json, type);

        if (mListMoodItems == null) {
            mListMoodItems = new ArrayList<>();
        }
    }

    public void clearData(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
