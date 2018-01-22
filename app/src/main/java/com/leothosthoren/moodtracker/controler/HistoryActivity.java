package com.leothosthoren.moodtracker.controler;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leothosthoren.moodtracker.R;
import com.leothosthoren.moodtracker.model.ListMoodItem;
import com.leothosthoren.moodtracker.view.MoodAdapter;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
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
    SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", mLocale);
    String d = sdf1.format(now);

    private RecyclerView mRecyclerView;
    private MoodAdapter mAdapter;
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

//    private void schedAlarm(Context context) {
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR_OF_DAY, 15);
//        cal.set(Calendar.MINUTE, 43);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MILLISECOND, 0);
//        //cal.add(Calendar.DAY_OF_MONTH, 1);
//        PendingIntent pi = PendingIntent.getService(context, 0,
//                new Intent(context, HistoryActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        assert am != null;
//        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pi);
//
//        mListMoodItems.add(new ListMoodItem(
//                LIST_COLOR_IMG[0][indexMood],
//                indexMood,
//                comment,
//                R.drawable.ic_comment_black_48px,
//                d
//        ));
//        saveData();
//    }

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
                        d
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
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));
        TextView toastText = (TextView) layout.findViewById(R.id.text);
        toastText.setText(text);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

        mAdapter.notifyItemChanged(position);
    }

//    public String dateMaker() {
//
//        Date now = new Date();
//        Locale mLocale = Locale.FRANCE;
//        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", mLocale);
//        String d = sdf1.format(now);
//
//        Calendar rightNow = Calendar.getInstance();
//        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
//        int minute = rightNow.get(Calendar.MINUTE);
//        int second = rightNow.get(Calendar.SECOND);
//
//        return "il y a "+ hour + " heure " + minute + "  minutes "+ second + " seconds.";
//    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MoodAdapter(mListMoodItems, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (!mListMoodItems.get(position).getComment().equals(""))
                    toastMaker(position);
            }
        });
    }


}
