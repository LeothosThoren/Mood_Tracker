package com.leothosthoren.moodtracker.controler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leothosthoren.moodtracker.R;
import com.leothosthoren.moodtracker.model.ListMoodItem;
import com.leothosthoren.moodtracker.view.ListMoodAdapter;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.leothosthoren.moodtracker.controler.MainActivity.LIST_COLOR_IMG;
import static com.leothosthoren.moodtracker.controler.MainActivity.comment;
import static com.leothosthoren.moodtracker.controler.MainActivity.indexMood;

public class HistoryActivity extends AppCompatActivity {

    public static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    public static final String MOOD_DATA = "MOOD_DATA";

    ArrayList<ListMoodItem> mListMoodItems = new ArrayList<>();

    Date now = new Date();
    Locale mLocale = Locale.FRANCE;
    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm", mLocale);
    String date = sdf1.format(now);


    private RecyclerView mRecyclerView;
    private ListMoodAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        loadData();
        buildRecyclerView();
//        schedAlarm(this);
        setButton();

    }

    private void schedAlarm(Context context) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 15);
        cal.set(Calendar.MINUTE, 43);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        //cal.add(Calendar.DAY_OF_MONTH, 1);
        PendingIntent pi = PendingIntent.getService(context, 0,
                new Intent(context, HistoryActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert am != null;
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);

        mListMoodItems.add(new ListMoodItem(
                LIST_COLOR_IMG[0][indexMood],
                indexMood,
                comment,
                R.drawable.ic_comment_black_48px,
                date
        ));
        saveData();
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mListMoodItems);
        editor.putString(MOOD_DATA, json).apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(MOOD_DATA, null);
        Type type = new TypeToken<ArrayList<ListMoodItem>>() {
        }.getType();
        mListMoodItems = gson.fromJson(json, type);

        if (mListMoodItems == null) {
            mListMoodItems = new ArrayList<>();
        }
    }

    public void clearData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }

    public void setButton() {
        //Button for testing sharepreferences
        Button btnSave = (Button) findViewById(R.id.Btn_save);
        Button btnDelete = (Button) findViewById(R.id.Btn_delete);

        /**Series of button for test**/
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListMoodItems.add(new ListMoodItem(
                        LIST_COLOR_IMG[0][indexMood],
                        indexMood,
                        comment,
                        R.drawable.ic_comment_black_48px,
                        date
                ));
                saveData();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }
        });
    }

    public void toastMaker(int position) {
        String text = mListMoodItems.get(position).getComment();
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        View vt = toast.getView();
        vt.setBackgroundColor(getResources().getColor(R.color.black));
        toast.show();
        mAdapter.notifyItemChanged(position);
    }

    public String dateMaker() {
        int SECONDS_IN_A_DAY = 24 * 60 * 60;

        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(new Date(0)); /* reset */
        thatDay.set(Calendar.DAY_OF_MONTH, 1);
        thatDay.set(Calendar.MONTH, 0); // 0-11 so 1 less


        Calendar today = Calendar.getInstance();
        long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
        long diffSec = diff / 1000;

        long days = diffSec / SECONDS_IN_A_DAY;
        long secondsDay = diffSec % SECONDS_IN_A_DAY;
        long seconds = secondsDay % 60;
        long minutes = (secondsDay / 60) % 60;
        long hours = (secondsDay / 3600); // % 24 not needed

        return "Il y a " + days + " " + minutes + " " + seconds + " jours";
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ListMoodAdapter(mListMoodItems, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ListMoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                toastMaker(position);
            }
        });
    }


}
