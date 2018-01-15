package com.leothosthoren.moodtracker.controler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.leothosthoren.moodtracker.R;
import com.leothosthoren.moodtracker.model.ListMoodItem;
import com.leothosthoren.moodtracker.view.ListMoodAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Date now = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ArrayList<ListMoodItem> listMood = new ArrayList<>();
        listMood.add(new ListMoodItem(R.color.light_sage, R.drawable.ic_comment_black_48px, now));
        listMood.add(new ListMoodItem(R.color.cornflower_blue_65, R.drawable.ic_comment_black_48px, now));
        listMood.add(new ListMoodItem(R.color.faded_red, R.drawable.ic_comment_black_48px, now));

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ListMoodAdapter(listMood);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
