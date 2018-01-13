package com.leothosthoren.moodtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.Date;

import static com.leothosthoren.moodtracker.MainActivity.LIST_COLOR_IMG;

public class HistoryActivity extends AppCompatActivity {

    ListView liste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


    }
}
