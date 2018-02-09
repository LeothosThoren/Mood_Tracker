package com.leothosthoren.moodtracker.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.leothosthoren.moodtracker.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.leothosthoren.moodtracker.controler.MainActivity.LIST_COLOR_IMG;
import static com.leothosthoren.moodtracker.controler.MainActivity.comment;
import static com.leothosthoren.moodtracker.controler.MainActivity.indexMood;
import static com.leothosthoren.moodtracker.model.MoodDataStorage.mListMoodItems;
import static com.leothosthoren.moodtracker.view.MoodAdapter.NUMBER_ITEM;

/**
 * This class is the useful to receive intents,
 * broadcast receiver performs an operation when the alarm is fired/triggered.
 */

public class MoodAlarmReceiver extends BroadcastReceiver {
    /*
    * @onReceive method
    * @context param
    * @intent param
    *
    * This method is called when the BroadcastReceiver is receiving
    * */
    @Override
    public void onReceive(Context context, Intent intent) {
        MoodDataStorage.loadData(context);
        Toast.makeText(context, "AR running the : " + showDate(), Toast.LENGTH_LONG).show();

        //We check the array size and remove first index when the limit is reached
        if (mListMoodItems.size() == NUMBER_ITEM)
            mListMoodItems.remove(0);

        //We add each day an another item with his content
        if (mListMoodItems.size() < NUMBER_ITEM) {
            mListMoodItems.add(new ListMoodItem(
                    LIST_COLOR_IMG[0][indexMood],
                    indexMood,
                    comment,
                    R.drawable.ic_comment_black_48px,
                    "date"
            ));
        }
        //Here we call the saveData method include in the MoodDataStorage class
        MoodDataStorage.saveData(context);
        resetMoodEachDay();
    }

    /*
    * @resetMoodEachDay method
    *
    * This method reset comment and index to theirs originals status
    * Every time the data is saved we call this method to get the app at his standard state
    * */
    public void resetMoodEachDay() {
        comment = "";
        indexMood = 3;
    }

    public String showDate() {
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);
        return simpleDateFormat.format(today);

    }

}
