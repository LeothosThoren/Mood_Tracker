package com.leothosthoren.moodtracker.controler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;

import com.leothosthoren.moodtracker.R;
import com.leothosthoren.moodtracker.model.ListMoodData;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ListView mListView;
    private AdapterView mAdapterView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ArrayList<ListMoodData> listMood = new ArrayList<>();

    }
}
