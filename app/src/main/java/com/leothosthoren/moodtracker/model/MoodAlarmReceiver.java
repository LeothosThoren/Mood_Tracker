package com.leothosthoren.moodtracker.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.leothosthoren.moodtracker.R;

import static com.leothosthoren.moodtracker.controler.MainActivity.LIST_COLOR_IMG;
import static com.leothosthoren.moodtracker.controler.MainActivity.comment;
import static com.leothosthoren.moodtracker.controler.MainActivity.indexMood;
import static com.leothosthoren.moodtracker.model.MoodDataStorage.mListMoodItems;
import static com.leothosthoren.moodtracker.view.MoodAdapter.NUMBER_ITEM;

public class MoodAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (mListMoodItems.size() == NUMBER_ITEM)
            mListMoodItems.remove(0);

        mListMoodItems.add(new ListMoodItem(
                LIST_COLOR_IMG[0][indexMood],
                indexMood,
                comment,
                R.drawable.ic_comment_black_48px,
                "date"
        ));
        MoodDataStorage.saveData(context);

        resetMoodEachDay();
    }

    public void resetMoodEachDay() {
        comment = "";
        indexMood = 3;
    }
}
