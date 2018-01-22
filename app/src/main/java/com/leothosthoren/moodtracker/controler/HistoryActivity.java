package com.leothosthoren.moodtracker.controler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

import com.leothosthoren.moodtracker.R;
import com.leothosthoren.moodtracker.model.ListMoodItem;
import com.leothosthoren.moodtracker.model.MoodAlarmReceiver;
import com.leothosthoren.moodtracker.model.MoodDataStorage;
import com.leothosthoren.moodtracker.view.MoodAdapter;

import java.util.Calendar;

import static com.leothosthoren.moodtracker.controler.MainActivity.LIST_COLOR_IMG;
import static com.leothosthoren.moodtracker.controler.MainActivity.comment;
import static com.leothosthoren.moodtracker.controler.MainActivity.indexMood;
import static com.leothosthoren.moodtracker.model.MoodDataStorage.mListMoodItems;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MoodAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PendingIntent mPendingIntent;
    private AlarmManager mAlarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        MoodDataStorage.loadData(this);
        buildRecyclerView();
        setButton();
        schedAlarm(this);
    }

    private void schedAlarm(Context context) {
        //The scheddule is set to be launch at midnight
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
//        cal.add(Calendar.DAY_OF_MONTH, 1);
        // Retrieve a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(context, MoodAlarmReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        mAlarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, mPendingIntent);

    }



    public void setButton() {
        //Button for testing sharepreferences saving
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
                        "date"
                ));
                MoodDataStorage.saveData(HistoryActivity.this);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoodDataStorage.clearData(HistoryActivity.this);
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
